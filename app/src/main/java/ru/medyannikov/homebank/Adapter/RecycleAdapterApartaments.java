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

import ru.medyannikov.homebank.Activity.ActivityApartamentInfo;
import ru.medyannikov.homebank.Model.Apartament;
import ru.medyannikov.homebank.R;
import ru.medyannikov.homebank.Utils.ClassUtils;

/**
 * Created by Vladimir on 15.11.2015.
 */
public class RecycleAdapterApartaments extends RecyclerView.Adapter<RecycleAdapterApartaments.ViewHolder> {
    private List<Apartament> apartamentsList;

    public RecycleAdapterApartaments(List<Apartament> apartaments){
        apartamentsList = apartaments;
    }

    @Override
    public RecycleAdapterApartaments.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_apartament_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        vh.context = view.getContext();
        return vh;

    }

    @Override
    public void onBindViewHolder(RecycleAdapterApartaments.ViewHolder holder, int position) {
        holder.apartamentName.setText(apartamentsList.get(position).getName().toString());
        holder.apartamentAbout.setText(apartamentsList.get(position).getAbout().toString());
        holder.apartamentValue.setText(apartamentsList.get(position).getValue().toString());
        holder.itemView.setId(position);
    }

    @Override
    public int getItemCount() {
        return apartamentsList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {
        private TextView apartamentName;
        private TextView apartamentAbout;
        private TextView apartamentValue;
        private TextView apartamentSync;

        private Context context;
        public ViewHolder(View itemView) {
            super(itemView);
            apartamentName = (TextView) itemView.findViewById(R.id.apartamentName);
            apartamentAbout = (TextView) itemView.findViewById(R.id.apartamentAbout);
            apartamentValue = (TextView) itemView.findViewById(R.id.apartamentValue);
            apartamentSync = (TextView) itemView.findViewById(R.id.apartamentSync);
            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            v.showContextMenu();
            return true;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Context menu");
            menu.add(v.getId(), 1, ContextMenu.NONE, R.string.menuApartInfo);
            menu.add(v.getId(), 2, ContextMenu.NONE, R.string.menuApartBill);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ActivityApartamentInfo.class);
            intent.putExtra(ClassUtils.INTENT_APARTAMENT_INFO, v.getId());
            v.getContext().startActivity(intent);
        }
    }



}
