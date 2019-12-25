package com.example.three_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int page,no;
    String name;
    int classname,prize;
    EditText name_edit,price_edit;
    Button Totwo;
    TextView text1;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        page =1;
        Totwo = findViewById(R.id.two);
        text1 = findViewById(R.id.text1);
        name_edit = findViewById(R.id.name_edit);
        price_edit = findViewById(R.id.price_edit);
        Intent it=getIntent();
        no = it.getIntExtra("page",0);
        text1.setText(no+"→ "+page);


        spinner = (Spinner)findViewById(R.id.spinner);
        final String[] lunch = {"魯肉飯", "控肉飯", "雞排飯", "炸醬麵", "水餃"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                lunch);
        spinner.setAdapter(lunchList);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("spinner_item",lunch[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void submit(View view){
        name = name_edit.getText();

    }
    public void totwo(View view) {

        Intent it = new Intent(this,Main2Activity.class);
        it.putExtra("page",1);
        startActivityForResult(it,page);
    }

    public void tothree(View view) {
        Intent it = new Intent(this,Main3Activity.class);
        it.putExtra("page",1);
        startActivityForResult(it,page);
    }



}
