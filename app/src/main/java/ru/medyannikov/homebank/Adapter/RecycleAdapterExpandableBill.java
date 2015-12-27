package ru.medyannikov.homebank.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.Model.Operation;

/**
 * Created by Vladimir on 21.12.2015.
 */
public abstract class RecycleAdapterExpandableBill<PVH extends AdapterExpandableBill.ParentViewHolder, CVH extends AdapterExpandableBill.ChildViewHolder > extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> objectList;
    private static final int HEADER = 0;
    private static final int CHILD = 1;

    public abstract PVH onCreateParentViewHolder(ViewGroup parentViewGroup);

    public abstract CVH onCreateChildViewHolder(ViewGroup childViewGroup);

    public RecycleAdapterExpandableBill(List<Object> objectList) {
        this.objectList = objectList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == HEADER) {
            PVH pvh = onCreateParentViewHolder(viewGroup);
            return pvh;
        } else if (viewType == CHILD) {
            return onCreateChildViewHolder(viewGroup);
        } else {
            throw new IllegalStateException("Incorrect ViewType found");
        }
    }

    public abstract void onBindParentViewHolder(PVH parentViewHolder, int position, Object parentListItem);

    public abstract void onBindChildViewHolder(CVH childViewHolder, int position, Object childListItem);

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof Bill){
            return HEADER;
        }
        else
            return CHILD;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object listItem = objectList.get(position);
        if (listItem instanceof Bill) {
            PVH parentViewHolder = (PVH) holder;
            onBindParentViewHolder(parentViewHolder, position, listItem);
        } else if (listItem == null) {
            throw new IllegalStateException("Incorrect ViewHolder found");
        } else {
            onBindChildViewHolder((CVH) holder, position, listItem);
        }
    }

    public void collapseViews(int parentPosition){
        Object listItem = objectList.get(parentPosition);
        if (listItem instanceof Bill)
        {
            List<?> collapseList = ((Bill) listItem).getListOperation();
            for (Object operation:collapseList){
                objectList.remove(operation);
                notifyDataSetChanged();
            }
            ((Bill) listItem).setExplanded(false);
        }
    }

    public void explandedViews(int parentPosition){
        Object listItem = objectList.get(parentPosition);
        if (listItem instanceof Bill)
        {

            List<?> collapseList = ((Bill) listItem).getListOperation();
            for (Object operation:collapseList){
                objectList.add(parentPosition + 1, operation);
                notifyDataSetChanged();
            }
            ((Bill) listItem).setExplanded(true);
        }
    }
}
