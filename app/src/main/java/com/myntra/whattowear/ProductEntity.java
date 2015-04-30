package com.myntra.whattowear;

import android.graphics.drawable.Drawable;

/**
 * Created by 8302 on 4/25/15.
 */
public class ProductEntity {
    public Drawable imageResId;
    public String titleResId;
    public String url;
    public boolean inWardrobe;

    public ProductEntity (Drawable imageResId, String titleResId, String url, boolean inWardrobe){
        this.imageResId = imageResId;
        this.titleResId = titleResId;
        this.url = url;
        this.inWardrobe = inWardrobe;
    }
}
