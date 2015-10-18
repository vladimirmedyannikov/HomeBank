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

import java.util.List;

import ru.medyannikov.homebank.Adapter.RecycleAdapterOperation;
import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.Model.Operation;
import ru.medyannikov.homebank.R;

/**
 * Created by Vladimir on 13.10.2015.
 */
public class OperationFragment extends Fragment {
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecycleAdapterOperation adapterOperation;
    private SQLiteDataSource dataSource;
    private List<Operation> operationList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.operation_fragment,container,false);
        dataSource = new SQLiteDataSource(this.getContext());
        dataSource.openConnection();

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewOperation);
        recyclerView.setLayoutManager(layoutManager);

        updateOperation();
        /*adapterOperation = new RecycleAdapterOperation(dataSource.getOperations());
        dataSource.closeConnetion();
        recyclerView.setAdapter(adapterOperation);*/

        return view;
    }

    public void updateOperation(){
        if (adapterOperation != null){
            recyclerView.setAdapter(adapterOperation);
            adapterOperation.notifyDataSetChanged();
        }
        else{
            operationList = dataSource.getOperations();
            adapterOperation = new RecycleAdapterOperation(operationList);
            recyclerView.setAdapter(adapterOperation);
        }
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
}
