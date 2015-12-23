package ru.medyannikov.homebank.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.medyannikov.homebank.Activity.ActivityBillInfo;
import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.Model.Operation;
import ru.medyannikov.homebank.R;
import ru.medyannikov.homebank.Utils.ClassUtils;

/**
 * Created by Vladimir on 21.12.2015.
 */
public class AdapterExpandableBill extends RecycleAdapterExpandableBill<AdapterExpandableBill.ParentViewHolder, AdapterExpandableBill.ChildViewHolder> {


    public AdapterExpandableBill(List<Object> objectList) {
        super(objectList);
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
        parentViewHolder.name.setText(((Bill) parentListItem).getName().toString());
        parentViewHolder.value.setText(((Bill) parentListItem).getValue().toString());
        parentViewHolder.about.setText(((Bill) parentListItem).getAbout().toString());
        parentViewHolder.itemView.setId(position);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int position, Object childListItem) {
        childViewHolder.operationBillName.setText(((Operation) childListItem).getAbout());
        childViewHolder.operationValue.setText(((Operation) childListItem).getValue().toString());
        childViewHolder.operationAbout.setText(((Operation) childListItem).getDate());
        childViewHolder.operationSync.setText(((Operation) childListItem).getSync().toString());
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

    public class ParentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {
        private TextView name;
        private TextView value;
        private TextView about;
        private Context context;
        public ParentViewHolder(View itemView) {
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
}
