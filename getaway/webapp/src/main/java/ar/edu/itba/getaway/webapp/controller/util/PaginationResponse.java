package ar.edu.itba.getaway.webapp.controller.util;

import ar.edu.itba.getaway.models.pagination.Page;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public class PaginationResponse {
    private PaginationResponse() {
        // Avoid instantiation of util class
    }

    public static <T, K> Response createPaginationResponse(Page<T> results, GenericEntity<K> resultsDto, UriBuilder uriBuilder) {
        if (results.getContent().isEmpty()) {
            if (results.getCurrentPage() == 1) {
                return Response.noContent().build();
            } else {
                return Response.status(NOT_FOUND).build();
            }
        }

        final Response.ResponseBuilder response = Response.ok(resultsDto);
        addPaginationLinks(response, results, uriBuilder);
        return response.build();
    }

    private static <T> void addPaginationLinks(Response.ResponseBuilder responseBuilder, Page<T> results, UriBuilder uriBuilder) {

        final int page = results.getCurrentPage();

        final int first = 1;
        final int last = results.getMaxPage();
        final int prev = page - 1;
        final int next = page + 1;

        responseBuilder.link(uriBuilder.clone().replaceQueryParam("page", first).build(), "first");

        if (first != last) {
            responseBuilder.link(uriBuilder.clone().replaceQueryParam("page", last).build(), "last");
        }

        if (page != first) {
            responseBuilder.link(uriBuilder.clone().replaceQueryParam("page", prev).build(), "prev");
        }

        if (page != last) {
            responseBuilder.link(uriBuilder.clone().replaceQueryParam("page", next).build(), "next");
        }
        responseBuilder.header("X-Total-Pages", results.getTotalPages());
    }
}
