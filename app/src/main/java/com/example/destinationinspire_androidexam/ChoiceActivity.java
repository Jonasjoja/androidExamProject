package com.example.destinationinspire_androidexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoiceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
    }

    public void selectCntryPressed(View view){
        // toDO
        // Intent intent = new Intent(this, CLASSTOHANDLESELECTING);
        // STARTACTIVITY

    }

    //Method will go to mapsactivity
    public void feelingLckyPressed(View view){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }




}
