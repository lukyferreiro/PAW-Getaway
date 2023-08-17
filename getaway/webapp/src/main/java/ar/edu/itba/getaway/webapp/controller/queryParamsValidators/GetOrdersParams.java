package ar.edu.itba.getaway.webapp.controller.queryParamsValidators;

import ar.edu.itba.getaway.models.OrderByModel;

public class GetOrdersParams {

    public static OrderByModel[] getOrdersByParams(Boolean provider) {

        OrderByModel[] orderByModels;

        if (provider) {
            orderByModels = OrderByModel.getProviderOrderByModel();
        } else {
            orderByModels = OrderByModel.getUserOrderByModel();
        }

        return orderByModels;
    }
}
