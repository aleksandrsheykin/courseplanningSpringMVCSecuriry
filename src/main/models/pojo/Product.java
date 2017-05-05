package main.models.pojo;

/**
 * Created by admin on 19.04.2017.
 */
public class Product {
    private Integer idProduct;
    private String name;
    private String description;
    private int user_id;

    public Product(Integer idProduct, String name, String description, int user_id) {
        this.idProduct = idProduct;
        this.name = name;
        this.description = description;
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
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
}