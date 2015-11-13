package ru.medyannikov.homebank.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.medyannikov.homebank.Activity.ActivityBillInfo;
import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.R;
import ru.medyannikov.homebank.Utils.ClassUtils;

/**
 * Created by Vladimir on 26.09.2015.
 */
public class RecycleAdapterBill extends RecyclerView.Adapter<RecycleAdapterBill.ViewHolder> {
    private List<Bill> billList;

    @Override
    public RecycleAdapterBill.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_bill_item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        vh.context = view.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterBill.ViewHolder holder, int position) {
        holder.name.setText(billList.get(position).getName().toString());
        holder.value.setText(billList.get(position).getValue().toString());
        holder.about.setText(billList.get(position).getAbout().toString());
        holder.itemView.setId(position);
    }

    @Override
    public int getItemCount() {
        if (billList != null){
        return billList.size();
        }else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {
        private TextView name;
        private TextView value;
        private TextView about;
        private Context context;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.billName);
            value = (TextView) itemView.findViewById(R.id.billValue);
            about = (TextView) itemView.findViewById(R.id.billAbout);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(v.getContext(), name.getText(), Toast.LENGTH_SHORT).show();
            /*Snackbar.make(v,name.getText(),Snackbar.LENGTH_SHORT)
                    .setAction("Wow", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();*/
            Intent intent = new Intent(v.getContext(), ActivityBillInfo.class);
            intent.putExtra(ClassUtils.INTENT_BILL_INFO,v.getId());
            v.getContext().startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            v.showContextMenu();
            return true;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Context menu");
            menu.add(v.getId(), 1, ContextMenu.NONE, R.string.billInfo);
            menu.add(v.getId(), 2, ContextMenu.NONE, R.string.setOperation);
            menu.add(v.getId(), 3, ContextMenu.NONE, R.string.billdelete);
        }
    }

    public RecycleAdapterBill(List<Bill> billList) {
        this.billList = billList;
    }
}
