package com.android.mltcode.watchlib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import java.util.Locale;

public final class SizeUtils {

    static float volatility = 0.5f;

    public interface onGetSizeListener {
        void onGetSize(View view);
    }

    private SizeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + volatility);
    }

    public static int px2dp(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + volatility);
    }

    public static int sp2px(Context context, float spValue) {
        return (int) ((spValue * context.getResources().getDisplayMetrics().scaledDensity) + volatility);
    }

    public static int px2sp(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().scaledDensity) + volatility);
    }

    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        if (unit == 0) {
            return value;
        }
        if (unit == 1) {
            return metrics.density * value;
        }
        if (unit == 2) {
            return metrics.scaledDensity * value;
        }
        if (unit == 3) {
            return metrics.xdpi * value * 0.013888889f;
        }
        if (unit == 4) {
            return metrics.xdpi * value;
        }
        if (unit != 5) {
            return 0.0f;
        }
        return metrics.xdpi * value * 0.03937008f;
    }

    public static void forceGetViewSize(final View view, final onGetSizeListener listener) {
        view.post(new Runnable() {
            public void run() {
                onGetSizeListener ongetsizelistener = listener;
                if (ongetsizelistener != null) {
                    ongetsizelistener.onGetSize(view);
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    public static int[] measureView(View view) {
        int heightSpec;
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(-1, -2);
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    public static int getMeasuredWidth(View view) {
        return measureView(view)[0];
    }

    public static int getMeasuredHeight(View view) {
        return measureView(view)[1];
    }

    public static String formatFloat(float value) {
        return String.format(Locale.getDefault(), "%.3f", new Object[]{Float.valueOf(value)});
    }

    public static String getPrecisionFormat(int precision) {
        return "%." + precision + "f";
    }

    public static int measure(int measureSpec, int defaultSize) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        if (specMode == 1073741824) {
            return specSize;
        }
        if (specMode == Integer.MIN_VALUE) {
            return Math.min(result, specSize);
        }
        return result;
    }

    public static float measureTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return Math.abs(fontMetrics.ascent) - fontMetrics.descent;
    }
}
