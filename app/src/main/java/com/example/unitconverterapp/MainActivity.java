package com.example.unitconverterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView weightCard, distanceCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("UNIT CONVERTER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        this.weightCard = (CardView) findViewById(R.id.weightCard);
        this.distanceCard = (CardView) findViewById(R.id.distanceCard);

        weightCard.setOnClickListener(this);
        distanceCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.weightCard:
                intent = new Intent(this,WeightConvert.class);
                startActivity(intent);
                break;
            case R.id.distanceCard:
                intent = new Intent(this,DistanceConvert.class);
                startActivity(intent);
                break;
        }
    }

}