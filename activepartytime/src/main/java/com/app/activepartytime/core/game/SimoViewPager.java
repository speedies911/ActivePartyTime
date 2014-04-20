package com.app.activepartytime.core.game;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Simo on 18.4.14.
 */
public class SimoViewPager extends ViewPager{



        public SimoViewPager(Context context){
            super(context);
        }

        public SimoViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev){
            return false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev){
            return false;
        }

}
