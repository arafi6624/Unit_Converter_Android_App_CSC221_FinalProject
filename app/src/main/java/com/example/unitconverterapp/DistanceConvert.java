package com.example.unitconverterapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DistanceConvert extends AppCompatActivity{

    private static final String DISTANCE_DATA_FILE = "distance_data.txt";

    // Declaring widgets
    EditText input_enterDistance;
    Spinner select_distanceFromUnit, select_distanceToUnit;
    Button btn_submit;
    TextView lbl_amountDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_convert);
        getSupportActionBar().setTitle("DISTANCE CONVERTER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initializing widgets
        input_enterDistance = findViewById(R.id.input_enterDistance);
        select_distanceFromUnit = findViewById(R.id.select_distanceFromUnit);
        select_distanceToUnit = findViewById(R.id.select_distanceToUnit);
        btn_submit = findViewById(R.id.btn_submit);
        lbl_amountDistance = findViewById(R.id.lbl_amountDistance);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.length_units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_distanceFromUnit.setAdapter(adapter);
        select_distanceToUnit.setAdapter(adapter);

        load();

        btn_submit.setOnClickListener(view -> {
            convert(input_enterDistance.getText().toString());
            save();
        });
    }

    public void convert(String input){
        int fromUnit = select_distanceFromUnit.getSelectedItemPosition();
        int toUnit = select_distanceToUnit.getSelectedItemPosition();
        double enteredAmountDouble = Double.parseDouble(input);
        double converted;

        if (fromUnit == 0 || toUnit == 0){
            lbl_amountDistance.setText("Invalid Units");
        }else if(fromUnit == toUnit){
            lbl_amountDistance.setText(input_enterDistance.getText());
        }else if(fromUnit == 1 && toUnit == 2){
            converted = enteredAmountDouble * 1.609;
            setAmountText(converted);
        }else if(fromUnit == 1 && toUnit == 3) {
            converted = enteredAmountDouble * 1609;
            setAmountText(converted);
        }else if(fromUnit == 1 && toUnit == 4){
            converted = enteredAmountDouble * 5280;
            setAmountText(converted);
        }else if(fromUnit == 2 && toUnit == 1){
            converted = enteredAmountDouble / 1.609;
            setAmountText(converted);
        }else if(fromUnit == 2 && toUnit == 3){
            converted = enteredAmountDouble * 1000;
            setAmountText(converted);
        }else if(fromUnit == 2 && toUnit == 4){
            converted = enteredAmountDouble * 3281;
            setAmountText(converted);
        }else if(fromUnit == 3 && toUnit == 1){
            converted = enteredAmountDouble / 1609;
            setAmountText(converted);
        }else if(fromUnit == 3 && toUnit == 2){
            converted = enteredAmountDouble / 1000;
            setAmountText(converted);
        }else if(fromUnit == 3 && toUnit == 4){
            converted = enteredAmountDouble * 3.281;
            setAmountText(converted);
        }else if(fromUnit == 4 && toUnit == 1){
            converted = enteredAmountDouble / 5280;
            setAmountText(converted);
        }else if(fromUnit == 4 && toUnit == 2){
            converted = enteredAmountDouble / 3281;
            setAmountText(converted);
        }else if(fromUnit == 4 && toUnit == 3){
            converted = enteredAmountDouble / 3.281;
            setAmountText(converted);
        }
    }

    private void setAmountText(double converted) {
        lbl_amountDistance.setText(String.format("%.3f",converted));
    }

    public void save(){
        String input = input_enterDistance.getText().toString();
        String fromUnit = String.valueOf(select_distanceFromUnit.getSelectedItemPosition());
        String toUnit = String.valueOf(select_distanceToUnit.getSelectedItemPosition());
        String result = lbl_amountDistance.getText().toString();

        String []lines = {input, fromUnit, toUnit, result};

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(DISTANCE_DATA_FILE, MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for(int i = 0; i < lines.length; ++i){
                bw.write(lines[i]);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(){
        FileInputStream fis = null;
        try {
            fis = openFileInput(DISTANCE_DATA_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while((text = br.readLine()) != null){
                sb.append(text).append(" ");
            }
            String savedData = sb.toString();
            String []data = savedData.split(" ");

            String input = data[0];
            int fromUnit = Integer.parseInt(data[1]);
            int toUnit = Integer.parseInt(data[2]);
            String result = data[3];

            input_enterDistance.setText(input);
            select_distanceFromUnit.setSelection(fromUnit);
            select_distanceToUnit.setSelection(toUnit);
            lbl_amountDistance.setText(result);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}