package ar.edu.itba.getaway.webapp.controller.forms;

import ar.edu.itba.getaway.models.*;
import ar.edu.itba.getaway.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.getaway.webapp.forms.ExperienceForm;
import ar.edu.itba.getaway.webapp.forms.SearchForm;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.interfaces.services.LocationService;
import ar.edu.itba.getaway.interfaces.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ExperienceFormController {

    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;
//    @Autowired
//    private ForceLogin forceLogin;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceFormController.class);

    private static final List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/jpg");

    @RequestMapping(value = "/create_experience", method = {RequestMethod.GET})
    public ModelAndView createExperienceForm(@ModelAttribute("experienceForm") final ExperienceForm form,
                                             @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                             HttpServletRequest request) {
        LOGGER.debug("Endpoint GET {}", request.getServletPath());
        final ModelAndView mav = new ModelAndView("experienceForm");
        final ExperienceCategory[] categoryModels = ExperienceCategory.values();
        final List<CityModel> cities = locationService.listAllCities();
        final String country = locationService.getCountryByName().get().getCountryName();
        mav.addObject("title", "createExperience.title");
        mav.addObject("description", "createExperience.description");
        mav.addObject("endpoint", "/create_experience");
        mav.addObject("cancelBtn", "/");
        mav.addObject("categories", categoryModels);
        mav.addObject("cities", cities);
        mav.addObject("country", country);
        mav.addObject("formCity", form.getExperienceCity());
        mav.addObject("formCategory", form.getExperienceCategory());
        return mav;
    }

    @RequestMapping(value = "/create_experience", method = {RequestMethod.POST})
    public ModelAndView createExperience(@Valid @ModelAttribute("experienceForm") final ExperienceForm form,
                                         final BindingResult errors,
                                         @Valid @ModelAttribute("searchForm") final SearchForm searchForm,
                                         Principal principal,
                                         HttpServletRequest request) throws Exception {
        LOGGER.debug("Endpoint POST {}", request.getServletPath());
        LOGGER.debug("User tries to create an experience with category id: {}", form.getExperienceCategory());

        if (errors.hasErrors()) {
            return createExperienceForm(form, searchForm, request);
        }

        final MultipartFile experienceImg = form.getExperienceImg();
        if (!experienceImg.isEmpty()) {
            if (!contentTypes.contains(experienceImg.getContentType())) {
                errors.rejectValue("experienceImg", "experienceForm.validation.imageFormat");
                return createExperienceForm(form, searchForm, request);
            }
        }

        final UserModel user = userService.getUserByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);
        final Long userId = user.getUserId();
        final Long categoryId = form.getExperienceCategory();
        final CityModel cityModel = locationService.getCityByName(form.getExperienceCity()).get();
        final Long cityId = cityModel.getCityId();
        final Double price = (form.getExperiencePrice().isEmpty()) ? null : Double.parseDouble(form.getExperiencePrice());
        final String description = (form.getExperienceInfo().isEmpty()) ? null : form.getExperienceInfo();
        final String url = (form.getExperienceUrl().isEmpty()) ? null : form.getExperienceUrl();

        final byte[] image = (experienceImg.isEmpty()) ? null : experienceImg.getBytes();
        final ExperienceModel experienceModel = experienceService.createExperience(form.getExperienceName(), form.getExperienceAddress(), description,
                form.getExperienceMail(), url, price, cityId, categoryId + 1, userId, image);
        if(!user.hasRole("PROVIDER")){
            LOGGER.debug("Updating SpringContextHolder");
            forceLogin(user, request);
        }
        return new ModelAndView("redirect:/experiences/" + experienceModel.getCategoryName() + "/" + experienceModel.getExperienceId());
    }

    //This method is used to update the SpringContextHolder
    //https://stackoverflow.com/questions/9910252/how-to-reload-authorities-on-user-update-with-spring-security
    private void forceLogin(UserModel user, HttpServletRequest request) {

        final PreAuthenticatedAuthenticationToken token =
                new PreAuthenticatedAuthenticationToken(user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));

        token.setDetails(new WebAuthenticationDetails(request));

        final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(token);

        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
        LOGGER.debug("Updated SpringContextHolder in order to update user roles");
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
        return roles.stream()
                .map((role) -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

}
