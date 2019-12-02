package com.android.mltcode.watchlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CircularRelativeLayout extends RelativeLayout {
    private static final int HEIGHT = 1;
    private static final int NONE = 2;
    private static final int WIDTH = 0;
    private Path ovalPath;
    private int primaryDimension;

    public CircularRelativeLayout(Context context) {
        super(context);
        init();
    }

    public CircularRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        this.primaryDimension = 0;
        this.ovalPath = new Path();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int i = this.primaryDimension;
        if (i == 0) {
            getLayoutParams().height = getMeasuredWidth();
        } else if (i == 1) {
            getLayoutParams().width = getMeasuredHeight();
        }
        this.ovalPath.reset();
        this.ovalPath.addOval(0.0f, 0.0f, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Path.Direction.CW);
        this.ovalPath.close();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.clipPath(this.ovalPath);
        super.onDraw(canvas);
    }

    public int getPrimaryDimension() {
        return this.primaryDimension;
    }

    public void setPrimaryDimension(int primaryDimension2) {
        this.primaryDimension = primaryDimension2;
    }
}
