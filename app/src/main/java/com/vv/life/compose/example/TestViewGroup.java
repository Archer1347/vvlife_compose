package com.vv.life.compose.example;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Desc:
 * <p>
 * Date: 2021/8/16
 * Copyright: Copyright (c) 2010-2020
 * Company: @微微科技有限公司
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @author: linjiaqiang
 */
public class TestViewGroup extends ScrollView {
    public TestViewGroup(@NonNull Context context) {
        super(context);
    }

    public TestViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("linjiaqiang", "TestViewGroup dispatchTouchEvent -> " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(ev);
        Log.e("linjiaqiang", "TestViewGroup onInterceptTouchEvent -> " + intercept + " " + ev.getAction());
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("linjiaqiang", "TestViewGroup event -> " + event.getAction());
        return super.onTouchEvent(event);
    }
}
