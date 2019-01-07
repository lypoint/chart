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
    public boolean isDrawDivider = true;
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

    private void initSetting(){
        bottomPaint = new Paint();
        bottomPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mData == null){
            super.onDraw(canvas);
            return;
        }


        int save = canvas.save();
        if (isFirst){
            //头部的紫色背景
            bottomPaint.setColor(Color.parseColor("#895BE6"));
            canvas.drawRect(new RectF(0, 0, getMeasuredWidth(),Utils.convertDpToPixel(45)),
                    bottomPaint);
            canvas.restoreToCount(save);
        }



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

        // make sure the data cannot be drawn outside the content-rect
        int clipRestoreCount = canvas.save();
        canvas.clipRect(mViewPortHandler.getContentRect());

        mRenderer.drawData(canvas);

        // Removes clipping rectangle
        canvas.restoreToCount(clipRestoreCount);

        if (isDrawDivider){
            //底部的分隔横线
            save = canvas.save();
            bottomPaint.setColor(Color.parseColor("#f7f7f7"));
            canvas.drawRect(new RectF(0, getMeasuredHeight()-Utils.convertDpToPixel(7), getMeasuredWidth(),
                    getMeasuredHeight()), bottomPaint);
            canvas.restoreToCount(save);
        }

        mXAxisRenderer.renderGridLines(canvas);
        mAxisRendererLeft.renderGridLines(canvas);
        mAxisRendererRight.renderGridLines(canvas);
        // if highlighting is enabled
        if (valuesToHighlight())
            mRenderer.drawHighlighted(canvas, mIndicesToHighlight);

        //头部的刻度线
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
