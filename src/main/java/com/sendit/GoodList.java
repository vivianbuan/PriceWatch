package com.sendit;

import java.util.ArrayList;
import java.util.List;

public class GoodList {

    public static List<GoodLinkInfo> getGoodList() {
        return new ArrayList<>() {{
            addAll(getHardGoodList());
            addAll(getSoftGoodList());
        }};
    }

    private static List<GoodLinkInfo> getHardGoodList() {
        return new ArrayList<>() {{
            add(new GoodLinkInfo("https://usa.endeavorsnowboards.com/collections/snowboards/products/2021-pioneer",
                    "span.current_price > span.money", WebsiteEnum.ENDEAVOR));
        }};
    }

    private static List<GoodLinkInfo> getSoftGoodList() {
        return new ArrayList<>() {{
            add(new GoodLinkInfo("https://www.evo.com/mittens/oyuki-chika-gore-tex-mitts-womens",
                    "meta[name=twitter:data1]", WebsiteEnum.EVO));
        }};
    }
}
