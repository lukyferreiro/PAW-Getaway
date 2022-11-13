package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.interfaces.exceptions.ExperienceNotFoundException;
import ar.edu.itba.getaway.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.getaway.interfaces.services.ExperienceService;
import ar.edu.itba.getaway.models.ExperienceModel;
import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.interfaces.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImagesController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private ExperienceService experienceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesController.class);

    @ResponseBody
    @RequestMapping(path = "/experiences/{experienceId:[0-9]+}/image",
            method = RequestMethod.GET,
            produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getExperiencesImages(@PathVariable("experienceId") final long experienceId) {
        LOGGER.info("Accessed experiences/{}/image GET controller", experienceId);
        final ExperienceModel experienceModel = experienceService.getExperienceById(experienceId).orElseThrow(ExperienceNotFoundException::new);
        return experienceModel.getExperienceImage().getImage();
    }

    @ResponseBody
    @RequestMapping(value = "/user/profileImage/{imageId:[0-9]+}",
            method = {RequestMethod.GET},
            produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getUserProfileImage(@PathVariable("imageId") final long imageId) {
        LOGGER.info("Retrieving profileImage {}", imageId);
        final ImageModel imageModel = imageService.getImgById(imageId).orElseThrow(ImageNotFoundException::new);
        return imageModel.getImage();
    }
}
