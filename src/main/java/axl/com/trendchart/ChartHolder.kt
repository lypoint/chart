package axl.com.trendchart

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import axl.com.trendchart.mychart.vo.DataParse
import com.asiainnovations.ace.quotes.detail.kline.vo.KLineBean
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.Utils
import kotlinx.android.synthetic.main.chart_item.view.*
import java.util.*


open class ChartHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open fun loadData() {
        initKline(itemView.context, itemView.newCombinedChart)
        initChartListener()

        //解析数据
        var mData = DataParse()
        var kLineDatas: ArrayList<KLineBean> = arrayListOf()
        for (i in 0 until 30) {
            val bean = KLineBean()
            bean.open = i.toFloat()
            bean.close = i.toFloat()
            bean.high = i.toFloat()
            bean.low = i.toFloat()
            bean.vol = i.toFloat()
            kLineDatas!!.add(bean)

        }


        if (kLineDatas.size > 0) {

            mData.initLineDatas(kLineDatas)
            itemView.newCombinedChart.minuteHelper = mData

            mData.initTimeSharingWireData(kLineDatas)
            setTimeSharingWire(LineDataSet(mData.timeSharingWireDataL, "DataSet 2"))

            itemView.newCombinedChart.invalidate()
            //刷新数据
            itemView.newCombinedChart.notifyDataSetChanged()

            //设置滚动到最后一个数据
            itemView.newCombinedChart.moveViewToX(itemView.newCombinedChart.xChartMax)
        }
    }

    //注册手势 实现双图联动
    private fun initChartListener() {
        //TODO
    }

    private fun initKline(context: Context, kline_chart_k: CombinedChart) {
        var params = kline_chart_k.layoutParams
        if (params != null) {
            params.width = Contants.chartWidth.toInt()
            kline_chart_k.layoutParams = params
        }

        kline_chart_k.setScaleEnabled(false)//启用图表缩放事件
        kline_chart_k.setDrawBorders(false)//是否绘制边线
        kline_chart_k.isDragXEnabled = true//启用图表拖拽事件
        kline_chart_k.isScaleYEnabled = false//启用Y轴上的缩放
        kline_chart_k.isScaleXEnabled = false//不启用X轴的缩放
        kline_chart_k.minOffset = 0f
        kline_chart_k.description = null
        kline_chart_k.setExtraOffsets(Contants.chartPaddingLeftDp, 0f, Contants.chartPaddingRightDp, 0f)
//        kline_chart_k.setViewPortOffsets(0f, 0f, context.dip(52f).toFloat(), 0f)
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
        xAxisKline.setAvoidFirstLastClipping(false)//设置首尾的值是否自动调整，避免被遮挡

        xAxisKline.setLabelCount(5, true)


        kline_chart_k.axisRight.setDrawLabels(false)
        kline_chart_k.axisRight.setDrawGridLines(false)
        kline_chart_k.axisRight.setDrawAxisLine(false)
        kline_chart_k.axisRight.setLabelCount(4, false)

        kline_chart_k.axisLeft.setDrawLabels(false)
        kline_chart_k.axisLeft.setDrawGridLines(false)
        kline_chart_k.axisLeft.setDrawAxisLine(false)
        kline_chart_k.axisLeft.setLabelCount(4, false) //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        kline_chart_k.isDragDecelerationEnabled = true
        kline_chart_k.dragDecelerationFrictionCoef = 0.2f
        kline_chart_k.isLogEnabled = true
    }


    private fun setTimeSharingWire(set2: LineDataSet) {
        // create a dataset and give it a type
        set2.axisDependency = YAxis.AxisDependency.LEFT
        set2.color = 0x00DFFF
        set2.setDrawCircles(true)
        set2.lineWidth = Utils.convertDpToPixel(0.5f)
        set2.circleRadius = 5f
        set2.fillAlpha = 255
        set2.setDrawFilled(true)
//        set2.fillColor = Color.WHITE
        set2.setDrawCircleHole(true)
//        set2.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        set2.highLightColor = Color.WHITE
        if (com.github.mikephil.charting.utils.Utils.getSDKInt() >= 18) {
            val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.drawable_k_line_fill_color)
            set2.fillDrawable = drawable
        } else {
            set2.fillColor = Color.WHITE
        }

        val data = LineData(set2)
        data.setDrawValues(true)

        val combinedData = CombinedData()
        combinedData.setData(data)
        itemView.newCombinedChart.data = combinedData

    }
}
