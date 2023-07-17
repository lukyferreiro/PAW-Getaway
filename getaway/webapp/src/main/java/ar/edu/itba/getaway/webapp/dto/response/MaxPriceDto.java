package ar.edu.itba.getaway.webapp.dto.response;

import javax.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;

public class MaxPriceDto implements Serializable {
    private Double maxPrice;
    private URI self;

    public MaxPriceDto() {
        // Used by Jersey
    }

    public MaxPriceDto(Double maxPrice, UriInfo uriInfo) {
        this.maxPrice = maxPrice;
        this.self = uriInfo.getBaseUriBuilder().path("experiences").path("maxPrice").build();
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
    public URI getSelf() {
        return self;
    }
    public void setSelf(URI self) {
        this.self = self;
    }
}
