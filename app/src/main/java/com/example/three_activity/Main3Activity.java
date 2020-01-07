package com.example.three_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Main3Activity extends AppCompatActivity {

    int no,page;
    TextView text3;
    PieChartView pieChart;

    SQLiteDatabase db;
    static final String db_name ="testDB";
    static  final  String tb_name="test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        text3 = findViewById(R.id.text3);
        page =3;
        no =0;
        Intent it=getIntent();
        no = it.getIntExtra("page",0);

        text3.setText(no+"→ "+page);
        db  = openOrCreateDatabase(db_name, Context.MODE_PRIVATE,null);

        final String[] lunch = {"undefined", "色情片", "避孕用品", "口服避用藥", "性病治療費"};
        final Integer[] classnumer= new Integer[100];
        final int[] pieColor ={Color.GRAY,Color.BLUE,Color.GREEN,Color.RED,Color.YELLOW};

        ArrayList<SliceValue> slices = new ArrayList<>();

        pieChart = findViewById(R.id.chart);
        for(int i=0;i<5;i++) {

            Cursor c2 = db.rawQuery("SELECT COUNT(*) FROM " + tb_name + " WHERE classname =" + i, null);
            if (c2.moveToFirst()) {
                classnumer[i] = Integer.valueOf(c2.getString(0));
            }
            slices.add(new SliceValue(classnumer[i], pieColor[i]).setLabel(lunch[i]+" "+classnumer[i]));

        }
        /*
        slices.add(new SliceValue(50, Color.BLUE).setLabel("KMT:50"));
        slices.add(new SliceValue(20, Color.GREEN).setLabel("DDP:20"));
        slices.add(new SliceValue(10, Color.RED).setLabel("Communist :10"));
        slices.add(new SliceValue(20, Color.YELLOW).setLabel("New:20"));
        */
        PieChartData chartData = new PieChartData(slices);
        chartData.setHasLabels(true).setValueLabelTextSize(14);
        chartData.setValueLabelBackgroundColor(Color.argb(255,0,0,0));
        chartData.setHasCenterCircle(true).setCenterText1("Cost Type")
                .setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChart.setPieChartData(chartData);

    }



    public void threetoone(View view) {
        Intent it = new Intent(this,MainActivity.class);
        it.putExtra("page",3);
        startActivityForResult(it,page);
    }

    public void threetotwo(View view) {
        Intent it = new Intent(this,MainActivity.class);
        it.putExtra("page",2);
        startActivityForResult(it,page);
    }





}
