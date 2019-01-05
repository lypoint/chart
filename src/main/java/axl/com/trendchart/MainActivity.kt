package axl.com.trendchart

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import axl.com.trendchart.mychart.CoupleChartGestureListener
import axl.com.trendchart.mychart.NewCombinedChart
import axl.com.trendchart.mychart.vo.DataParse
import com.asiainnovations.ace.quotes.detail.kline.vo.KLineBean
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.lineChart).setOnClickListener {
            openActivity(MyChartAct::class.java)
        }

    }

    fun combine(){
        initKline(this, newCombinedChart)
        initKline(this, newCombinedChart2)
        initKline(this, newCombinedChart3)

        initChartListener()

        kLineDatas = arrayListOf()
        for (i in 0 until 100) {
            val bean = KLineBean()
            if (i == 0) {
                val isUp = Random().nextInt(1) % 2 == 0
                bean.open = Random().nextInt(100).toFloat()
                bean.close = bean.open +
                        Random().nextInt(Math.abs(bean.open.times(if (isUp) 0.1 else -0.1).toInt()).let {
                            if (it == 0) {
                                bean.open.toInt()
                            } else {
                                it
                            }
                        })

                bean.high = bean.open + Random().nextInt(Math.abs(bean.open.times(0.1).toInt()).let {
                    if (it == 0) {
                        bean.open.toInt()
                    } else {
                        it
                    }
                })
                bean.low = bean.open + Random().nextInt(Math.abs(bean.open.times(-0.1).toInt()))
                bean.vol = Random().nextInt(100000).plus(100000).toFloat()
            } else {
                val isUp = Random().nextInt(3) % 2 == 0
                bean.open = kLineDatas!![i - 1].close
                bean.close = bean.open +
                        Random().nextInt(Math.abs(bean.open.times(0.1).toInt())).times(if (isUp) 1 else -1)

                bean.high = bean.open + Random().nextInt(Math.abs(bean.open.times(0.1).toInt()))
                bean.low = bean.open + Random().nextInt(Math.abs(bean.open.times(0.1).toInt())).times(-1)
                bean.vol = Random().nextInt(100000).plus(100000).toFloat()
            }

            kLineDatas!!.add(bean)

        }

        if (kLineDatas!!.size > 0) {

            mData.initLineDatas(kLineDatas)
            newCombinedChart.minuteHelper = mData
            newCombinedChart2.minuteHelper = mData
            newCombinedChart3.minuteHelper = mData
            //分时图

            setTimeSharingWire(newCombinedChart)
            setTimeSharingWire(newCombinedChart2)
            setTimeSharingWire(newCombinedChart3)


            newCombinedChart.invalidate()
            newCombinedChart2.invalidate()
            newCombinedChart3.invalidate()
            //刷新数据

            newCombinedChart.notifyDataSetChanged()
            newCombinedChart2.notifyDataSetChanged()
            newCombinedChart3.notifyDataSetChanged()


            //设置滚动到最后一个数据
            newCombinedChart.moveViewToX(newCombinedChart.xChartMax)
            newCombinedChart2.moveViewToX(newCombinedChart.xChartMax)
            newCombinedChart3.moveViewToX(newCombinedChart.xChartMax)
        }
    }


    fun initKline(context: Context, kline_chart_k: CombinedChart) {

        kline_chart_k.setScaleEnabled(true)//启用图表缩放事件
        kline_chart_k.setDrawBorders(false)//是否绘制边线
        kline_chart_k.isDragEnabled = true//启用图表拖拽事件
        kline_chart_k.isScaleYEnabled = false//启用Y轴上的缩放
        kline_chart_k.minOffset = 0f
        kline_chart_k.description = null
        kline_chart_k.setExtraOffsets(0f, 0f, 0f, 0f)
        kline_chart_k.setViewPortOffsets(0f, 0f, context.dip(52f).toFloat(), 0f)
        kline_chart_k.isAutoScaleMinMaxEnabled = true
        val lineChartLegend = kline_chart_k.legend
        lineChartLegend.isEnabled = false//是否绘制 Legend 图例

        //bar x y轴
        val xAxisKline = kline_chart_k.xAxis
        xAxisKline.setDrawLabels(false) //是否显示X坐标轴上的刻度，默认是true
        xAxisKline.setDrawGridLines(false)//是否显示X坐标轴上的刻度竖线，默认是true
        xAxisKline.setDrawAxisLine(false) //是否绘制坐标轴的线，即含有坐标的那条线，默认是true

        xAxisKline.textColor = context.resources.getColor(R.color.quote_grey_color)//设置字的颜色
        xAxisKline.position = XAxis.XAxisPosition.BOTTOM//设置值显示在什么位置
        xAxisKline.setAvoidFirstLastClipping(true)//设置首尾的值是否自动调整，避免被遮挡

        val axisLeftKline = kline_chart_k.axisRight
        axisLeftKline.setDrawGridLines(false)
        axisLeftKline.setDrawAxisLine(false)
        axisLeftKline.setDrawZeroLine(false)
        axisLeftKline.setDrawLabels(true)
        axisLeftKline.textColor = context.resources.getColor(R.color.quote_main_text_color)
        axisLeftKline.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        axisLeftKline.setLabelCount(4, false) //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        val axisRightKline = kline_chart_k.axisLeft
        axisRightKline.setDrawLabels(false)
        axisRightKline.setDrawGridLines(false)
        axisRightKline.setDrawAxisLine(false)
        axisRightKline.setLabelCount(4, false) //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        kline_chart_k.isDragDecelerationEnabled = true
        kline_chart_k.dragDecelerationFrictionCoef = 0.2f
    }


    //解析数据
    private var mData: DataParse = DataParse()

    //K线图数据
    private var kLineDatas: ArrayList<KLineBean>? = null

    private fun setTimeSharingWire(combinedChart: NewCombinedChart) {
        mData.initTimeSharingWireData(kLineDatas)

//        val set = LineDataSet(mData.timeSharingWireDataL, "DataSet 2")
//        set.color = 0xBCBCBC
//        set.lineWidth = 1f
//        set.setDrawCircles(false)
//        set.setDrawCircleHole(false)
//        set.setDrawValues(false)
//        set.isHighlightEnabled = false
//        set.axisDependency = YAxis.AxisDependency.LEFT
//        set.mode = LineDataSet.Mode.CUBIC_BEZIER
//        set.setDrawFilled(true)
//        if (Utils.getSDKInt() >= 18) {
//            val drawable = ContextCompat.getDrawable(this, R.drawable.drawable_k_line_fill_color)
//            set.fillDrawable = drawable
//        } else {
//            set.fillColor = Color.YELLOW
//        }


        val set2 = LineDataSet(mData.timeSharingWireDataL, "DataSet 2")
        // create a dataset and give it a type
        set2.axisDependency = YAxis.AxisDependency.LEFT
        set2.color = 0x00DFFF
        set2.setDrawCircles(true)
//        set2.lineWidth = dip(0.5f).toFloat()
        set2.circleRadius = 5f
        set2.fillAlpha = 255
        set2.setDrawFilled(true)
//        set2.fillColor = Color.WHITE
        set2.setDrawCircleHole(true)
//        set2.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        set2.highLightColor = Color.WHITE
        if (com.github.mikephil.charting.utils.Utils.getSDKInt() >= 18) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.drawable_k_line_fill_color)
            set2.fillDrawable = drawable
        } else {
            set2.fillColor = Color.WHITE
        }

        val data = LineData(set2)
        data.setDrawValues(true)

        val combinedData = CombinedData()
        combinedData.setData(data)
        combinedChart.data = combinedData

    }



    //注册手势 实现双图联动
    private fun initChartListener() {
        newCombinedChart.onChartGestureListener = CoupleChartGestureListener(newCombinedChart, arrayOf(newCombinedChart2,newCombinedChart3)) //CoupleChartGestureListener(spread_pie_chart, arrayOf(spread_pie_trading_volume), null)
        newCombinedChart2.onChartGestureListener = CoupleChartGestureListener(newCombinedChart2, arrayOf(newCombinedChart,newCombinedChart3))
        newCombinedChart3.onChartGestureListener = CoupleChartGestureListener(newCombinedChart3, arrayOf(newCombinedChart,newCombinedChart2))

        newCombinedChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry, h: Highlight) {
                if (newCombinedChart2.h == null || newCombinedChart2.h!!.x != h.x) {
                    val highlight: Highlight
                    val touchY = h.yPx - newCombinedChart2.height
                    val h1 = newCombinedChart2.getHighlightByTouchPoint(h.x, touchY)
                    highlight = if (null == h1) {
                        Highlight(h.x, 0f, h.xPx, touchY, h.dataIndex, h.dataSetIndex, h.axis)
                    } else {
                        Highlight(h.x, h.y, h.xPx, touchY, h.dataIndex, h.dataSetIndex, h.axis)
                    }
                    newCombinedChart.h = h
                    newCombinedChart2.highlightValues(arrayOf(highlight))
//                    updateText(h.x.toInt())
                }

                if (newCombinedChart3.h == null || newCombinedChart3.h!!.x != h.x) {
                    val highlight: Highlight
                    val touchY = h.yPx - newCombinedChart3.height
                    val h1 = newCombinedChart3.getHighlightByTouchPoint(h.x, touchY)
                    highlight = if (null == h1) {
                        Highlight(h.x, 0f, h.xPx, touchY, h.dataIndex, h.dataSetIndex, h.axis)
                    } else {
                        Highlight(h.x, h.y, h.xPx, touchY, h.dataIndex, h.dataSetIndex, h.axis)
                    }
                    newCombinedChart.h = h
                    newCombinedChart3.highlightValues(arrayOf(highlight))
//                    updateText(h.x.toInt())
                }

            }

            override fun onNothingSelected() {
            }
        })
        newCombinedChart2.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
            }

            override fun onValueSelected(e: Entry, h: Highlight) {
                if (newCombinedChart.h == null || newCombinedChart.h!!.x != h.x) {
                    val highlight: Highlight
                    val touchY = h.yPx + newCombinedChart2.height
                    val h1 = newCombinedChart2.getHighlightByTouchPoint(h.x, touchY)
                    highlight = if (null == h1) {
                        Highlight(h.x, 0f, h.xPx, touchY, h.dataIndex, h.dataSetIndex, h.axis)
                    } else {
                        Highlight(h.x, h.y, h.xPx, touchY, h.dataIndex, h.dataSetIndex, h.axis)
                    }
                    newCombinedChart2.h = h
                    newCombinedChart.highlightValues(arrayOf(highlight))
//                    updateText(h.x.toInt())
                }

                if (newCombinedChart3.h == null || newCombinedChart3.h!!.x != h.x) {
                    val highlight: Highlight
                    val touchY = h.yPx + newCombinedChart2.height
                    val h1 = newCombinedChart3.getHighlightByTouchPoint(h.x, touchY)
                    highlight = if (null == h1) {
                        Highlight(h.x, 0f, h.xPx, touchY, h.dataIndex, h.dataSetIndex, h.axis)
                    } else {
                        Highlight(h.x, h.y, h.xPx, touchY, h.dataIndex, h.dataSetIndex, h.axis)
                    }
                    newCombinedChart2.h = h
                    newCombinedChart3.highlightValues(arrayOf(highlight))
//                    updateText(h.x.toInt())
                }
            }
        })


        newCombinedChart3.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
            }

            override fun onValueSelected(e: Entry, h: Highlight) {
                if (newCombinedChart.h == null || newCombinedChart.h!!.x != h.x) {
                    val highlight: Highlight
                    val touchY = h.yPx + newCombinedChart3.height
                    val h1 = newCombinedChart3.getHighlightByTouchPoint(h.x, touchY)
                    highlight = if (null == h1) {
                        Highlight(h.x, 0f, h.xPx, touchY, h.dataIndex, h.dataSetIndex, h.axis)
                    } else {
                        Highlight(h.x, h.y, h.xPx, touchY, h.dataIndex, h.dataSetIndex, h.axis)
                    }
                    newCombinedChart3.h = h
                    newCombinedChart.highlightValues(arrayOf(highlight))
//                    updateText(h.x.toInt())
                }

                if (newCombinedChart2.h == null || newCombinedChart2.h!!.x != h.x) {
                    val highlight: Highlight
                    val touchY = h.yPx + newCombinedChart3.height
                    val h1 = newCombinedChart3.getHighlightByTouchPoint(h.x, touchY)
                    highlight = if (null == h1) {
                        Highlight(h.x, 0f, h.xPx, touchY, h.dataIndex, h.dataSetIndex, h.axis)
                    } else {
                        Highlight(h.x, h.y, h.xPx, touchY, h.dataIndex, h.dataSetIndex, h.axis)
                    }
                    newCombinedChart3.h = h
                    newCombinedChart2.highlightValues(arrayOf(highlight))
//                    updateText(h.x.toInt())
                }
            }
        })

    }
}
