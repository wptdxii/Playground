package com.wptdxii.framekit.component.recyclerview.listener;

import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class OnItemClickListener implements RecyclerView.OnItemTouchListener {

    private final GestureDetectorCompat mDetectorCompat;

    public OnItemClickListener(@NonNull RecyclerView recyclerView) {
        mDetectorCompat = new GestureDetectorCompat(recyclerView.getContext(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null) {
                            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(childView);
                            onItemClick(viewHolder);
                        }
                        return true;
                    }
                });
    }

    protected abstract void onItemClick(ViewHolder viewHolder);

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
}
