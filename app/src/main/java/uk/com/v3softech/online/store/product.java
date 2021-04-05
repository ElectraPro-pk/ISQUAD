package uk.com.v3softech.online.store;

public class product {
    private String name,description,image,price,off,type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public product(){

    }
    public product(String name, String description, String image, String price, String off,String type) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.off = off;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }
}
