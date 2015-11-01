package ru.medyannikov.homebank.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.R;

/**
 * Created by Vladimir on 01.11.2015.
 */
public class ChartFragment extends Fragment {
    private LineChart lineChart;
    private SQLiteDataSource dataSource;
    private ArrayList<Entry> listData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chart,container,false);
        dataSource = new SQLiteDataSource(view.getContext());
        dataSource.openConnection();

        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        lineChart.setDescription(view.getResources().getString(R.string.descriptionChart));

        YAxis leftAxis = lineChart.getAxisLeft();

        LimitLine ll = new LimitLine(140f, "Critical Blood Pressure");
        ll.setLineColor(Color.RED);
        ll.setLineWidth(4f);
        ll.setTextColor(Color.BLACK);
        ll.setTextSize(12f);
// .. and more styling options

        leftAxis.addLimitLine(ll);

//new String [] {"1", "2","50"}


        /*LineDataSet lineDataSet = new LineDataSet(dataSource.getBillEntrys(),"Bills");
        LineData lineData = new LineData();
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setColor(ColorTemplate.getHoloBlue());
        lineDataSet.setCircleColor(Color.WHITE);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setCircleSize(3f);
        lineDataSet.setFillAlpha(65);
        lineDataSet.setFillColor(ColorTemplate.getHoloBlue());
        lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));
        lineDataSet.setDrawCircleHole(false);

        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);*/
        int range = 5;
        int count = 5;

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult) + 50;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals1.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, "DataSet 1");

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range;
            float val = (float) (Math.random() * mult) + 450;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals2.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set2 = new LineDataSet(yVals2, "DataSet 2");


        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set2);
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSource.getBillXVlas(), dataSource.getBillsDataset());
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        lineChart.setData(data);




        return view;
    }

    public static  ChartFragment getInstance(){
        Bundle bundle = new Bundle();
        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
