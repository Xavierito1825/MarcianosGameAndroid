package com.example.marcianos;

import static java.util.Collections.rotate;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Random;

public class GameManager extends View {
    Context context;
    Bitmap fondo;
    static int screenWidth, screenHeight;
    MyNave myNave;
    Enemigo enemigo;
    ArrayList<Bala> balas_enemigas, balas_aliadas;
    ArrayList<Star> estrellas;
    int bala_enemiga_velocity;
    int bala_aliada_velocity;
    boolean disparo_enemigo = false;
    boolean disparo_aliado = false;
    int vidas_aliado;
    int vidas_enemigo;
    Vidas vidas;
    Canvas my_canvas;
    int dificulty;

    Bitmap bitmapbala;
    Bitmap bitmapbalaamiga;
    Bitmap bitmapbalaenemiga;
    Bitmap big_star;
    Bitmap star;
    Bitmap giga_star;

    Handler handler;
    boolean stop = false;

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    public GameManager(Context context, int dif){
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        fondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo);
        fondo = Bitmap.createScaledBitmap(fondo, screenWidth,screenHeight,true);
        bitmapbala = BitmapFactory.decodeResource(context.getResources(), R.drawable.bala);
        bitmapbala = Utils.getResizedBitmap(bitmapbala, 50, 50);
        bitmapbalaamiga=Utils.rotate(bitmapbala, 90f);
        bitmapbalaenemiga=Utils.rotate(bitmapbala, -90f);
        giga_star = BitmapFactory.decodeResource(context.getResources(), R.drawable.star);
        giga_star = Utils.getResizedBitmap(giga_star, 200, 200);
        big_star = BitmapFactory.decodeResource(context.getResources(), R.drawable.star);
        big_star = Utils.getResizedBitmap(big_star, 150, 150);
        star = BitmapFactory.decodeResource(context.getResources(), R.drawable.star);
        star = Utils.getResizedBitmap(star, 100, 100);
        myNave = new MyNave(context);
        enemigo = new Enemigo(context);

        dificulty = dif;

