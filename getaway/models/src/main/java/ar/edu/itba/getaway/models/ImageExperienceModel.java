package ar.edu.itba.getaway.models;

public class ImageExperienceModel {
    private final long imageId;
    private final long experienceId;
    private boolean isCover;

    public ImageExperienceModel(long imageId, long experienceId, boolean isCover ) {
        this.imageId = imageId;
        this.experienceId = experienceId;
        this.isCover = isCover;
    }

    public long getId() {
        return imageId;
    }

    public long getExperienceId() {
        return experienceId;
    }

    public boolean isCover() {
        return isCover;
    }

    public void setIsCover(boolean isCover) {
        this.isCover = isCover;
    }
}