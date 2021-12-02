package entities;

import enums.Category;

public final class Gift {
    private final String productName;
    private final double price;
    private final Category category;

    public Gift(String productName, double price, Category category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }
}
