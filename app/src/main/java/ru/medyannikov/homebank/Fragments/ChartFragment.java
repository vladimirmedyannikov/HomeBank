package ru.medyannikov.homebank.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import ru.medyannikov.homebank.Eventbus.BusProvider;
import ru.medyannikov.homebank.Eventbus.OperationChangeEvent;
import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.R;

/**
 * Created by Vladimir on 01.11.2015.
 */
public class ChartFragment extends Fragment {
    private LineChart lineChart;
    private SQLiteDataSource dataSource;
    private ArrayList<Entry> listData;
    public  ChartFragment fragment;

    private Bill bill = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (!BusProvider.getInstance().isRegistered(this)) {BusProvider.getInstance().register(this);}
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chart, container, false);
        dataSource = new SQLiteDataSource(view.getContext());
        dataSource.openConnection();

        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        lineChart.setDescription(view.getResources().getString(R.string.descriptionChart));



        return view;
    }

    private void createChart() {
        YAxis leftAxis = lineChart.getAxisLeft();

        LimitLine ll = new LimitLine(100000f, "Good value");
        ll.setLineColor(Color.RED);
        ll.setLineWidth(1f);
        ll.setTextColor(Color.BLACK);
        ll.setTextSize(6f);
        leftAxis.addLimitLine(ll);


        LineData data = updateChart();

        lineChart.setData(data);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createChart();
    }

    @NonNull
    private LineData updateChart() {
        LineData data = null;
        if (bill == null){
            data = new LineData(dataSource.getBillXVlas(), dataSource.getBillsDataset());
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);
        } else {
            data = new LineData(dataSource.getBillXVlas(bill), dataSource.getBillsDataset(bill));
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);
        }
        return data;
    }

    public static  ChartFragment getInstance(){
        Bundle bundle = new Bundle();
        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ChartFragment getInstance(Bill bill){
        ChartFragment fragment = getInstance();
        fragment.setIdBill(bill);
        return fragment;
    }

    public void setIdBill(Bill idBill) {
        this.bill = idBill;
    }

    public void onEvent(OperationChangeEvent event){
        LineData data = updateChart();
        lineChart.setData(data);
    }
}
