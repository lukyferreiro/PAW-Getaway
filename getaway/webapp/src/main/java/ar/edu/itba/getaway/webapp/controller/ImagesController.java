package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.exceptions.ImageNotFoundException;
import ar.edu.itba.getaway.models.ImageModel;
import ar.edu.itba.getaway.services.ImageService;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesController.class);

    @ResponseBody
    @RequestMapping(path = "/{experienceId}/image",
            method = RequestMethod.GET,
            produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getExperiencesImages(@PathVariable("experienceId") final long experienceId) {
        LOGGER.info("Accessed experiences/{}/image GET controller", experienceId);
        ImageModel optionalImageModel = imageService.getImgByExperienceId(experienceId).orElseThrow(ImageNotFoundException::new);
        return optionalImageModel.getImage();
    }


    @RequestMapping(path = "/user/images/profile/{imageId}",
            method = RequestMethod.GET,
            produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getProfileImages(@PathVariable("imageId") long imageId) {
        LOGGER.info("Accessed /user/images/profile/{} GET controller", imageId);
        ImageModel optionalImageModel = imageService.getImgById(imageId).orElseThrow(ImageNotFoundException::new);
        return optionalImageModel.getImage();
    }

}
