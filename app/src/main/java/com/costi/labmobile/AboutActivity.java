package com.costi.labmobile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    PieChart pieChart;
    ArrayList<PieEntry> pieEntries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView call = (TextView) findViewById(R.id.call_us);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0757189393"));
                if (ActivityCompat.checkSelfPermission(AboutActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    Toast.makeText(AboutActivity.this, "You don't granted app the permision to make calls", Toast.LENGTH_SHORT).show();
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });

        TextView email = (TextView) findViewById(R.id.email_us);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:" + "oncioiu.costi@gmail.com"));
                startActivity(intent);
            }
        });

        pieChart = (PieChart) findViewById(R.id.pie_chart);
        pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(70,"Very satisfied"));
        pieEntries.add(new PieEntry(33,"Satisfied"));
        pieEntries.add(new PieEntry(8,"Not very satisfied"));
        pieEntries.add(new PieEntry(2,"Not satisfied"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"How satisfied are our clients");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);




        pieChart.setDrawEntryLabels(false);
//        pieChart.setUsePercentValues(true);

        pieChart.animateY(1000);

        pieChart.setData(pieData);



    }
}
