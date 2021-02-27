package com.sendit;

public class GoodPriceInfo {
    String title;
    Double price;

    public GoodPriceInfo(String title, Double price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "GoodPriceInfo{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
