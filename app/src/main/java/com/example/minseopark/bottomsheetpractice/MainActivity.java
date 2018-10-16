package com.example.minseopark.bottomsheetpractice;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout llBottomSheet;
    private ValueAnimator angulatingAnimator;
    private ValueAnimator roundingAnimator;
    private CoordinatorLayout.LayoutParams layoutParams;
    private boolean isExpended;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llBottomSheet = findViewById(R.id.bottom_sheet);

        layoutParams = (CoordinatorLayout.LayoutParams) llBottomSheet.getLayoutParams();

        // init the bottom sheet behavior
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 80.f, getResources().getDisplayMetrics()));
        bottomSheetBehavior.setHideable(false);

        // animation settings
        angulatingAnimator = ValueAnimator.ofInt((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, getResources().getDisplayMetrics()));
        angulatingAnimator.setDuration(150);
        roundingAnimator = ValueAnimator.ofInt((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16.0f, getResources().getDisplayMetrics()));
        roundingAnimator.setDuration(150);

        // set callback for changes
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int bottomSheetState) {
                if (bottomSheetState == BottomSheetBehavior.STATE_EXPANDED) {
                    setAngulatingAnimator();
                    isExpended = true;
                } else if (bottomSheetState == BottomSheetBehavior.STATE_DRAGGING && isExpended) {
                    setRoundingAnimator();
                } else if (bottomSheetState == BottomSheetBehavior.STATE_COLLAPSED) {
                    isExpended = false;
                }
            }
            @Override
            public void onSlide(@NonNull View view, float offset) {}
        });
    }

    // set rounded corner method
    public void setRoundingAnimator() {
        roundingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                layoutParams.setMarginStart(value);
                layoutParams.setMarginEnd(value);
                llBottomSheet.setLayoutParams(layoutParams);
                llBottomSheet.setBackground(getDrawable(R.drawable.rounded_corner));
            }
        });
        roundingAnimator.start();
    }

    // set angulated corner method
    public void setAngulatingAnimator() {
        angulatingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                layoutParams.setMarginStart(value);
                layoutParams.setMarginEnd(value);
                llBottomSheet.setLayoutParams(layoutParams);
                llBottomSheet.setBackground(getDrawable(R.drawable.unrouned_corner));
            }
        });
        angulatingAnimator.start();
    }
}
