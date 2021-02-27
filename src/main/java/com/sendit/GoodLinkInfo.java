package com.sendit;

/* Class to store the url & html information of product. */
public class GoodLinkInfo {
    String url;
    String htmlKey;
    Website website;

    public GoodLinkInfo(String url, String htmlKey, Website website) {
        this.url = url;
        this.htmlKey = htmlKey;
        this.website = website;
    }
}
