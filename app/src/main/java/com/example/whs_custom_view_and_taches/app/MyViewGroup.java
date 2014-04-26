package com.example.whs_custom_view_and_taches.app;

import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by vanik on 26.04.14.
 */
class MyViewGroup extends ViewGroup {
    private static String MY_LOGGER = "myLogger";
    private View view1;
    private View view2;
    private int width;
    private int height;
    private double oldX;
    private double oldY;

    MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        view1 = new View(context);
        view1.setId(0);
        view1.setBackgroundColor(Color.BLUE);
        view1.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MY_LOGGER, "on touch listener in view 1");
                Log.d(MY_LOGGER, "event parent id = " + v.getId());
                double x = event.getX();;
                double y = event.getY();;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(MY_LOGGER, "action down in view1");
                        oldX = x;
                        oldY = y;
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(MY_LOGGER, "action move in view1");
                        double dx = oldX - x;
                        double dy = oldY - y;
                        if(dx < 0 && ViewConfiguration.getTouchSlop() < Math.abs(dx) && Math.abs(dx) > Math.abs(dy)) {

                        }
                        return true;
                }
                return false;
            }
        });

        view2 = new View(context);
        view2.setId(1);
        view2.setBackgroundColor(Color.YELLOW);
        view2.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MY_LOGGER, "on touch listener in view 2");
                return false;
            }
        });
        addView(view1);
        addView(view2);
        Log.d(MY_LOGGER, "view1 has id = " + view1.getId());
        Log.d(MY_LOGGER, "view2 has id = " + view2.getId());


    }

    MyViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(MY_LOGGER, "action down");
                return false;
            case MotionEvent.ACTION_MOVE:
                Log.d(MY_LOGGER, "action move");
                double dx = oldX - x;
                double dy = oldY - y;
            if(dx < 0 && ViewConfiguration.getTouchSlop() < Math.abs(dx) && Math.abs(dx) > Math.abs(dy)) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(1).measure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height =     MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        getChildAt(0).layout(0, 0, width, height );
        getChildAt(1).layout(-width, 0, 0, height);

    }
}
