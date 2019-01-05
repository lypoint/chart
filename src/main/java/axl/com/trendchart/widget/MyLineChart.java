package axl.com.trendchart.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.utils.Utils;

public class MyLineChart extends LineChart {
    private Paint bottomPaint;
    public boolean isFirst = false;

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
        int save = canvas.save();

        if (isFirst){
            bottomPaint.setColor(Color.parseColor("#895BE6"));
            canvas.drawRect(new RectF(0, 0, getMeasuredWidth(),Utils.convertDpToPixel(45)),
                    bottomPaint);
            canvas.restoreToCount(save);
        }
        save = canvas.save();
        bottomPaint.setColor(Color.parseColor("#f7f7f7"));
        canvas.drawRect(new RectF(0, getMeasuredHeight()-Utils.convertDpToPixel(7), getMeasuredWidth(),
                getMeasuredHeight()), bottomPaint);
        canvas.restoreToCount(save);

        super.onDraw(canvas);


    }
}
