package com.vv.life.compose.example;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

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
public class TextTouchView extends androidx.appcompat.widget.AppCompatButton {
    public TextTouchView(Context context) {
        super(context);
    }

    public TextTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("linjiaqiang", "TextTouchView dispatchTouchEvent -> " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = super.onTouchEvent(event);
        Log.e("linjiaqiang", "TextTouchView event -> " + handled + " " + event.getAction());
        return handled;
    }
}
