package com.play.edit_dp_and_post.editpost_dp;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utility {
    public static int calculateNoOfColumns(Context context,float columnWidthDp)
    {
        DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
        float screenWidthDp =displayMetrics.widthPixels/displayMetrics.density;
        int noOfColumns=(int)(screenWidthDp/columnWidthDp +0.5);
        return noOfColumns;

    }
}
