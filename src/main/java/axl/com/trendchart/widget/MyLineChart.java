package axl.com.trendchart.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.utils.Utils;

public class MyLineChart extends LineChart {
    private Paint bottomPaint;
    public boolean isFirst = false;
    //是否漂浮View
    public boolean isFloat = false;
    public boolean isDrawDivider = true;

    //设置头部的颜色，默认紫色
    private String headerColor = "#895BE6";
    //底部分隔线的颜色
    private String dividerColor = "#f7f7f7";

    //是否是漂浮的头部
    public boolean isFloatHeader = false;

    public void setDividerColor(String dividerColor) {
        this.dividerColor = dividerColor;
    }

    public void setHeaderColor(String headerColor) {
        this.headerColor = headerColor;
    }

    public MyLineChart(Context context) {
        super(context);
        initSetting();
    }

    public MyLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSetting();
    }

    public MyLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSetting();
    }

    private void initSetting() {
        bottomPaint = new Paint();
        bottomPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int save = canvas.save();
        if (isFirst) {
            //头部的紫色背景
            bottomPaint.setColor(Color.parseColor(headerColor));
            canvas.drawRect(new RectF(0, 0, getMeasuredWidth(), Utils.convertDpToPixel(45)),
                    bottomPaint);
            canvas.restoreToCount(save);
        }


        if (mData == null) {
            super.onDraw(canvas);
            return;
        }

        //        long starttime = System.currentTimeMillis();

        // execute all drawing commands
        drawGridBackground(canvas);

        if (mAutoScaleMinMaxEnabled) {
            autoScale();
        }

        if (mAxisLeft.isEnabled())
            mAxisRendererLeft.computeAxis(mAxisLeft.mAxisMinimum, mAxisLeft.mAxisMaximum, mAxisLeft.isInverted());

        if (mAxisRight.isEnabled())
            mAxisRendererRight.computeAxis(mAxisRight.mAxisMinimum, mAxisRight.mAxisMaximum, mAxisRight.isInverted());

        if (mXAxis.isEnabled())
            mXAxisRenderer.computeAxis(mXAxis.mAxisMinimum, mXAxis.mAxisMaximum, false);

        mXAxisRenderer.renderAxisLine(canvas);
        mAxisRendererLeft.renderAxisLine(canvas);
        mAxisRendererRight.renderAxisLine(canvas);




        // make sure the data cannot be drawn outside the content-rect
        int clipRestoreCount = canvas.save();
        canvas.clipRect(mViewPortHandler.getContentRect());

        if (!isFloatHeader)
            mRenderer.drawData(canvas);

        if (isDrawDivider) {
            //底部的分隔横线
            save = canvas.save();
            bottomPaint.setColor(Color.parseColor(dividerColor));
            canvas.drawRect(new RectF(0, getMeasuredHeight() - Utils.convertDpToPixel(7), getMeasuredWidth(),
                    getMeasuredHeight()), bottomPaint);
            canvas.restoreToCount(save);
        }
        mXAxisRenderer.renderGridLines(canvas);
        mAxisRendererLeft.renderGridLines(canvas);
        mAxisRendererRight.renderGridLines(canvas);

        // if highlighting is enabled
        if (valuesToHighlight())
            mRenderer.drawHighlighted(canvas, mIndicesToHighlight);

        // Removes clipping rectangle
        canvas.restoreToCount(clipRestoreCount);

        //补充后面缺失的分隔线
        if (isDrawDivider) {
            //底部的分隔横线
            save = canvas.save();
            bottomPaint.setColor(Color.parseColor(dividerColor));
            canvas.drawRect(new RectF(
                    getMeasuredWidth() - Utils.convertDpToPixel(94f),
                    getMeasuredHeight() - Utils.convertDpToPixel(7),
                    getMeasuredWidth(),
                    getMeasuredHeight()), bottomPaint);
            canvas.restoreToCount(save);
        }

        mRenderer.drawExtras(canvas);


        mXAxisRenderer.renderAxisLabels(canvas);
        mAxisRendererLeft.renderAxisLabels(canvas);
        mAxisRendererRight.renderAxisLabels(canvas);

        if (isClipValuesToContentEnabled()) {
            clipRestoreCount = canvas.save();
            canvas.clipRect(mViewPortHandler.getContentRect());

            mRenderer.drawValues(canvas);

            canvas.restoreToCount(clipRestoreCount);
        } else {
            mRenderer.drawValues(canvas);
        }

    }
}
