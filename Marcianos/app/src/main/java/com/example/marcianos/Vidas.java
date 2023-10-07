package com.example.marcianos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Vidas {
    Bitmap corazones_aliados;
    Bitmap corazones_enemigos;
    int vidas_aliado;
    int vidas_enemigo;
    Context context;

    public Vidas(Context context, int vidas_aliado, int vidas_enemigo){
        this.context = context;
        this.vidas_aliado = vidas_aliado;
        this.vidas_enemigo = vidas_enemigo;
        corazones_aliados = BitmapFactory.decodeResource(context.getResources(), R.drawable.corazon_aliado);
        corazones_aliados = getResizedBitmap(corazones_aliados, 50, 50);
        corazones_enemigos = BitmapFactory.decodeResource(context.getResources(), R.drawable.corazon_enemigo);
        corazones_enemigos = getResizedBitmap(corazones_enemigos, 50, 50);
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

    public void draw_vidas(Canvas canvas , int ancho ){
        for(int i=vidas_aliado; i>=1; i--){
            canvas.drawBitmap(corazones_aliados, ancho - corazones_aliados.getWidth() * i, 0, null);
        }
        for(int i=vidas_enemigo; i>=1; i--){
            canvas.drawBitmap(corazones_enemigos, corazones_enemigos.getWidth() * i, 0, null);
        }
    }

    public void hit_enemy(){
        vidas_enemigo--;
    }
    public void hit_me(){
        vidas_aliado--;
    }

    public int getVidas_aliado() {
        return vidas_aliado;
    }

    public int getVidas_enemigo() {
        return vidas_enemigo;
    }
}
