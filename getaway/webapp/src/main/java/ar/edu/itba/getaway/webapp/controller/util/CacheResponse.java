package ar.edu.itba.getaway.webapp.controller.util;

import ar.edu.itba.getaway.models.ImageModel;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;

public class CacheResponse {

    private static final int MAX_TIME = 31536000;

    private CacheResponse() {
        // Avoid instantiation of util class
    }

    public static Response cacheResponse(ImageModel image, Request request, boolean isExperience) {

        EntityTag eTag = null;
        if (!isExperience) {
            eTag = new EntityTag(String.valueOf(image.getImageId()));
        }

        final CacheControl cacheControl = new CacheControl();

        if (!isExperience) {
            cacheControl.setNoCache(true);
        } else {
            cacheControl.setNoTransform(false);
            cacheControl.getCacheExtension().put("public", null);
            cacheControl.setMaxAge(MAX_TIME);
            cacheControl.getCacheExtension().put("immutable", null);
        }

        if (!isExperience) {
            Response.ResponseBuilder response = request.evaluatePreconditions(eTag);
            if (response == null) {
                response = Response.ok(new ByteArrayInputStream(image.getImage()))
                        .tag(eTag)
                        .type(image.getMimeType())
                        .header("Content-Disposition", "inline; filename=" + image.getImageId())
                ;
            }
            return response.cacheControl(cacheControl).build();
        } else {
            return Response.ok(new ByteArrayInputStream(image.getImage()))
                    .type(image.getMimeType())
                    .header("Content-Disposition", "inline; filename=" + image.getImageId())
                    .cacheControl(cacheControl)
                    .build();
        }

    }

}