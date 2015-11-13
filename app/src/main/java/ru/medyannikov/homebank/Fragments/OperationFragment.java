package ru.medyannikov.homebank.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.squareup.otto.Subscribe;

import java.util.List;

import ru.medyannikov.homebank.Adapter.RecycleAdapterOperation;
import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.Eventbus.BusProvider;
import ru.medyannikov.homebank.Eventbus.OperationChangeEvent;
import ru.medyannikov.homebank.IntentDialog.OperationIntent;
import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.Model.Operation;
import ru.medyannikov.homebank.R;
import ru.medyannikov.homebank.Utils.ClassUtils;

/**
 * Created by Vladimir on 13.10.2015.
 */
public class OperationFragment extends Fragment {
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecycleAdapterOperation adapterOperation;
    private SQLiteDataSource dataSource;
    private List<Operation> operationList;
    private FloatingActionButton fab;
    private Bill bill = null;

    @Override
    public void onResume() {
        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!BusProvider.getInstance().isRegistered(this)) BusProvider.getInstance().register(this);
        View view = inflater.inflate(R.layout.operation_fragment,container,false);
        dataSource = new SQLiteDataSource(this.getContext());
        dataSource.openConnection();

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewOperation);
        recyclerView.setLayoutManager(layoutManager);

        fab = (FloatingActionButton) view.findViewById(R.id.fabOperation);
        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAction();
            }
        });

        updateOperation();
        /*adapterOperation = new RecycleAdapterOperation(dataSource.getOperations());
        dataSource.closeConnetion();
        recyclerView.setAdapter(adapterOperation);*/

        return view;
    }

    private void fabAction() {
        Intent intent = new Intent(getContext(), OperationIntent.class);

        if (bill != null) {
            intent.putExtra(ClassUtils.INTENT_BILL_INFO, bill.get_id());
            intent.putExtra(ClassUtils.INTENT_ADD_OPERATION,false);
        } else {
            intent.putExtra(ClassUtils.INTENT_ADD_OPERATION,true);
        }
        startActivityForResult(intent, 0);
    }

    public void updateOperation(){
        /*if (adapterOperation != null){
            recyclerView.setAdapter(adapterOperation);
            adapterOperation.notifyDataSetChanged();
        }
        else{*/
        if (bill == null){
            operationList = dataSource.getOperations();
            adapterOperation = new RecycleAdapterOperation(operationList);
            recyclerView.setAdapter(adapterOperation);
        }
        else {
            operationList = dataSource.getOperations(bill.get_id());
            adapterOperation = new RecycleAdapterOperation(operationList);
            recyclerView.setAdapter(adapterOperation);
        }
       //, }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateOperation();
    }

    public static OperationFragment getInstance() {

        Bundle bundle = new Bundle();
        OperationFragment fragment = new OperationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static OperationFragment getInstance(Bill bill) {

        Bundle bundle = new Bundle();
        OperationFragment fragment = new OperationFragment();
        fragment.setArguments(bundle);
        fragment.bill = bill;
        return fragment;
    }

    @Subscribe public void onEvent(OperationChangeEvent event){
        updateOperation();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
