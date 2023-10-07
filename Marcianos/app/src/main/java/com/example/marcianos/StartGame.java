package com.example.marcianos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartGame extends AppCompatActivity {
    int dificulty = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game);

        Button btn_h = (Button) findViewById(R.id.Hard);
        btn_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hard();
            }
        });

        Button btn_m = (Button) findViewById(R.id.Medium);
        btn_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medium();
            }
        });

        Button btn_e = (Button) findViewById(R.id.Easy);
        btn_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Easy();
            }
        });
    }

    void Easy(){
        dificulty = 0;
        TextView textView = (TextView) findViewById(R.id.Texto);
        textView.setText("Dificulty: EASY");
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.BLUE);
        textView.setTextScaleX(2);
    }
    void Medium(){
        dificulty = 1;
        TextView textView = (TextView) findViewById(R.id.Texto);
        textView.setText("Dificulty: MEDIUM");
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.YELLOW);
        textView.setTextScaleX(2);
    }
    void Hard(){
        dificulty = 2;
        TextView textView = (TextView) findViewById(R.id.Texto);
        textView.setText("Dificulty: HARD");
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.RED);
        textView.setTextScaleX(2);
    }
    public int getDificulty() {
        return dificulty;
    }

    public void GameStart(View view) {
        if(dificulty >= 0) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("dificulty",dificulty);
            startActivity(intent);
            finish();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Dificulty not selected", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
    }
}
