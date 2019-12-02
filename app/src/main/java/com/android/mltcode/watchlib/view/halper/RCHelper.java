package com.android.mltcode.watchlib.view.halper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import com.hoinnet.android.packageinstaller.R;

import java.util.ArrayList;

public class RCHelper {
    public Region mAreaRegion;
    public boolean mClipBackground;
    public Path mClipPath;
    public int mDefaultStrokeColor;
    public RectF mLayer;
    public Paint mPaint;
    public boolean mRoundAsCircle = false;
    public int mStrokeColor;
    public ColorStateList mStrokeColorStateList;
    public int mStrokeWidth;
    public float[] radii = new float[8];

    public void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RCAttrs);
        this.mRoundAsCircle = ta.getBoolean(R.styleable.RCAttrs_round_as_circle, false);
        this.mStrokeColorStateList = ta.getColorStateList(R.styleable.RCAttrs_stroke_color);
        ColorStateList colorStateList = this.mStrokeColorStateList;
        if (colorStateList != null) {
            this.mStrokeColor = colorStateList.getDefaultColor();
            this.mDefaultStrokeColor = this.mStrokeColorStateList.getDefaultColor();
        } else {
            this.mStrokeColor = -1;
            this.mDefaultStrokeColor = -1;
        }
        this.mStrokeWidth = ta.getDimensionPixelSize(R.styleable.RCAttrs_stroke_width, 0);
        this.mClipBackground = ta.getBoolean(R.styleable.RCAttrs_clip_background, false);
        int roundCorner = ta.getDimensionPixelSize(R.styleable.RCAttrs_round_corner, 0);
        int roundCornerTopLeft = ta.getDimensionPixelSize(R.styleable.RCAttrs_round_corner_top_left, roundCorner);
        int roundCornerTopRight = ta.getDimensionPixelSize(R.styleable.RCAttrs_round_corner_top_right, roundCorner);
        int roundCornerBottomLeft = ta.getDimensionPixelSize(R.styleable.RCAttrs_round_corner_bottom_left, roundCorner);
        int roundCornerBottomRight = ta.getDimensionPixelSize(R.styleable.RCAttrs_round_corner_bottom_right, roundCorner);
        ta.recycle();
        float[] fArr = this.radii;
        fArr[0] = (float) roundCornerTopLeft;
        fArr[1] = (float) roundCornerTopLeft;
        fArr[2] = (float) roundCornerTopRight;
        fArr[3] = (float) roundCornerTopRight;
        fArr[4] = (float) roundCornerBottomRight;
        fArr[5] = (float) roundCornerBottomRight;
        fArr[6] = (float) roundCornerBottomLeft;
        fArr[7] = (float) roundCornerBottomLeft;
        this.mLayer = new RectF();
        this.mClipPath = new Path();
        this.mAreaRegion = new Region();
        this.mPaint = new Paint();
        this.mPaint.setColor(-1);
        this.mPaint.setAntiAlias(true);
    }

    public void onSizeChanged(View view, int w, int h) {
        this.mLayer.set(0.0f, 0.0f, (float) w, (float) h);
        refreshRegion(view);
    }

    public void refreshRegion(View view) {
        int w = (int) this.mLayer.width();
        int h = (int) this.mLayer.height();
        RectF areas = new RectF();
        areas.left = (float) view.getPaddingLeft();
        areas.top = (float) view.getPaddingTop();
        areas.right = (float) (w - view.getPaddingRight());
        areas.bottom = (float) (h - view.getPaddingBottom());
        this.mClipPath.reset();
        if (this.mRoundAsCircle) {
            float r = (areas.width() >= areas.height() ? areas.height() : areas.width()) / 2.0f;
            PointF center = new PointF((float) (w / 2), (float) (h / 2));
            float y = ((float) (h / 2)) - r;
            this.mClipPath.moveTo(areas.left, y);
            this.mClipPath.addCircle(center.x, y + r, r, Path.Direction.CW);
        } else {
            this.mClipPath.addRoundRect(areas, this.radii, Path.Direction.CW);
        }
        this.mAreaRegion.setPath(this.mClipPath, new Region((int) areas.left, (int) areas.top, (int) areas.right, (int) areas.bottom));
    }

    public void onClipDraw(Canvas canvas) {
        if (this.mStrokeWidth > 0) {
            this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            this.mPaint.setColor(-1);
            this.mPaint.setStrokeWidth((float) (this.mStrokeWidth * 2));
            this.mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(this.mClipPath, this.mPaint);
            this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            this.mPaint.setColor(this.mStrokeColor);
            this.mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(this.mClipPath, this.mPaint);
        }
        this.mPaint.setColor(-1);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        Path path = new Path();
        path.addRect(0.0f, 0.0f, (float) ((int) this.mLayer.width()), (float) ((int) this.mLayer.height()), Path.Direction.CW);
        path.op(this.mClipPath, Path.Op.DIFFERENCE);
        canvas.drawPath(path, this.mPaint);
    }

    public void drawableStateChanged(View view) {
        if (view instanceof RCAttrs) {
            ArrayList<Integer> stateListArray = new ArrayList<>();
            if (view instanceof Checkable) {
                stateListArray.add(16842911);
                if (((Checkable) view).isChecked()) {
                    stateListArray.add(16842912);
                }
            }
            if (view.isEnabled()) {
                stateListArray.add(16842910);
            }
            if (view.isFocused()) {
                stateListArray.add(16842908);
            }
            if (view.isPressed()) {
                stateListArray.add(16842919);
            }
            if (view.isHovered()) {
                stateListArray.add(16843623);
            }
            if (view.isSelected()) {
                stateListArray.add(16842913);
            }
            if (view.isActivated()) {
                stateListArray.add(16843518);
            }
            if (view.hasWindowFocus()) {
                stateListArray.add(16842909);
            }
            ColorStateList colorStateList = this.mStrokeColorStateList;
            if (colorStateList != null && colorStateList.isStateful()) {
                int[] stateList = new int[stateListArray.size()];
                for (int i = 0; i < stateListArray.size(); i++) {
                    stateList[i] = stateListArray.get(i).intValue();
                }
                ((RCAttrs) view).setStrokeColor(this.mStrokeColorStateList.getColorForState(stateList, this.mDefaultStrokeColor));
            }
        }
    }
}