        vidas_aliado = 3;
        if(dificulty == 0) {
            vidas_enemigo = 3;
            bala_enemiga_velocity = 10;
            bala_aliada_velocity = 20;
        }
        else if(dificulty == 1) {
            vidas_enemigo = 5;
            bala_enemiga_velocity = 15;
            bala_aliada_velocity = 15;
        }
        else {
            vidas_enemigo = 7;
            bala_enemiga_velocity = 20;
            bala_aliada_velocity = 10;
        }
        vidas = new Vidas(context, vidas_aliado, vidas_enemigo);
        balas_enemigas = new ArrayList<>();
        balas_aliadas = new ArrayList<>();
        estrellas = new ArrayList<>();
        handler = new Handler();

    }


    @Override
    protected void onDraw(Canvas canvas) {

        my_canvas = canvas;

        canvas.drawBitmap(fondo, 0, 0, null);
        //--------------------------------Stars--------------------------------------------------------------
        int min = 0;
        int max = 1000;
        int random = new Random().nextInt((max - min) + 1) + min;
        if(estrellas.size() < 30 && random < 50){
            Star estrella;
            if(random < 30) {
                estrella = new Star(context, 1);
            }
            else if(random < 40){
                estrella = new Star(context, 2);
            }
            else {
                estrella = new Star(context, 3);
            }
            estrellas.add(estrella);
        }
        for (int i = 0; i < estrellas.size(); i++) {
            estrellas.get(i).move_star();
        }
        for (int i = 0; i < estrellas.size(); i++) {
            if(estrellas.size() > 0  && estrellas.get(i).getStar_y() > screenHeight){
                estrellas.remove(i);
                i--;
            } else if(estrellas.size() > 0){
                if(estrellas.get(i).getStar_velocity() == 1) {
                    canvas.drawBitmap(star, estrellas.get(i).getStar_x(), estrellas.get(i).getStar_y(), null);
                }
                else if(estrellas.get(i).getStar_velocity() == 2) {
                    canvas.drawBitmap(big_star, estrellas.get(i).getStar_x(), estrellas.get(i).getStar_y(), null);
                }
                else{
                    canvas.drawBitmap(giga_star, estrellas.get(i).getStar_x(), estrellas.get(i).getStar_y(), null);
                }
            }
        }



        //--------------------------------Movimiento Enemigo------------------------------------------------------------------
        enemigo.move();

        //---------------------------------Disparo Enemigo--------------------------------------------------------------------

        if (disparo_enemigo == false){
            Bala bala_enemiga = new Bala(context, enemigo.getEnemy_x()+enemigo.enemy_width()/2, enemigo.getEnemy_y()+enemigo.enemy_height()/2, bala_enemiga_velocity);
            balas_enemigas.add(bala_enemiga);
            disparo_enemigo = true;
        }

        //if(disparo_enemigo == true){
            for(int i = 0; i<balas_enemigas.size(); i++){
                balas_enemigas.get(i).move_bala_enemiga();
                //canvas.drawBitmap(balas_enemigas.get(i).getBala_enemiga(), balas_enemigas.get(i).getBala_x(), balas_enemigas.get(i).getBala_y(), null);
                for(int j = 0; j<balas_enemigas.size(); j++){
                    if(balas_enemigas.size() > 0 && balas_enemigas.get(j).getBala_x() >= myNave.getMy_x() - myNave.getMyNaveWidth()/2 &&
                            balas_enemigas.get(j).getBala_x() <= myNave.getMy_x() + myNave.getMyNaveWidth() &&
                            balas_enemigas.get(j).getBala_y() <= myNave.getMy_y() + myNave.getMyNaveHeight()/2 &&
                            balas_enemigas.get(j).getBala_y() >= myNave.getMy_y() - myNave.getMyNaveHeight()/2) {

                        vidas.hit_me();

                            balas_enemigas.remove(j);
                            if(balas_enemigas.size() > 0)
                            balas_enemigas.get(j).move_bala_enemiga();

                    }
                    else
                        canvas.drawBitmap(bitmapbalaenemiga, balas_enemigas.get(i).getBala_x(), balas_enemigas.get(i).getBala_y(), null);
                        //canvas.drawBitmap(balas_enemigas.get(j).getBala_enemiga(), balas_enemigas.get(i).getBala_x(), balas_enemigas.get(i).getBala_y(), null);
                }

                if (balas_enemigas.size() > 0 && balas_enemigas.get(i).getBala_y() > screenHeight){
                    balas_enemigas.remove(i);
                    i--;
                } else if(balas_enemigas.size() > 0){
                    canvas.drawBitmap(bitmapbalaenemiga, balas_enemigas.get(i).getBala_x(), balas_enemigas.get(i).getBala_y(), null);
                    //canvas.drawBitmap(balas_enemigas.get(i).getBala_enemiga(), balas_enemigas.get(i).getBala_x(), balas_enemigas.get(i).getBala_y(), null);
                }


            }
            if(dificulty == 2) {
                if (balas_enemigas.get(0).getBala_y() > screenHeight / 3 && balas_enemigas.size() < 3) {
                    if (balas_enemigas.size() == 2) {
                        if (balas_enemigas.get(0).getBala_y() > screenHeight - screenHeight / 3)
                            disparo_enemigo = false;
                    } else {
                        disparo_enemigo = false;
                    }
                }
            }
            else if(dificulty == 1) {
                if (balas_enemigas.get(0).getBala_y() > screenHeight / 2 && balas_enemigas.size() < 2) {
                    disparo_enemigo = false;
                }
            }
            else {
                if (balas_enemigas.size() < 1)
                    disparo_enemigo = false;
            }
        //}

        //----------------------------------------------Disparo Aliado-----------------------------------------------
        if (disparo_aliado == false){
            Bala bala_aliada = new Bala(context, myNave.getMy_x()+myNave.getMyNaveWidth()/2, myNave.getMy_y()+myNave.getMyNaveHeight()/2, bala_aliada_velocity);
            balas_aliadas.add(bala_aliada);
            disparo_aliado = true;
        }

        for(int i = 0; i<balas_aliadas.size(); i++){
            balas_aliadas.get(i).move_bala_aliada();
            //canvas.drawBitmap(balas_aliadas.get(i).getBala_aliada(), balas_aliadas.get(i).getBala_x(), balas_aliadas.get(i).getBala_y(), null);
            for(int j = 0; j<balas_aliadas.size(); j++){
                if(balas_aliadas.size() > 0 && balas_aliadas.get(j).getBala_x() >= enemigo.enemy_x - enemigo.enemy_width()/2 &&
                        balas_aliadas.get(j).getBala_x() <= enemigo.enemy_x + enemigo.enemy_width() &&
                        balas_aliadas.get(j).getBala_y() <= enemigo.enemy_y + enemigo.enemy_height() &&
                        balas_aliadas.get(j).getBala_y() >= enemigo.enemy_y - enemigo.enemy_height()/2) {

                    vidas.hit_enemy();
                    balas_aliadas.remove(j);
                    balas_aliadas.get(j).move_bala_aliada();
                }
                else
                    canvas.drawBitmap(bitmapbalaamiga, balas_aliadas.get(i).getBala_x(), balas_aliadas.get(i).getBala_y(), null);
                    //canvas.drawBitmap(balas_aliadas.get(j).getBala_aliada(), balas_aliadas.get(i).getBala_x(), balas_aliadas.get(i).getBala_y(), null);
            }

            if (balas_aliadas.size() > 0 && balas_aliadas.get(i).getBala_y() < 0){
                balas_aliadas.remove(i);
                i--;
            } else{
                canvas.drawBitmap(bitmapbalaamiga, balas_aliadas.get(i).getBala_x(), balas_aliadas.get(i).getBala_y(), null);
                //canvas.drawBitmap(balas_aliadas.get(i).getBala_aliada(), balas_aliadas.get(i).getBala_x(), balas_aliadas.get(i).getBala_y(), null);
            }


        }

        if(balas_aliadas.get(0).getBala_y() < screenHeight/2 && balas_aliadas.size() < 2){
            disparo_aliado = false;
        }
        //}

        canvas.drawBitmap(enemigo.getEnemigo(), enemigo.getEnemy_x(), enemigo.getEnemy_y(), null);
        canvas.drawBitmap(myNave.getMynave(), myNave.getMy_x(), myNave.getMy_y(), null);
        vidas.draw_vidas(canvas,screenWidth);

        if (vidas.getVidas_aliado() <= 0 || vidas.getVidas_enemigo() <= 0){
            stop = true;
            if(vidas.getVidas_aliado() <= 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("GAME OVER");
                builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restart_game();
                    }
                });
                builder.setNegativeButton("Menú", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context.startActivity(new Intent(context,StartGame.class));
                    }
                });
                builder.setMessage("You lose, your enemy have "+vidas.getVidas_enemigo()+" lives");
                builder.show();
            }
            else{
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setTitle("VICTORIIII");
                builder2.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restart_game();
                    }
                });
                builder2.setNegativeButton("Menú", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context.startActivity(new Intent(context,StartGame.class));
                    }
                });
                builder2.setMessage("You win, you have "+vidas.getVidas_aliado()+" lives");
                builder2.show();
            }

        }
        if(!stop)
            handler.postDelayed(runnable, 15);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();
        //if(touchX >= myNave.getMy_x() && touchX <= myNave.getMy_x()+ myNave.getMyNaveWidth() ){
            myNave.setMy_x(touchX);
        //}

        return true;
    }

    void restart_game(){
        stop = false;
        vidas = new Vidas(context, vidas_aliado, vidas_enemigo);
        disparo_enemigo = false;
        disparo_aliado = false;
        myNave.setMy_x(myNave.getMyNaveWidth()/2);
        enemigo.enemy_x = screenWidth/2;
        for (int i = 0; i < balas_enemigas.size() ; i++) {
            balas_enemigas.remove(i);
            --i;
        }
        for (int i = 0; i < balas_aliadas.size() ; i++) {
            balas_aliadas.remove(i);
            --i;
        }
        handler.postDelayed(runnable, 30);

    }
}
