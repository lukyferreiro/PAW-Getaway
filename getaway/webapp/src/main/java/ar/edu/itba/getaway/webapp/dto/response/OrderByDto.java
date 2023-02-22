package ar.edu.itba.getaway.webapp.dto.response;

import ar.edu.itba.getaway.models.OrderByModel;

import java.util.Collection;
import java.util.stream.Collectors;

public class OrderByDto {
    private OrderByModel order;

    public static Collection<OrderByDto> mapOrderByToDto(Collection<OrderByModel> orderByModels) {
        return orderByModels.stream().map(OrderByDto::new).collect(Collectors.toList());
    }

    public OrderByDto() {
        // Used by Jersey
    }

    public OrderByDto(OrderByModel order) {
        this.order = order;
    }

    public OrderByModel getOrder() {
        return order;
    }

    public void setOrder(OrderByModel order) {
        this.order = order;
    }
}
