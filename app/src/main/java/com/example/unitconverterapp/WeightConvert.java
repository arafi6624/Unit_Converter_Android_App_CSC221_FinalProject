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

public class WeightConvert extends AppCompatActivity {

    private static final String WEIGHT_DATA_FILE = "weight_data.txt";

    // Declaring widgets
    EditText input_enterWeight;
    Spinner select_weightFromUnit, select_weightToUnit;
    Button btn_submitWeight;
    TextView lbl_amountWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_convert);
        getSupportActionBar().setTitle("WEIGHT CONVERTER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initializing widgets
        input_enterWeight = findViewById(R.id.input_enterWeight);
        select_weightFromUnit = findViewById(R.id.select_weightFromUnit);
        select_weightToUnit = findViewById(R.id.select_weightToUnit);
        btn_submitWeight = findViewById(R.id.btn_submitWeight);
        lbl_amountWeight = findViewById(R.id.lbl_amountWeight);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weight_units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_weightFromUnit.setAdapter(adapter);
        select_weightToUnit.setAdapter(adapter);

        load();

        btn_submitWeight.setOnClickListener(view -> {
            convert(input_enterWeight.getText().toString());
            save();
        });
    }

    private void convert(String input) {
        int fromUnit = select_weightFromUnit.getSelectedItemPosition();
        int toUnit = select_weightToUnit.getSelectedItemPosition();
        double inputDouble = Double.parseDouble(input);
        double converted;

        if (fromUnit == 0 || toUnit == 0) {
            lbl_amountWeight.setText("Invalid Units");
        } else if (fromUnit == toUnit) {
            lbl_amountWeight.setText(input);
        } else if (fromUnit == 1 && toUnit == 2) {
            converted = inputDouble / 2.205;
            setTextAmount(converted);
        } else if (fromUnit == 1 && toUnit == 3) {
            converted = inputDouble * 454;
            setTextAmount(converted);
        } else if (fromUnit == 1 && toUnit == 4) {
            converted = inputDouble * 16;
            setTextAmount(converted);
        } else if (fromUnit == 2 && toUnit == 1) {
            converted = inputDouble * 2.205;
            setTextAmount(converted);
        } else if (fromUnit == 2 && toUnit == 3) {
            converted = inputDouble * 1000;
            setTextAmount(converted);
        } else if (fromUnit == 2 && toUnit == 4) {
            converted = inputDouble * 35.274;
            setTextAmount(converted);
        } else if (fromUnit == 3 && toUnit == 1) {
            converted = inputDouble / 454;
            setTextAmount(converted);
        } else if (fromUnit == 3 && toUnit == 2) {
            converted = inputDouble / 1000;
            setTextAmount(converted);
        } else if (fromUnit == 3 && toUnit == 4) {
            converted = inputDouble / 28.35;
            setTextAmount(converted);
        } else if (fromUnit == 4 && toUnit == 1) {
            converted = inputDouble / 16;
            setTextAmount(converted);
        } else if (fromUnit == 4 && toUnit == 2) {
            converted = inputDouble / 35.274;
            setTextAmount(converted);
        } else if (fromUnit == 4 && toUnit == 3) {
            converted = inputDouble * 28.35;
            setTextAmount(converted);
        }
    }

    private void setTextAmount(double converted) {
        lbl_amountWeight.setText(String.format("%.3f", converted));
    }

    public void save(){
        String input = input_enterWeight.getText().toString();
        String fromUnit = String.valueOf(select_weightFromUnit.getSelectedItemPosition());
        String toUnit = String.valueOf(select_weightToUnit.getSelectedItemPosition());
        String result = lbl_amountWeight.getText().toString();

        String []lines = {input, fromUnit, toUnit, result};

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(WEIGHT_DATA_FILE, MODE_PRIVATE);
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
            fis = openFileInput(WEIGHT_DATA_FILE);
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

            input_enterWeight.setText(input);
            select_weightFromUnit.setSelection(fromUnit);
            select_weightToUnit.setSelection(toUnit);
            lbl_amountWeight.setText(result);

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