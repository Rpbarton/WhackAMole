package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout grid;
    private TextView timerCount;
    private TextView textView;
    public decTime dec;
    private int timeLeft;
    public Button clickButton;
    private Drawable hey;
    private ImageView[] imageViews;
    private int moleLocation;
    private Random rand;
    public Handler handler;
    public boolean running;
    public moleMove pos;
    public int points;
    public TextView pointsView;
    public Button button2;
    public int picNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickButton = new Button(this);
        clickButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               pointGain(v);
                                           }
                                       });
        grid = findViewById(R.id.gridLayout);
        textView = findViewById(R.id.textView);
        timerCount = findViewById(R.id.timerCount);
        dec = new decTime();
        timeLeft = 30;
        pointsView = findViewById(R.id.pointsView);
        picNum = 1;
        hey = getDrawable(R.drawable.heycrop);
        imageViews = new ImageView[16];
        rand = new Random();
        handler = new Handler();
        running = false;
        button2 = findViewById(R.id.button2);
        pos = new moleMove();
        points = 0;
        moleLocation = rand.nextInt(16);
        for (int i = 0; i<16; i++){
            imageViews[i] = (ImageView) getLayoutInflater().inflate(R.layout.mole_view, null);
            imageViews[i].setMinimumWidth(270);
            imageViews[i].setMinimumHeight(270);
            if(i == moleLocation) imageViews[i].setImageDrawable(hey);
            grid.addView(imageViews[i]);
        imageViews[i].setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    public void picPressed(View v){
        Intent i = new Intent (this, PictureActivity.class);
        i.putExtra("PICTURE", picNum);
        startActivityForResult(i, 1);
    }

    public void helpPressed(View v){
        Intent i = new Intent (this, HelpActivity.class);
        startActivity(i);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        picNum = data.getIntExtra("PICTURE", 1);
        if(picNum == 1){
            hey = getDrawable(R.drawable.heycrop);
            textView.setText("A MAN HAS FALLEN INTO THE RIVER IN LEGO CITY");
        } else if(picNum == 2){
            hey = getDrawable(R.drawable.moale);
            textView.setText("A MOLE HAS FALLEN INTO THE RIVER IN LEGO CITY");
        } else {
            hey = getDrawable(R.drawable.iceagebaby);
            textView.setText("ICE AGE BABY HAS FALLEN INTO THE RIVER IN LEGO CITY");
        }

    }

    private class moleMove implements Runnable{

        public void run(){
            imageViews[moleLocation].setImageDrawable(null);
            moleLocation = rand.nextInt(16);
            imageViews[moleLocation].setImageDrawable(hey);
            handler.postDelayed(pos, 650);
        }
    }

    public void pointGain(View v) { //called when onClickListener detects a mole being clicked
        if(running == true && (v == imageViews[moleLocation])) {
            points += 100;
            pointsView.setText("Points: " + points);
            imageViews[moleLocation].setImageDrawable(null);
        }
    }
    private class decTime implements Runnable{

        public void run(){
            if(timeLeft > 0) {
                timeLeft--;
                timerCount.setText("" +timeLeft);
                handler.postDelayed(dec, 1000);
            } else {
                running = false;
                handler.removeCallbacks(dec);
                handler.removeCallbacks(pos);
            }
        }
    }

    public void startPressed(View v){
        if(running){
            running = false;
            handler.removeCallbacks(pos);
            handler.removeCallbacks(dec);
        } else {
            running = true;
            timeLeft = 30;
            timerCount.setText("" +timeLeft);
            handler.postDelayed(pos, 650);
            handler.postDelayed(dec, 1000);
        }
    }
}
