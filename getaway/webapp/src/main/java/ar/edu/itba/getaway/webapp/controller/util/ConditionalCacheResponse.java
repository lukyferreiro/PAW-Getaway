package ar.edu.itba.getaway.webapp.controller.util;

import ar.edu.itba.getaway.models.ImageModel;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;

public class ConditionalCacheResponse {

    private ConditionalCacheResponse() {
        // Avoid instantiation of util class
    }

    public static Response conditionalCacheResponse(ImageModel image, Request request) {
        EntityTag eTag = new EntityTag(String.valueOf(image.getImageId()));
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);

        Response.ResponseBuilder response = request.evaluatePreconditions(eTag);
        if (response == null) {
            return Response.ok(new ByteArrayInputStream(image.getImage()))
                    .cacheControl(cacheControl).tag(eTag).type(image.getMimeType()).build();
        }

        return response.build();
    }

}
