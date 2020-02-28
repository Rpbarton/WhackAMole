package com.example.whackamole;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class PictureActivity extends AppCompatActivity {
    private RadioButton legoGuyButton;
    private RadioButton moleButton;
    private RadioButton babyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_change);
        legoGuyButton = findViewById(R.id.legoGuyButton);
        moleButton = findViewById(R.id.moleButton);
        babyButton = findViewById(R.id.babyButton);
        Intent i = getIntent();
        int picNum = i.getIntExtra("PICTURE", 1);
        if(picNum == 1){
            legoGuyButton.setChecked(true);
        } else if(picNum == 2){
            moleButton.setChecked(true);
        } else if(picNum == 3){
            babyButton.setChecked(true);
        }
    }


    public void onBackPressed() {
        int picNum;
        if (legoGuyButton.isChecked()) {
            picNum = 1;
        } else if (moleButton.isChecked()){
            picNum = 2;
        } else {
            picNum = 3;
        }
        Intent i = new Intent();
        i.putExtra("PICTURE", picNum);
        setResult(RESULT_OK, i);
        finish();
    }
}
