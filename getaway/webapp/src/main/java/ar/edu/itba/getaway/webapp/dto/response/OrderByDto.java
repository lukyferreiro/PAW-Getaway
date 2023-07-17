package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.OrderByModel;
import ar.edu.itba.getaway.models.ReviewModel;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

public class OrderByDto implements Serializable {
    private OrderByModel order;
    private URI self;

    public static Collection<OrderByDto> mapOrderByToDto(Collection<OrderByModel> orderByModels, UriInfo uriInfo) {
        return orderByModels.stream().map(order -> new OrderByDto(order, uriInfo)).collect(Collectors.toList());
    }

    public OrderByDto() {
        // Used by Jersey
    }

    public OrderByDto(OrderByModel order, UriInfo uriInfo) {
        this.order = order;
        this.self = uriInfo.getBaseUriBuilder().path("experiences").path("orders").build();
    }

    public OrderByModel getOrder() {
        return order;
    }

    public void setOrder(OrderByModel order) {
        this.order = order;
    }
    public URI getSelf() {
        return self;
    }
    public void setSelf(URI self) {
        this.self = self;
    }
}
