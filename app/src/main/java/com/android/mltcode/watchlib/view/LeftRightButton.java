package com.android.mltcode.watchlib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hoinnet.android.packageinstaller.R;

public class LeftRightButton extends LinearLayout implements View.OnClickListener {
    private final int BUTTON_TYPE_IMG;
    private Context mContext;
    private LinearLayout mLayoutLeft;
    private LinearLayout mLayoutRight;
    private Drawable mLeftDrawableBg;
    private Drawable mLeftDrawableImg;
    private ImageView mLeftImg;
    private LeftRightListener mLeftRightListener;
    private String mLeftText;
    private TextView mLeftTv;
    private Resources mRes;
    private Drawable mRightDrawableBg;
    private Drawable mRightDrawableImg;
    private ImageView mRightImg;
    private String mRightText;
    private TextView mRightTv;
    private int mType;

    public interface LeftRightListener {
        void onLeftClick(View view);

        void onRightClick(View view);
    }

    public LeftRightButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public LeftRightButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("WrongConstant")
    public LeftRightButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.BUTTON_TYPE_IMG = 1;
        this.mType = 1;
        this.mContext = context;
        this.mRes = getResources();
        LayoutInflater.from(context).inflate(R.layout.left_right_layout, this, true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LeftRightView);
        this.mLeftDrawableBg = typedArray.getDrawable(R.styleable.LeftRightView_leftBackground);
        this.mRightDrawableBg = typedArray.getDrawable(R.styleable.LeftRightView_rightBackground);
        this.mType = typedArray.getInt(R.styleable.LeftRightView_leftRightType, 1);
        this.mLeftDrawableImg = typedArray.getDrawable(R.styleable.LeftRightView_leftImgSrc);
        this.mRightDrawableImg = typedArray.getDrawable(R.styleable.LeftRightView_rightImgSrc);
        this.mLeftText = typedArray.getString(R.styleable.LeftRightView_leftText);
        this.mRightText = typedArray.getString(R.styleable.LeftRightView_rightText);
        typedArray.recycle();
        this.mLayoutLeft = (LinearLayout) findViewById(R.id.lr_cancel);
        this.mLayoutRight = (LinearLayout) findViewById(R.id.lr_confrim);
        this.mLayoutLeft.setBackground(this.mLeftDrawableBg);
        this.mLayoutRight.setBackground(this.mRightDrawableBg);
        this.mLeftImg = (ImageView) findViewById(R.id.lr_left_img);
        this.mRightImg = (ImageView) findViewById(R.id.lr_right_img);
        this.mLeftTv = (TextView) findViewById(R.id.lr_left_tv);
        this.mRightTv = (TextView) findViewById(R.id.lr_right_tv);
        if (this.mType == 1) {
            this.mLeftTv.setVisibility(8);
            this.mRightTv.setVisibility(8);
            this.mLeftImg.setVisibility(0);
            this.mRightImg.setVisibility(0);
            this.mLeftImg.setImageDrawable(this.mLeftDrawableImg);
            this.mRightImg.setImageDrawable(this.mRightDrawableImg);
        } else {
            this.mLeftTv.setVisibility(0);
            this.mRightTv.setVisibility(0);
            this.mLeftImg.setVisibility(8);
            this.mRightImg.setVisibility(8);
            this.mLeftTv.setText(this.mLeftText);
            this.mRightTv.setText(this.mRightText);
        }
        this.mLayoutLeft.setOnClickListener(this);
        this.mLayoutRight.setOnClickListener(this);
    }

    public void setLeftRightListener(LeftRightListener listener) {
        if (listener != null) {
            this.mLeftRightListener = listener;
        }
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.lr_cancel) {
            LeftRightListener leftRightListener = this.mLeftRightListener;
            if (leftRightListener != null) {
                leftRightListener.onLeftClick(v);
            }
        } else if (i == R.id.lr_confrim) {
            LeftRightListener leftRightListener2 = this.mLeftRightListener;
            if (leftRightListener2 != null) {
                leftRightListener2.onRightClick(v);
            }
        }
    }
}
