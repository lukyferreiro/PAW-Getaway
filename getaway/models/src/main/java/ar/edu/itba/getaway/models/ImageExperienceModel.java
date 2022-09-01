package ar.edu.itba.getaway.models;

public class ImageModel {
    private final long imageId;
    private final long experienceId;
    private boolean isCover;

    public ImageModel(long imageId, long experienceId, boolean isCover ) {
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

    public void setIsCover(booleann isCover) {
        this.isCover = isCover;
    }
}