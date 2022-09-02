package ar.edu.itba.getaway.models;

public class TagModel {
    private final long tagId;
    private final String tagName;

    public TagModel(long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public long getId() {
        return tagId;
    }

    public String getName() {
        return tagName;
    }
}