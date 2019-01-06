package axl.com.trendchart.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

//自定义X轴显示
public class MyXAxisRenderer extends XAxisRenderer {
    private static Paint.FontMetrics mFontMetricsBuffer = new Paint.FontMetrics();
    private static Rect mDrawTextRectBuffer = new Rect();
    private int selectBottom = 14;
    private int unSelectBottom = 15;
    //可绘制的总体高度
    private float xAxisHeight = Utils.convertDpToPixel(45);

    public MyXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }

    @Override
    protected void drawLabels(Canvas c, float pos, MPPointF anchor) {
        final float labelRotationAngleDegrees = mXAxis.getLabelRotationAngle();
        boolean centeringEnabled = mXAxis.isCenterAxisLabelsEnabled();

        float[] positions = new float[mXAxis.mEntryCount * 2];

        for (int i = 0; i < positions.length; i += 2) {

            // only fill x values
            if (centeringEnabled) {
                positions[i] = mXAxis.mCenteredEntries[i / 2];
            } else {
                positions[i] = mXAxis.mEntries[i / 2];
            }
        }

        mTrans.pointValuesToPixel(positions);

        for (int i = 0; i < positions.length; i += 2) {

            float x = positions[i];

            if (mViewPortHandler.isInBoundsX(x)) {

                String label = mXAxis.getValueFormatter().getFormattedValue(mXAxis.mEntries[i / 2], mXAxis);

                if (mXAxis.isAvoidFirstLastClippingEnabled()) {

                    // avoid clipping of the last
                    if (i == mXAxis.mEntryCount - 1 && mXAxis.mEntryCount > 1) {
                        float width = Utils.calcTextWidth(mAxisLabelPaint, label);

                        if (width > mViewPortHandler.offsetRight() * 2
                                && x + width > mViewPortHandler.getChartWidth())
                            x -= width / 2;
                        // avoid clipping of the first
                    } else if (i == 0) {
                        float width = Utils.calcTextWidth(mAxisLabelPaint, label);
                        x += width / 2;
                    }
                }
                Log.e("drawXAxisValue,","label="+label+",x="+x+",pos="+pos);
                drawXAxisValue(c, label, x, pos, mAxisLabelPaint, anchor, labelRotationAngleDegrees);
            }
        }
    }


    public void drawXAxisValue(Canvas c, String text, float x, float y,
                               Paint paint,
                               MPPointF anchor, float angleDegrees) {

        float drawOffsetX = 0.f;
        float drawOffsetY = 0.f;

        final float lineHeight = paint.getFontMetrics(mFontMetricsBuffer);
        paint.getTextBounds(text, 0, text.length(), mDrawTextRectBuffer);

        // Android sometimes has pre-padding
        drawOffsetX -= mDrawTextRectBuffer.left;

        drawOffsetY += -mFontMetricsBuffer.ascent;

        Paint.Align originalTextAlign = paint.getTextAlign();
        paint.setTextAlign(Paint.Align.LEFT);

        //以上注释是源码的逻辑，下面的是自己的逻辑，重新计算drawOffsetY
        if (text.contains("月")) {//带月的显示规则
            paint.setColor(Color.parseColor("#D0C3DB"));
            String month = "("+text.substring(0,text.indexOf("月")+1)+")";
            drawText(c,paint,month,9,anchor,drawOffsetX,drawOffsetY, x,31);
            drawText(c,paint,text.substring(text.indexOf("月")+1),11,anchor,drawOffsetX,
                    drawOffsetY,x,unSelectBottom);
        } else if (text.equals("29日")) {//被选中
            paint.setColor(Color.parseColor("#ffffff"));
            paint.setTextSize(Utils.convertDpToPixel(14));
            paint.getTextBounds(text, 0, text.length(), mDrawTextRectBuffer);
            if (anchor.x != 0.f || anchor.y != 0.f) {
                drawOffsetX -= mDrawTextRectBuffer.width() * anchor.x;
            }
            drawOffsetX += x;
            drawOffsetY = xAxisHeight - Utils.convertDpToPixel(selectBottom);//距离顶部11个dp
            c.drawText(text, drawOffsetX, drawOffsetY, paint);
        } else {//未被选中
            drawText(c,paint,text,11,anchor,drawOffsetX,drawOffsetY,x,unSelectBottom);
        }
        paint.setStrokeWidth(Utils.convertDpToPixel(1f));
        c.drawLine(x, xAxisHeight-Utils.convertDpToPixel(2), x, xAxisHeight - Utils
                .convertDpToPixel(10f), paint);
        paint.setTextAlign(originalTextAlign);
    }

    private void drawText(Canvas c,Paint paint,String text,float textsize,MPPointF anchor,float
            drawOffsetX,float drawOffsetY,float x,float marginBottom){
        paint.setColor(Color.parseColor("#D0C3DB"));
        paint.setTextSize(Utils.convertDpToPixel(textsize));
        paint.getTextBounds(text, 0, text.length(), mDrawTextRectBuffer);
        if (anchor.x != 0.f || anchor.y != 0.f) {
            drawOffsetX -= mDrawTextRectBuffer.width() * anchor.x;
        }
        drawOffsetX += x;
        drawOffsetY = xAxisHeight - Utils.convertDpToPixel(marginBottom);//距离顶部11个dp
        c.drawText(text, drawOffsetX, drawOffsetY, paint);
    }
}
