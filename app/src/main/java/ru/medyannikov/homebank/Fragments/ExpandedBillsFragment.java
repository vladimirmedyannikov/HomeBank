package ru.medyannikov.homebank.Fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.squareup.otto.Subscribe;

import java.util.List;

import ru.medyannikov.homebank.Activity.ActivityBillInfo;
import ru.medyannikov.homebank.Adapter.AdapterExpandableBill;
import ru.medyannikov.homebank.Adapter.RecycleAdapterBill;
import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.DecorationRecycler.LineDivinerRecycler;
import ru.medyannikov.homebank.Eventbus.BillChangeEvent;
import ru.medyannikov.homebank.Eventbus.BusProvider;
import ru.medyannikov.homebank.Eventbus.OperationChangeEvent;
import ru.medyannikov.homebank.IntentDialog.BillIntent;
import ru.medyannikov.homebank.IntentDialog.OperationIntent;
import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.R;
import ru.medyannikov.homebank.Utils.ClassUtils;

/**
 * Created by Vladimir on 26.09.2015.
 */
public class ExpandedBillsFragment extends Fragment {
    public static final int LAYOUT = R.layout.bills_fragment;
    private RecyclerView recyclerView;
    List<Object> billList;
    private LinearLayoutManager layoutManager;
    private RecycleAdapterBill adapter;
    private FloatingActionButton fab;
    private SQLiteDataSource dataSource;
    private static ExpandedBillsFragment billsFragment;
    public static ExpandedBillsFragment getInstance(){
        Bundle bundle = new Bundle();
        if (billsFragment == null) billsFragment = new ExpandedBillsFragment();
        billsFragment.setArguments(bundle);
        return billsFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        dataSource = new SQLiteDataSource(getContext().getApplicationContext());
        dataSource.openConnection();

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!BusProvider.getInstance().isRegistered(this)) BusProvider.getInstance().register(this);
        View view = inflater.inflate(LAYOUT,container,false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewBills);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        fab.setKeepScreenOn(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAction();
            }
        });
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Drawable draw = getContext().getResources().getDrawable(R.drawable.line_diviner);
        recyclerView.addItemDecoration(new LineDivinerRecycler(draw));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //billList = new ArrayList<Bill>();

        /*adapter = new RecycleAdapterBill(billList);
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);*/
        return view;
    }

    private void fabAction() {
        Intent intent = new Intent(getContext(), BillIntent.class);
        startActivityForResult(intent,1);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        updateItems();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void updateItems(){
        billList = dataSource.getExplandedBills();
        AdapterExpandableBill adapterExpandableBill = new AdapterExpandableBill(billList);
        recyclerView.setAdapter(adapterExpandableBill);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateItems();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId())
        {
            case 1:
                //Toast.makeText(this.getContext(), "Info " + item.getGroupId(), Toast.LENGTH_SHORT).show();
                //Snackbar.make(this.getView(), "Info " + item.getGroupId() + " " + billList.get(item.getGroupId()).getName(), Snackbar.LENGTH_SHORT).show();
                intent = new Intent(getContext(), ActivityBillInfo.class);
                intent.putExtra(ClassUtils.INTENT_BILL_INFO,item.getGroupId());
                startActivity(intent);
                break;
            case 2:
                //Toast.makeText(this.getContext(), "Delete " + item.getGroupId(), Toast.LENGTH_SHORT).show();
                //Snackbar.make(this.getView(), "Add operation " + item.getGroupId() + " " + billList.get(item.getGroupId()).getName(), Snackbar.LENGTH_SHORT).show();
                intent = new Intent(getContext(), OperationIntent.class);
                intent.putExtra(ClassUtils.INTENT_ADD_OPERATION,false);
                intent.putExtra(ClassUtils.INTENT_BILL_INFO, item.getGroupId());
                startActivityForResult(intent, item.getGroupId());
                break;
            case 3:
                //Toast.makeText(this.getContext(), "Delete " + item.getGroupId(), Toast.LENGTH_SHORT).show();
               // Snackbar.make(this.getView(), "Delete " + item.getGroupId() + " " + billList.get(item.getGroupId()).getName(), Snackbar.LENGTH_SHORT).show();
                break;
 /*           case 4:
                ((Bill)dataSource.getExplandedBills().get(item.getGroupId())).setE
                break;*/
        }
        return true;
    }
    @Subscribe
    public void onEvent(OperationChangeEvent event){
        updateItems();
    }
    public void onEvent(BillChangeEvent event){ updateItems(); }
}
