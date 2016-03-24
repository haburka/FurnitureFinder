package com.robb.furniturefinder;

import android.graphics.Bitmap;

/**
 * Created by Xinhai Xu on 2016/3/24.
 */
class ManageItem {
    String name;
    String description;
    Bitmap bm;

    ManageItem(String name, String desc, Bitmap bm) {
        this.name = name;
        this.description = desc;
        this.bm = bm;
    }
}