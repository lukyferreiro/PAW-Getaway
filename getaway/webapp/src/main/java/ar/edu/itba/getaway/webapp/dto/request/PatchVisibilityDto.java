package ar.edu.itba.getaway.webapp.dto.request;

import javax.validation.constraints.NotNull;

public class PatchVisibilityDto {

    @NotNull(message = "NotNull")
    private Boolean visibility;

    public Boolean getVisibility() {
        return visibility;
    }
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

}
