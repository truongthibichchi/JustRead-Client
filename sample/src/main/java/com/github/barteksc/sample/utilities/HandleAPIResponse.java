package com.github.barteksc.sample.utilities;

import android.content.Context;
import android.widget.Toast;

import com.github.barteksc.sample.constant.ConstString;

import java.io.IOException;

import es.dmoral.toasty.Toasty;

public class HandleAPIResponse {

    public static void handleFailureResponse(Context context, Throwable t) {
        if (t instanceof IOException) {
            Toasty.error(context, ConstString.FAILURE_STATUS, Toast.LENGTH_SHORT, true).show();
            t.printStackTrace();
        }
        else {
            Toasty.error(context, ConstString.FAILURE_STATUS, Toast.LENGTH_SHORT, true).show();
            t.printStackTrace();
        }
    }
}
