package com.sendit;

import org.junit.jupiter.api.Test;

class PriceExtractorTest {

    @Test
    void testExtractEvo() {
        GoodLinkInfo linkInfo =
                new GoodLinkInfo("https://www.evo.com/mittens/oyuki-chika-gore-tex-mitts-womens",
                        "meta[name=twitter:data1]", WebsiteEnum.EVO);
        System.out.println(PriceExtractor.extract(linkInfo).get().toString());
    }

    @Test
    void testExtractEndeavor() {
        GoodLinkInfo linkInfo =
                new GoodLinkInfo("https://usa.endeavorsnowboards.com/collections/snowboards/products/2021-pioneer",
                        "span.current_price > span.money", WebsiteEnum.ENDEAVOR);
        System.out.println(PriceExtractor.extract(linkInfo).get().toString());
    }

}