package com.example.marcianos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.util.Random;

public class Star {
        Bitmap bala;
        Context context;
        int star_x, star_y;
        int star_velocity;

        public Star(Context context, int star_velocity) {
            this.context = context;
            Random rand = new Random();
            this.star_x = rand.nextInt(GameManager.screenWidth + 1);
            this.star_y = 0;
            this.star_velocity = star_velocity;
        }

        public void move_star(){
            this.star_y += this.star_velocity;
        }

        public int getStar_x() {
            return star_x;
        }

        public int getStar_y() {
            return star_y;
        }

    public int getStar_velocity() {
        return star_velocity;
    }
}
