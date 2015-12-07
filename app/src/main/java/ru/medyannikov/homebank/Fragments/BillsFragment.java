package ru.medyannikov.homebank.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import ru.medyannikov.homebank.Adapter.RecycleAdapterBill;
import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
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
public class BillsFragment extends Fragment {
    public static final int LAYOUT = R.layout.bills_fragment;
    private RecyclerView recyclerView;
    List<Bill> billList;
    private LinearLayoutManager layoutManager;
    private RecycleAdapterBill adapter;
    private FloatingActionButton fab;
    private SQLiteDataSource dataSource;
    private static BillsFragment billsFragment;
    public static BillsFragment getInstance(){
        Bundle bundle = new Bundle();
        if (billsFragment == null) billsFragment = new BillsFragment();
        billsFragment.setArguments(bundle);
        return billsFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BusProvider.getInstance().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!BusProvider.getInstance().isRegistered(this)) BusProvider.getInstance().register(this);
        View view = inflater.inflate(LAYOUT,container,false);

        dataSource = new SQLiteDataSource(getContext());
        dataSource.openConnection();

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
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
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
        /*billList = dataSource.getBills();
        adapter = new RecycleAdapterBill(billList);
        recyclerView.setAdapter(adapter);*/
        /*if (adapter != null) {
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else{*/
            billList = dataSource.getBills();
            adapter = new RecycleAdapterBill(billList);
            recyclerView.setAdapter(adapter);
       // }
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
                intent.putExtra(ClassUtils.INTENT_BILL_INFO, billList.get(item.getGroupId()).getId());
                startActivityForResult(intent, item.getGroupId());
                break;
            case 3:
                //Toast.makeText(this.getContext(), "Delete " + item.getGroupId(), Toast.LENGTH_SHORT).show();
                Snackbar.make(this.getView(), "Delete " + item.getGroupId() + " " + billList.get(item.getGroupId()).getName(), Snackbar.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    @Subscribe
    public void onEvent(OperationChangeEvent event){
        updateItems();
    }
    public void onEvent(BillChangeEvent event){ updateItems(); }
}
