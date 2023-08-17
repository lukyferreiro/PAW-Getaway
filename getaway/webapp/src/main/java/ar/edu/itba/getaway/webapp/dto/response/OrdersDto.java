package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.OrderByModel;

import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;

public class OrdersDto implements Serializable {
    private String[] orders;
    private URI self;

    public OrdersDto() {
        // Used by Jersey
    }

    public OrdersDto(OrderByModel[] orders, UriInfo uriInfo) {
        this.orders = Arrays.stream(orders).map(OrderByModel::name).toArray(String[]::new);
        this.self = uriInfo.getBaseUriBuilder().path("experiences").path("orders").build();
    }

    public String[] getOrder() {
        return orders;
    }
    public void setOrder(String[] orders) {
        this.orders = orders;
    }
    public URI getSelf() {
        return self;
    }
    public void setSelf(URI self) {
        this.self = self;
    }
}
