package com.github.barteksc.sample.utilities;

import android.graphics.Typeface;

import es.dmoral.toasty.Toasty;

public class ToastyConfigUtility {

    public static void createInstance() {
       Toasty.Config.getInstance()
                .tintIcon(true)
                .setToastTypeface(Typeface.SANS_SERIF)
                .setTextSize(12)
                .apply();
    }
}
