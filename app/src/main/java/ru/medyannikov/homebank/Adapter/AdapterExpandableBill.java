package ru.medyannikov.homebank.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.medyannikov.homebank.R;

/**
 * Created by Vladimir on 21.12.2015.
 */
public class AdapterExpandableBill extends RecycleAdapterExpandableBill<AdapterExpandableBill.ParentViewHolder, AdapterExpandableBill.ChildViewHolder> {




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public ParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View view = LayoutInflater.from(parentViewGroup.getContext()).inflate(R.layout.recycle_bill_item, parentViewGroup, false);
        return new ParentViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View view = LayoutInflater.from(childViewGroup.getContext()).inflate(R.layout.recycle_operation_item,childViewGroup,false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(ParentViewHolder parentViewHolder, int position, Object parentListItem) {

    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int position, Object childListItem) {

    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView operationBillName;
        private TextView operationAbout;
        private TextView operationValue;
        private TextView operationSync;

        public ChildViewHolder(View itemView) {
            super(itemView);
            operationBillName = (TextView) itemView.findViewById(R.id.operatBillName);
            operationAbout = (TextView) itemView.findViewById(R.id.operatAbout);
            operationValue = (TextView) itemView.findViewById(R.id.operatValue);
            operationSync = (TextView) itemView.findViewById(R.id.operatSync);
        }
    }

    public class ParentViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView value;
        private TextView about;
        private Context context;
        public ParentViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.billName);
            value = (TextView) itemView.findViewById(R.id.billValue);
            about = (TextView) itemView.findViewById(R.id.billAbout);
            /*itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);*/
        }
    }
}
