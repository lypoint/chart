package axl.com.trendchart.mychart


import android.graphics.Matrix
import android.view.MotionEvent
import android.view.View

import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener

/**
 * User: zeyu.zhu(Axl.Jacobs@gmail.com)
 * Date: 2018/6/15
 * Time: 上午9:57
 */
class CoupleChartGestureListener(private val srcChart: Chart<*>, private val dstCharts: Array<Chart<*>>) : OnChartGestureListener {

    override fun onChartGestureStart(me: MotionEvent, lastPerformedGesture: ChartTouchListener.ChartGesture) {
        syncCharts()
    }

    override fun onChartGestureEnd(me: MotionEvent, lastPerformedGesture: ChartTouchListener.ChartGesture) {
        syncCharts()
    }

    override fun onChartLongPressed(me: MotionEvent) {
        syncCharts()
    }

    override fun onChartDoubleTapped(me: MotionEvent) {
        syncCharts()
    }

    override fun onChartSingleTapped(me: MotionEvent) {
        syncCharts()
    }

    override fun onChartFling(me1: MotionEvent, me2: MotionEvent, velocityX: Float, velocityY: Float) {
        syncCharts()
    }

    override fun onChartScale(me: MotionEvent, scaleX: Float, scaleY: Float) {
        //        Log.d(TAG, "onChartScale " + scaleX + "/" + scaleY + " X=" + me.getX() + "Y=" + me.getY());
        syncCharts()
    }

    override fun onChartTranslate(me: MotionEvent, dX: Float, dY: Float) {
        //        Log.d(TAG, "onChartTranslate " + dX + "/" + dY + " X=" + me.getX() + "Y=" + me.getY());
        syncCharts()
    }

    fun syncCharts() {
        val srcMatrix: Matrix
        val srcVals = FloatArray(9)
        var dstMatrix: Matrix
        val dstVals = FloatArray(9)
        // get src chart translation matrix:
        srcMatrix = srcChart.viewPortHandler.matrixTouch
        srcMatrix.getValues(srcVals)
        // apply X axis scaling and position to dst charts:
        for (dstChart in dstCharts) {
            if (dstChart.visibility == View.VISIBLE) {
                dstMatrix = dstChart.viewPortHandler.matrixTouch
                dstMatrix.getValues(dstVals)

                dstVals[Matrix.MSCALE_X] = srcVals[Matrix.MSCALE_X]
                dstVals[Matrix.MSKEW_X] = srcVals[Matrix.MSKEW_X]
                dstVals[Matrix.MTRANS_X] = srcVals[Matrix.MTRANS_X]
                dstVals[Matrix.MSKEW_Y] = srcVals[Matrix.MSKEW_Y]
                dstVals[Matrix.MSCALE_Y] = srcVals[Matrix.MSCALE_Y]
                dstVals[Matrix.MTRANS_Y] = srcVals[Matrix.MTRANS_Y]
                dstVals[Matrix.MPERSP_0] = srcVals[Matrix.MPERSP_0]
                dstVals[Matrix.MPERSP_1] = srcVals[Matrix.MPERSP_1]
                dstVals[Matrix.MPERSP_2] = srcVals[Matrix.MPERSP_2]

                dstMatrix.setValues(dstVals)
                dstChart.viewPortHandler.refresh(dstMatrix, dstChart, true)
            }
        }
    }
}