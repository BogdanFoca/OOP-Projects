package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.Category;

import java.util.Objects;

public final class Gift {
    private final String productName;
    private final double price;
    private final Category category;
    @JsonIgnore
    private int quantity;

    public Gift(final String productName, final double price, final Category category,
                final int quantity) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    /**
     *
     */
    public void reduceQuantity() {
        quantity--;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gift gift = (Gift) o;
        return productName.equals(gift.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName);
    }
}
