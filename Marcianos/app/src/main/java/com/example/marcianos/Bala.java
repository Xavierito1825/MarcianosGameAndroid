package com.example.marcianos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Bala {
        Bitmap bala;
        Context context;
        int bala_x, bala_y;
        int bala_velocity;

        public Bala(Context context, int bala_x, int bala_y, int bala_velocity) {
            this.context = context;
            //bala = BitmapFactory.decodeResource(context.getResources(), R.drawable.bala);
            //bala = getResizedBitmap(bala, 50, 50);
            this.bala_x = bala_x;
            this.bala_y = bala_y;
            this.bala_velocity = bala_velocity;
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
        public Bitmap rotate(Bitmap bitmap, float grados) {
            Matrix matrix = new Matrix();
            matrix.postRotate(grados);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        public void move_bala_enemiga(){
            this.bala_y += this.bala_velocity;
        }
        public void move_bala_aliada(){
            this.bala_y -= this.bala_velocity;
        }

        public int getBala_x() {
            return bala_x;
        }

        public int getBala_y() {
            return bala_y;
        }

        public int getShotWidth() {
            return bala.getWidth();
        }
        public int getShotHeight() {
            return bala.getHeight();
        }
        public Bitmap getBala_enemiga(){
            return rotate(bala, -90f);
        }
        public Bitmap getBala_aliada(){
            return rotate(bala,90f);
        }


}
