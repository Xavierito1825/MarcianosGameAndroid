package com.example.marcianos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.Random;

public class MyNave {
    Context context;
    Bitmap mynave;
    int my_x, my_y;
    Random random;

    public MyNave(Context context) {
        this.context = context;
        mynave = BitmapFactory.decodeResource(context.getResources(), R.drawable.nave_aliada);
        mynave = getResizedBitmap(mynave, 150, 150);
        my_x = 0;
        my_y = GameManager.screenHeight - mynave.getHeight();
        System.out.println(GameManager.screenHeight+"alto de pantalla  "+mynave.getHeight()+"alto nave");
    }

    Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public int getMyNaveWidth(){
        return mynave.getWidth();
    }
    public int getMyNaveHeight() {
        return mynave.getHeight();
    }

    public Bitmap getMynave() {
        return mynave;
    }

    public int getMy_x() {
        return my_x;
    }

    public int getMy_y() {
        return my_y;
    }

    public void setMy_x(int my_x) {
        this.my_x = my_x - getMyNaveWidth()/2;
    }
}
