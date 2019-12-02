package com.android.mltcode.watchlib.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;

import com.android.mltcode.watchlib.utils.SizeUtils;
import com.hoinnet.android.packageinstaller.R;

public class CircleProgressBar extends View {
    private static final String TAG = CircleProgressBar.class.getSimpleName();
    private final boolean ANTI_ALIAS = true;
    private final int DEFAULT_ANIM_TIME = 1000;
    private final int DEFAULT_ARC_WIDTH = 15;
    private final int DEFAULT_HINT_SIZE = 15;
    private final int DEFAULT_MAX_VALUE = 100;
    private final int DEFAULT_SIZE = 150;
    private final int DEFAULT_START_ANGLE = 270;
    private final int DEFAULT_SWEEP_ANGLE = 360;
    private final int DEFAULT_UNIT_SIZE = 30;
    private final int DEFAULT_VALUE = 0;
    private final int DEFAULT_VALUE_SIZE = 15;
    private final int DEFAULT_WAVE_HEIGHT = 40;
    private boolean antiAlias;
    private long mAnimTime;
    private ValueAnimator mAnimator;
    private Paint mArcPaint;
    private float mArcWidth;
    private int mBgArcColor;
    private Paint mBgArcPaint;
    private float mBgArcWidth;
    private Point mCenterPoint;
    private Context mContext;
    private int mDefaultSize;
    private int[] mGradientColors = {-16711936, InputDeviceCompat.SOURCE_ANY, SupportMenu.CATEGORY_MASK};
    private CharSequence mHint;
    private int mHintColor;
    private float mHintOffset;
    private TextPaint mHintPaint;
    private float mHintSize;
    /* access modifiers changed from: private */
    public float mMaxValue;
    /* access modifiers changed from: private */
    public float mPercent;
    private int mPrecision;
    private String mPrecisionFormat;
    private float mRadius;
    private RectF mRectF;
    private float mStartAngle;
    private float mSweepAngle;
    private SweepGradient mSweepGradient;
    private float mTextOffsetPercentInRadius;
    private CharSequence mUnit;
    private int mUnitColor;
    private float mUnitOffset;
    private TextPaint mUnitPaint;
    private float mUnitSize;
    /* access modifiers changed from: private */
    public float mValue;
    private int mValueColor;
    private float mValueOffset;
    private TextPaint mValuePaint;
    private float mValueSize;

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        this.mDefaultSize = SizeUtils.dp2px(this.mContext, 150.0f);
        this.mAnimator = new ValueAnimator();
        this.mRectF = new RectF();
        this.mCenterPoint = new Point();
        initAttrs(attrs);
        initPaint();
        setValue(this.mValue);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = this.mContext.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        this.antiAlias = typedArray.getBoolean(R.styleable.CircleProgressBar_antiAlias, true);
        this.mHint = typedArray.getString(R.styleable.CircleProgressBar_hint);
        this.mHintColor = typedArray.getColor(R.styleable.CircleProgressBar_hintColor, ViewCompat.MEASURED_STATE_MASK);
        this.mHintSize = typedArray.getDimension(R.styleable.CircleProgressBar_hintSize, 15.0f);
        this.mValue = typedArray.getFloat(R.styleable.CircleProgressBar_value, 0.0f);
        this.mMaxValue = typedArray.getFloat(R.styleable.CircleProgressBar_maxValue, 100.0f);
        this.mPrecision = typedArray.getInt(R.styleable.CircleProgressBar_precision, 0);
        this.mPrecisionFormat = SizeUtils.getPrecisionFormat(this.mPrecision);
        this.mValueColor = typedArray.getColor(R.styleable.CircleProgressBar_valueColor, ViewCompat.MEASURED_STATE_MASK);
        this.mValueSize = typedArray.getDimension(R.styleable.CircleProgressBar_valueSize, 15.0f);
        this.mUnit = typedArray.getString(R.styleable.CircleProgressBar_unit);
        this.mUnitColor = typedArray.getColor(R.styleable.CircleProgressBar_unitColor, ViewCompat.MEASURED_STATE_MASK);
        this.mUnitSize = typedArray.getDimension(R.styleable.CircleProgressBar_unitSize, 30.0f);
        this.mArcWidth = typedArray.getDimension(R.styleable.CircleProgressBar_arcWidth, 15.0f);
        this.mStartAngle = typedArray.getFloat(R.styleable.CircleProgressBar_startAngle, 270.0f);
        this.mSweepAngle = typedArray.getFloat(R.styleable.CircleProgressBar_sweepAngle, 360.0f);
        this.mBgArcColor = typedArray.getColor(R.styleable.CircleProgressBar_bgArcColor, -1);
        this.mBgArcWidth = typedArray.getDimension(R.styleable.CircleProgressBar_bgArcWidth, 15.0f);
        this.mTextOffsetPercentInRadius = typedArray.getFloat(R.styleable.CircleProgressBar_textOffsetPercentInRadius, 0.33f);
        this.mPercent = typedArray.getFloat(R.styleable.CircleProgressBar_percent, 0.0f);
        this.mAnimTime = (long) typedArray.getInt(R.styleable.CircleProgressBar_animTime, 1000);
        int gradientArcColors = typedArray.getResourceId(R.styleable.CircleProgressBar_arcColors, 0);
        if (gradientArcColors != 0) {
            try {
                int[] gradientColors = getResources().getIntArray(gradientArcColors);
                if (gradientColors.length == 0) {
                    int color = getResources().getColor(gradientArcColors);
                    this.mGradientColors = new int[2];
                    this.mGradientColors[0] = color;
                    this.mGradientColors[1] = color;
                } else if (gradientColors.length == 1) {
                    this.mGradientColors = new int[2];
                    this.mGradientColors[0] = gradientColors[0];
                    this.mGradientColors[1] = gradientColors[0];
                } else {
                    this.mGradientColors = gradientColors;
                }
            } catch (Resources.NotFoundException e) {
                throw new Resources.NotFoundException("the give resource not found.");
            }
        }
        typedArray.recycle();
    }

    private void initPaint() {
        this.mHintPaint = new TextPaint();
        this.mHintPaint.setAntiAlias(this.antiAlias);
        this.mHintPaint.setTextSize(this.mHintSize);
        this.mHintPaint.setColor(this.mHintColor);
        this.mHintPaint.setTextAlign(Paint.Align.CENTER);
        this.mValuePaint = new TextPaint();
        this.mValuePaint.setAntiAlias(this.antiAlias);
        this.mValuePaint.setTextSize(this.mValueSize);
        this.mValuePaint.setColor(this.mValueColor);
        this.mValuePaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.mValuePaint.setTextAlign(Paint.Align.CENTER);
        this.mUnitPaint = new TextPaint();
        this.mUnitPaint.setAntiAlias(this.antiAlias);
        this.mUnitPaint.setTextSize(this.mUnitSize);
        this.mUnitPaint.setColor(this.mUnitColor);
        this.mUnitPaint.setTextAlign(Paint.Align.CENTER);
        this.mArcPaint = new Paint();
        this.mArcPaint.setAntiAlias(this.antiAlias);
        this.mArcPaint.setStyle(Paint.Style.STROKE);
        this.mArcPaint.setStrokeWidth(this.mArcWidth);
        this.mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mBgArcPaint = new Paint();
        this.mBgArcPaint.setAntiAlias(this.antiAlias);
        this.mBgArcPaint.setColor(this.mBgArcColor);
        this.mBgArcPaint.setStyle(Paint.Style.STROKE);
        this.mBgArcPaint.setStrokeWidth(this.mBgArcWidth);
        this.mBgArcPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(SizeUtils.measure(widthMeasureSpec, this.mDefaultSize), SizeUtils.measure(heightMeasureSpec, this.mDefaultSize));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float maxArcWidth = Math.max(this.mArcWidth, this.mBgArcWidth);
        this.mRadius = (float) (Math.min(((w - getPaddingLeft()) - getPaddingRight()) - (((int) maxArcWidth) * 2), ((h - getPaddingTop()) - getPaddingBottom()) - (((int) maxArcWidth) * 2)) / 2);
        Point point = this.mCenterPoint;
        point.x = w / 2;
        point.y = h / 2;
        this.mRectF.left = (((float) point.x) - this.mRadius) - (maxArcWidth / 2.0f);
        this.mRectF.top = (((float) this.mCenterPoint.y) - this.mRadius) - (maxArcWidth / 2.0f);
        this.mRectF.right = ((float) this.mCenterPoint.x) + this.mRadius + (maxArcWidth / 2.0f);
        this.mRectF.bottom = ((float) this.mCenterPoint.y) + this.mRadius + (maxArcWidth / 2.0f);
        this.mValueOffset = ((float) this.mCenterPoint.y) + getBaselineOffsetFromY(this.mValuePaint);
        this.mHintOffset = (((float) this.mCenterPoint.y) - (this.mRadius * this.mTextOffsetPercentInRadius)) + getBaselineOffsetFromY(this.mHintPaint);
        this.mUnitOffset = ((float) this.mCenterPoint.y) + (this.mRadius * this.mTextOffsetPercentInRadius) + getBaselineOffsetFromY(this.mUnitPaint);
        updateArcPaint();
    }

    private float getBaselineOffsetFromY(Paint paint) {
        return SizeUtils.measureTextHeight(paint) / 2.0f;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
        drawArc(canvas);
    }

    private void drawText(Canvas canvas) {
        CharSequence charSequence = this.mHint;
        if (charSequence != null) {
            canvas.drawText(charSequence.toString(), (float) this.mCenterPoint.x, this.mHintOffset, this.mHintPaint);
        }
        CharSequence charSequence2 = this.mUnit;
        if (charSequence2 != null) {
            canvas.drawText(charSequence2.toString(), (float) this.mCenterPoint.x, this.mUnitOffset, this.mUnitPaint);
            canvas.drawText(String.format(this.mPrecisionFormat, new Object[]{Float.valueOf(this.mValue)}), (float) this.mCenterPoint.x, this.mValueOffset, this.mValuePaint);
        }
    }

    private void drawArc(Canvas canvas) {
        canvas.save();
        float currentAngle = this.mSweepAngle * this.mPercent;
        canvas.rotate(this.mStartAngle, (float) this.mCenterPoint.x, (float) this.mCenterPoint.y);
        Canvas canvas2 = canvas;
        canvas2.drawArc(this.mRectF, currentAngle, (this.mSweepAngle - currentAngle) + 2.0f, false, this.mBgArcPaint);
        canvas2.drawArc(this.mRectF, 2.0f, currentAngle, false, this.mArcPaint);
        canvas.restore();
    }

    private void updateArcPaint() {
        this.mSweepGradient = new SweepGradient((float) this.mCenterPoint.x, (float) this.mCenterPoint.y, this.mGradientColors, (float[]) null);
        this.mArcPaint.setShader(this.mSweepGradient);
    }

    public boolean isAntiAlias() {
        return this.antiAlias;
    }

    public CharSequence getHint() {
        return this.mHint;
    }

    public void setHint(CharSequence hint) {
        this.mHint = hint;
    }

    public CharSequence getUnit() {
        return this.mUnit;
    }

    public void setUnit(CharSequence unit) {
        this.mUnit = unit;
    }

    public float getValue() {
        return this.mValue;
    }

    public void setValue(float value) {
        if (value > this.mMaxValue) {
            value = this.mMaxValue;
        } else if (value < 0.0f) {
            value = 0.0f;
        }
        startAnimator(this.mPercent, value / this.mMaxValue, this.mAnimTime);
    }

    private void startAnimator(float start, float end, long animTime) {
        this.mAnimator = ValueAnimator.ofFloat(new float[]{start, end});
        this.mAnimator.setDuration(animTime);
        this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float unused = CircleProgressBar.this.mPercent = ((Float) animation.getAnimatedValue()).floatValue();
                CircleProgressBar circleProgressBar = CircleProgressBar.this;
                float unused2 = circleProgressBar.mValue = circleProgressBar.mPercent * CircleProgressBar.this.mMaxValue;
                CircleProgressBar.this.invalidate();
            }
        });
        this.mAnimator.start();
    }

    public float getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(float maxValue) {
        this.mMaxValue = maxValue;
    }

    public int getPrecision() {
        return this.mPrecision;
    }

    public void setPrecision(int precision) {
        this.mPrecision = precision;
        this.mPrecisionFormat = SizeUtils.getPrecisionFormat(precision);
    }

    public int[] getGradientColors() {
        return this.mGradientColors;
    }

    public void setGradientColors(int[] gradientColors) {
        this.mGradientColors = gradientColors;
        updateArcPaint();
    }

    public long getAnimTime() {
        return this.mAnimTime;
    }

    public void setAnimTime(long animTime) {
        this.mAnimTime = animTime;
    }

    public void reset() {
        startAnimator(this.mPercent, 0.0f, 1000);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
