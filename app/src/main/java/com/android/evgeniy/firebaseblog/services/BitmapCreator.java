package com.android.evgeniy.firebaseblog.services;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.MarkerOptions;

public final class BitmapCreator {
    public static Bitmap getBitmap(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap((int) (drawable.getIntrinsicWidth() * 1.2), (int) (drawable.getIntrinsicHeight() * 1.2), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 1.2), (int) (drawable.getIntrinsicHeight() * 1.2));
        drawable.draw(canvas);

        return bitmap;
    }
}
