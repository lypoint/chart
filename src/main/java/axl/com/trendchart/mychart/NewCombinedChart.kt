package axl.com.trendchart.mychart

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import axl.com.trendchart.mychart.vo.DataParse
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.highlight.Highlight


/**
 * User: zeyu.zhu(Axl.Jacobs@gmail.com)
 * Date: 2018/9/12
 * Time: 下午6:47
 */
class NewCombinedChart : CombinedChart {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)


    var myMarkerViewLeft: NewLeftMarkerView? = null
    var myMarkerViewBottom: NewBottomMarkerView? = null
    var myMarkerViewHorizontalLine: NewHorizontalLineMarkerView? = null
//    var myMarkerViewVerticalLine: NewVerticalLineMarkerView? = null
    var minuteHelper: DataParse? = null
    var mySecondChart: NewCombinedChart? = null

    private var currentX = 0f
    private var currentY = 0f

    var h: Highlight? = null

    //长按时长
    private val LONG_PRESS_TIME: Long = 500

    private var currentTime = 0L

    private val longPressedThread = Runnable {
        isDragEnabled = false
    }


    override fun setOnTouchListener(l: OnTouchListener?){}

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentX = event.x
                currentY = event.y
                currentTime = System.currentTimeMillis()
                handler.postDelayed(longPressedThread, LONG_PRESS_TIME)
            }
            MotionEvent.ACTION_MOVE -> {
                if (isMoved(event.x, event.y)) {
                    handler.removeCallbacks(longPressedThread)
                }
                if (!isDragEnabled) {
                    highlightValue(getHighlightByTouchPoint(event.x, event.y))
                }
            }
            MotionEvent.ACTION_UP -> {
                handler.removeCallbacks(longPressedThread)
                isDragEnabled = true
                highlightValue(null)
                mySecondChart?.highlightValue(null)

            }
            MotionEvent.ACTION_CANCEL -> {
                handler.removeCallbacks(longPressedThread)
                isDragEnabled = true
                highlightValue(null)
                mySecondChart?.highlightValue(null)
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 判断是否移动了
     */
    private fun isMoved(mCurrentInScreenX: Float, mCurrentInScreenY: Float): Boolean {
        return !(Math.abs(currentX - mCurrentInScreenX) <= 20 && Math.abs(currentY - mCurrentInScreenY) <= 20)
    }

    override fun drawMarkers(canvas: Canvas?) {
        if (mIndicesToHighlight == null || !valuesToHighlight())
            return
        for (i in mIndicesToHighlight.indices) {
            val highlight = mIndicesToHighlight[i]
            val xIndex = mIndicesToHighlight[i].dataIndex
            val deltaX = if (mXAxis != null)
                mXAxis.mAxisRange
            else
                (if (mData == null) 0f - 1f else mData.entryCount - 1f)

            if (xIndex <= deltaX && xIndex <= deltaX * mAnimator.phaseX) {
                val pos = getMarkerPosition(highlight)
                // check bounds
                if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                    continue

                if (myMarkerViewHorizontalLine != null) {
                    myMarkerViewHorizontalLine?.viewWidth = width
                    myMarkerViewHorizontalLine?.refreshContent(getEntryByTouchPoint(x, y), mIndicesToHighlight[0], minuteHelper!!)
                    myMarkerViewHorizontalLine?.draw(canvas)
                }

//                if (myMarkerViewVerticalLine != null) {
//                    myMarkerViewVerticalLine?.viewHeight = height
//                    myMarkerViewVerticalLine?.refreshContent(getEntryByTouchPoint(x, y), mIndicesToHighlight[0], minuteHelper!!)
//                    myMarkerViewVerticalLine?.draw(canvas)
//                }

                if (myMarkerViewLeft != null) {

                    val pos = getMarkerPosition(mIndicesToHighlight[0])
                    myMarkerViewLeft?.refreshContent(getEntryByTouchPoint(x, y), mIndicesToHighlight[0])
                    val width = mViewPortHandler.contentWidth().toInt()
                    myMarkerViewLeft?.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                    myMarkerViewLeft?.layout(0, 0, width, myMarkerViewLeft?.measuredHeight ?: 0)
                    myMarkerViewLeft?.draw(canvas, mViewPortHandler.contentLeft(), pos[1])
                    mSelectionListener?.onValueSelected(getEntryByTouchPoint(x, y), mIndicesToHighlight[0])
                }

                if (myMarkerViewBottom != null) {
                    myMarkerViewBottom?.viewHeight = height
                    myMarkerViewBottom?.refreshContent(getEntryByTouchPoint(x, y), mIndicesToHighlight[0], minuteHelper!!)
                    myMarkerViewBottom?.draw(canvas)
                }
            }
        }
    }
}