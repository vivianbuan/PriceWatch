package com.sendit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Optional;

public class PriceExtractor {
    public static Optional<GoodPriceInfo> extract(GoodLinkInfo good) {
        try {
            Document doc = Jsoup.connect(good.url).get();
            Elements priceInfo = doc.select(good.htmlKey);
            return Optional.of(new GoodPriceInfo(doc.title(),
                    good.website.getPrice(priceInfo).get()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
