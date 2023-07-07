package ar.edu.itba.getaway.webapp.controller.util;

import ar.edu.itba.getaway.models.ImageModel;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CacheResponse {


    private CacheResponse() {
        // Avoid instantiation of util class
    }

    public static Response cacheResponse(ImageModel image, Request request) {

        EntityTag eTag = new EntityTag(getImageHash(image.getImage()));
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoCache(true);
        Response.ResponseBuilder response = request.evaluatePreconditions(eTag);
        if (response == null) {
            response = Response
                    .ok(new ByteArrayInputStream(image.getImage()))
                    .tag(eTag)
                    .type(image.getMimeType())
                    .header("Content-Disposition", "inline; filename=" + image.getImageId());
        }
        return response.cacheControl(cacheControl).build();

    }
    private static String getImageHash(byte[] image) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("SHA-256").digest(image);
        } catch (NoSuchAlgorithmException e) {
            hash = null;
        } StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hash) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

}
