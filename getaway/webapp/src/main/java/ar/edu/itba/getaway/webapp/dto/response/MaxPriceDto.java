package ar.edu.itba.getaway.webapp.dto.response;

public class MaxPriceDto {
    private Double maxPrice;

    public MaxPriceDto() {
        // Used by Jersey
    }

    public MaxPriceDto(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
