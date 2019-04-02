/*
 * Copyright 2015 Eric Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package binding.com.demo.ui.drag;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

import binding.com.demo.R;
import timber.log.Timber;

import static binding.com.demo.BuildConfig.DEBUG;

public class SwipeViewLayout extends FrameLayout {
//    private ViewDragHelper mViewDragHelper;
    private int mCurrentX;
    private int mCurEdgeFlag = ViewDragHelper.EDGE_LEFT;
    private int mActivePointerId = INVALID_POINTER;
    private static final int INVALID_POINTER = -1;
    private boolean mIsBeingDragged;


    public SwipeViewLayout(Context context) {
        super(context);
        init();
    }

    public SwipeViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public SwipeViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeViewLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

    }
//        float mLastMotionX;
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        final int action = ev.getAction() & MotionEvent.ACTION_MASK;
//        if (!mIsBeingDragged) {
//            final int pointerIndex = ev.findPointerIndex(mActivePointerId);
//            if (pointerIndex == -1) {
//                // A child has consumed some touch events and put us into an inconsistent
//                // state.
//                needsInvalidate = resetTouch();
//                break;
//            }
//            final float x = ev.getX(pointerIndex);
//            final float xDiff = Math.abs(x - mLastMotionX);
//            final float y = ev.getY(pointerIndex);
//            final float yDiff = Math.abs(y - mLastMotionY);
//
//            if (xDiff > mTouchSlop && xDiff > yDiff) {
//                if (DEBUG) Log.v(TAG, "Starting drag!");
//                mIsBeingDragged = true;
//                requestParentDisallowInterceptTouchEvent(true);
//                mLastMotionX = x - mInitialMotionX > 0 ? mInitialMotionX + mTouchSlop :
//                        mInitialMotionX - mTouchSlop;
//                mLastMotionY = y;
//
//                setScrollingCacheEnabled(true);
//
//                // Disallow Parent Intercept, just in case
//                ViewParent parent = getParent();
//                if (parent != null) {
//                    parent.requestDisallowInterceptTouchEvent(true);
//                }
//            }
//        }
//        return mIsBeingDragged;
//    }
//
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//    }
//
//
//    private boolean resetTouch() {
//
//    }

}
