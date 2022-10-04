package ar.edu.itba.getaway.models;

public class ImageExperienceModel {
    private final Long imageId;
    private final Long experienceId;
    private boolean isCover;

    public ImageExperienceModel(Long imageId, Long experienceId, boolean isCover ) {
        this.imageId = imageId;
        this.experienceId = experienceId;
        this.isCover = isCover;
    }

    public Long getImageId() {
        return imageId;
    }
    public Long getExperienceId() {
        return experienceId;
    }
    public boolean isCover() {
        return isCover;
    }
    public void setIsCover(boolean isCover) {
        this.isCover = isCover;
    }
}