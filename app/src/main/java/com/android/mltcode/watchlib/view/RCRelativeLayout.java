package com.android.mltcode.watchlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.android.mltcode.watchlib.view.halper.RCAttrs;
import com.android.mltcode.watchlib.view.halper.RCHelper;

public class RCRelativeLayout extends RelativeLayout implements RCAttrs {
    RCHelper mRCHelper;

    public RCRelativeLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public RCRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RCRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mRCHelper = new RCHelper();
        this.mRCHelper.initAttrs(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mRCHelper.onSizeChanged(this, w, h);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(this.mRCHelper.mLayer, (Paint) null, 31);
        super.dispatchDraw(canvas);
        this.mRCHelper.onClipDraw(canvas);
        canvas.restore();
    }

    public void draw(Canvas canvas) {
        if (this.mRCHelper.mClipBackground) {
            canvas.save();
            canvas.clipPath(this.mRCHelper.mClipPath);
            super.draw(canvas);
            canvas.restore();
            return;
        }
        super.draw(canvas);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == 0 && !this.mRCHelper.mAreaRegion.contains((int) ev.getX(), (int) ev.getY())) {
            return false;
        }
        if (action == 0 || action == 1) {
            refreshDrawableState();
        } else if (action == 3) {
            setPressed(false);
            refreshDrawableState();
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setClipBackground(boolean clipBackground) {
        this.mRCHelper.mClipBackground = clipBackground;
        invalidate();
    }

    public void setRoundAsCircle(boolean roundAsCircle) {
        this.mRCHelper.mRoundAsCircle = roundAsCircle;
        invalidate();
    }

    public void setRadius(int radius) {
        for (int i = 0; i < this.mRCHelper.radii.length; i++) {
            this.mRCHelper.radii[i] = (float) radius;
        }
        invalidate();
    }

    public void setTopLeftRadius(int topLeftRadius) {
        this.mRCHelper.radii[0] = (float) topLeftRadius;
        this.mRCHelper.radii[1] = (float) topLeftRadius;
        invalidate();
    }

    public void setTopRightRadius(int topRightRadius) {
        this.mRCHelper.radii[2] = (float) topRightRadius;
        this.mRCHelper.radii[3] = (float) topRightRadius;
        invalidate();
    }

    public void setBottomLeftRadius(int bottomLeftRadius) {
        this.mRCHelper.radii[6] = (float) bottomLeftRadius;
        this.mRCHelper.radii[7] = (float) bottomLeftRadius;
        invalidate();
    }

    public void setBottomRightRadius(int bottomRightRadius) {
        this.mRCHelper.radii[4] = (float) bottomRightRadius;
        this.mRCHelper.radii[5] = (float) bottomRightRadius;
        invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.mRCHelper.mStrokeWidth = strokeWidth;
        invalidate();
    }

    public void setStrokeColor(int strokeColor) {
        this.mRCHelper.mStrokeColor = strokeColor;
        invalidate();
    }

    public void invalidate() {
        RCHelper rCHelper = this.mRCHelper;
        if (rCHelper != null) {
            rCHelper.refreshRegion(this);
        }
        super.invalidate();
    }

    public boolean isClipBackground() {
        return this.mRCHelper.mClipBackground;
    }

    public boolean isRoundAsCircle() {
        return this.mRCHelper.mRoundAsCircle;
    }

    public float getTopLeftRadius() {
        return this.mRCHelper.radii[0];
    }

    public float getTopRightRadius() {
        return this.mRCHelper.radii[2];
    }

    public float getBottomLeftRadius() {
        return this.mRCHelper.radii[4];
    }

    public float getBottomRightRadius() {
        return this.mRCHelper.radii[6];
    }

    public int getStrokeWidth() {
        return this.mRCHelper.mStrokeWidth;
    }

    public int getStrokeColor() {
        return this.mRCHelper.mStrokeColor;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        this.mRCHelper.drawableStateChanged(this);
    }
}
