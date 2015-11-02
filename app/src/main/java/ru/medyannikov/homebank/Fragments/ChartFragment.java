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
    private static ChartFragment fragment;
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
        leftAxis.addLimitLine(ll);

        LineData data = new LineData(dataSource.getBillXVlas(), dataSource.getBillsDataset());
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(9f);


        lineChart.setData(data);




        return view;
    }

    public static  ChartFragment getInstance(){
        Bundle bundle = new Bundle();
        if (fragment == null){
            fragment = new ChartFragment();
        }
        fragment.setArguments(bundle);
        return fragment;
    }
}
