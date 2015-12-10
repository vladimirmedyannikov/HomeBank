package ru.medyannikov.homebank.DecorationRecycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.medyannikov.homebank.R;

/**
 * Created by Vladimir on 08.12.2015.
 */
public class LineDivinerRecycler extends RecyclerView.ItemDecoration {
    private Drawable draw;
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + draw.getIntrinsicHeight();

            draw.setBounds(left, top, right, bottom);
            draw.draw(c);
        }
    }

    public LineDivinerRecycler(Drawable draw){
        this.draw = draw;
    }
}
