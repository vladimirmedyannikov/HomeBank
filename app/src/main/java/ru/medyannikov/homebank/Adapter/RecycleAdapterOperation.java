package ru.medyannikov.homebank.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.Model.Operation;
import ru.medyannikov.homebank.R;

/**
 * Created by Vladimir on 13.10.2015.
 */
public class RecycleAdapterOperation extends RecyclerView.Adapter<RecycleAdapterOperation.ViewHolder> {
    private List<Operation> operationList;

    public RecycleAdapterOperation(List<Operation> list) {
        operationList = list;

    }

    @Override
    public RecycleAdapterOperation.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_operation_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        vh.context = view.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterOperation.ViewHolder holder, int position) {
       holder.operationBillName.setText(operationList.get(position).getNameBill());
       holder.operationAbout.setText(operationList.get(position).getAbout());
       holder.operationValue.setText(operationList.get(position).getValue().toString());
        if(operationList.get(position).getType() == 1){
            holder.operationValue.setTextColor(holder.context.getResources().getColor(R.color.operationSub));
        } else{
            holder.operationValue.setTextColor(holder.context.getResources().getColor(R.color.black));
        }
        if (operationList.get(position).getSync() == 0){
            holder.operationSync.setTextColor(holder.context.getResources().getColor(R.color.color_no_sync));
        } else{
            holder.operationSync.setTextColor(holder.context.getResources().getColor(R.color.color_sync));
        }
        holder.operationAbout.setText(operationList.get(position).getDate());
       //holder.operationSync.setText(operationList.get(position).getSync().toString());
    }

    @Override
    public int getItemCount() {
        if (operationList != null){
            return operationList.size();
        }
        else return 0;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView operationBillName;
        private TextView operationAbout;
        private TextView operationValue;
        private TextView operationSync;

        public ViewHolder(View itemView) {
            super(itemView);
            operationBillName = (TextView) itemView.findViewById(R.id.operatBillName);
            operationAbout = (TextView) itemView.findViewById(R.id.operatAbout);
            operationValue = (TextView) itemView.findViewById(R.id.operatValue);
            operationSync = (TextView) itemView.findViewById(R.id.operatSync);
        }
    }
}
