package com.example.marcianos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.Random;

public class Enemigo {
    Context context;
    Bitmap enemigo;
    int enemy_x, enemy_y;
    int enemy_velocity;

    public Enemigo (Context context){
        this.context = context;
        enemigo = BitmapFactory.decodeResource(context.getResources(), R.drawable.nave_enemiga);
        enemigo = getResizedBitmap(enemigo,200,200);
        enemy_x = GameManager.screenWidth/2;
        enemy_y = 0;
        enemy_velocity = 15;
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

    public int enememy_width() {
        return enemigo.getWidth();
    }

    public void move () {
        enemy_x = enemy_x+enemy_velocity;
        final int min = 0;
        final int max = 1000;
        final int random = new Random().nextInt((max - min) + 1) + min;
        if(random < 17 && enemy_x > 100)
            enemy_velocity = enemy_velocity*-1;
        if (enemy_x >= GameManager.screenWidth - enememy_width() )
            enemy_velocity = enemy_velocity*-1;
        if (enemy_x  <= 0 )
            enemy_velocity = enemy_velocity*-1;
    }
    public void change_direction() {
        enemy_velocity= enemy_velocity*-1;
    }
    public int enemy_height() {
        return enemigo.getHeight();
    }
    public int enemy_width() {
        return enemigo.getWidth();
    }

    public Bitmap getEnemigo() {
        return enemigo;
    }
    public int getEnemy_x() {
        return enemy_x;
    }

    public int getEnemy_y() {
        return enemy_y;
    }
}
