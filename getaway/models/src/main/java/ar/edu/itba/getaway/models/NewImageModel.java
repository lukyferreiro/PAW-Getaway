package ar.edu.itba.getaway.models;

public class NewImageModel {

    private byte[] image;
    private String mimeType;

    public NewImageModel(byte[] image, String mimeType) {
        this.image = image;
        this.mimeType = mimeType;
    }

    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public String getMimeType() {
        return mimeType;
    }
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
