package com.example.waqasjutt.promocodewithfragment.PromoCodes.CareemPromoCodes;

import java.io.Serializable;

/**
 * Created by Waqas Jutt on 8/3/2017.
 */

public class GoDetails implements Serializable {

    private String original_title;
    private String overview;
    private String discount;


    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
