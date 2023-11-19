package com.evan.HTTPCatEmailer.model;

public class HttpStatus {

    private Long id;

    private String status;

    private byte[] image;

    public HttpStatus(){
    }

    public HttpStatus(String status, byte[] image){
        this.status = status;
        this.image = image;
    }

    public byte[] getImage(){
        return image;
    }
    public void setImage(byte[] image){
        this.image = image;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
