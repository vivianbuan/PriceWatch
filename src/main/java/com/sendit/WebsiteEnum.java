package com.sendit;

import org.jsoup.select.Elements;

import java.util.Optional;

public enum WebsiteEnum implements Website{
    ENDEAVOR {
        @Override
        public Optional<Double> getPrice(Elements elements) {
            try {
                Double price = Double.parseDouble(elements.get(0).text().substring(1));
                return Optional.of(price);
            } catch (Exception ignored) {}
            return Optional.empty();
        }
    },
    EVO {
        @Override
        public Optional<Double> getPrice(Elements element) {
            try {
                Double price = Double.parseDouble(element.attr("content").substring(1));
                return Optional.of(price);
            } catch (Exception ignored) {}
            return Optional.empty();
        }
    }


}

interface Website {
    Optional<Double> getPrice(Elements element);
}
