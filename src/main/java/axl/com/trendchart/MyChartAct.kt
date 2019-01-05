package axl.com.trendchart

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils

import java.util.ArrayList

import axl.com.trendchart.widget.MyLineChart

class MyChartAct : AppCompatActivity() {
    var chartList: MutableList<MyLineChart> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_chart)
        chartList.add(findViewById<View>(R.id.lineChart1) as MyLineChart)
        chartList.add(findViewById<View>(R.id.lineChart2) as MyLineChart)
        chartList.add(findViewById<View>(R.id.lineChart3) as MyLineChart)
        chartList.add(findViewById<View>(R.id.lineChart4) as MyLineChart)

        for (chartAct in chartList) {
            initKline(chartAct)
            addTestData(chartAct)
        }
        initChartListener()
    }

    private fun initKline(lineChart1: MyLineChart) {
        lineChart1.setScaleEnabled(true) //启用图表缩放事件
        lineChart1.setDrawBorders(false) //是否绘制边线
        lineChart1.isDragEnabled = true
        lineChart1.isScaleYEnabled = false
        lineChart1.minOffset = 0f
        lineChart1.description = null

        val legend = lineChart1.legend
        legend.isEnabled = false

        val xAxis = lineChart1.xAxis
        xAxis.position = XAxis.XAxisPosition.TOP

        lineChart1.rendererXAxis
        xAxis.labelCount = 5
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawLimitLinesBehindData(true)

        xAxis.setDrawGridLines(true) //设置x轴上每个点对应的线
        xAxis.gridLineWidth = 1f
        xAxis.gridColor = Color.parseColor("#D3CFE5")

        xAxis.enableGridDashedLine(20f, 5f, 0f)
        xAxis.gridColor = Color.parseColor("#D3CFE5")
        xAxis.valueFormatter = IAxisValueFormatter { value, axis ->
            value.toInt().toString()
        }

        //设置padding
        if (lineChart1.id == R.id.lineChart1) {
            lineChart1.isFirst = true
            lineChart1.setViewPortOffsets(Utils.convertDpToPixel(5f), Utils.convertDpToPixel(45f), Utils.convertDpToPixel(94f), Utils.convertDpToPixel(0f))
        } else {
            lineChart1.setViewPortOffsets(Utils.convertDpToPixel(5f), Utils.convertDpToPixel(0f), Utils.convertDpToPixel(94f), Utils.convertDpToPixel(0f))
        }

        val axisRight = lineChart1.axisRight
        axisRight.setDrawGridLines(false)

        axisRight.setDrawAxisLine(false)
        axisRight.setDrawZeroLine(false)
        axisRight.axisMinimum = 0f
        axisRight.setDrawLabels(false)
        axisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        axisRight.setLabelCount(4, false) //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        val axisLeft = lineChart1.axisLeft
        axisLeft.setDrawLabels(false)
        axisLeft.setDrawGridLines(false)
        axisLeft.setDrawZeroLine(false)
        axisLeft.setDrawAxisLine(false)
        axisRight.axisMinimum = 0f
        axisLeft.setLabelCount(4, false) //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
    }

    private fun addTestData(lineChart1: MyLineChart) {
        val values = ArrayList<Entry>()
        for (i in 0..29) {
            if (i == 0) {
                values.add(Entry(i.toFloat(), 14f))
            } else if (i == 1) {
                values.add(Entry(i.toFloat(), 21f))
            } else if (i == 2) {
                values.add(Entry(i.toFloat(), 67f))
            } else if (i == 3) {
                values.add(Entry(i.toFloat(), 54f))
            } else if (i == 4) {
                values.add(Entry(i.toFloat(), 89f))
            } else if (i == 5) {
                values.add(Entry(i.toFloat(), 22f))
            } else if (i == 6) {
                values.add(Entry(i.toFloat(), 29f))
            } else if (i == 7) {
                values.add(Entry(i.toFloat(), 93f))
            } else if (i == 8) {
                values.add(Entry(i.toFloat(), 3f))
            } else if (i == 9) {
                values.add(Entry(i.toFloat(), 0f))
            } else if (i == 10) {
                values.add(Entry(i.toFloat(), 87f))
            } else if (i == 11) {
                values.add(Entry(i.toFloat(), 57f))
            } else if (i == 12) {
                values.add(Entry(i.toFloat(), 23f))
            } else if (i == 13) {
                values.add(Entry(i.toFloat(), 22f))
            } else if (i == 14) {
                values.add(Entry(i.toFloat(), 80f))
            } else if (i == 15) {
                values.add(Entry(i.toFloat(), 56f))
            } else if (i == 16) {
                values.add(Entry(i.toFloat(), 40f))
            } else if (i == 17) {
                values.add(Entry(i.toFloat(), 100f))
            } else if (i == 18) {
                values.add(Entry(i.toFloat(), 8f))
            } else if (i == 19) {
                values.add(Entry(i.toFloat(), 3f))
            } else if (i == 20) {
                values.add(Entry(i.toFloat(), 10f))
            } else if (i == 21) {
                values.add(Entry(i.toFloat(), 40f))
            } else if (i == 22) {
                values.add(Entry(i.toFloat(), 50f))
            } else if (i == 23) {
                values.add(Entry(i.toFloat(), 30f))
            } else if (i == 24) {
                values.add(Entry(i.toFloat(), 22f))
            } else if (i == 25) {
                values.add(Entry(i.toFloat(), 9f))
            } else if (i == 26) {
                values.add(Entry(i.toFloat(), 5f))
            } else if (i == 27) {
                values.add(Entry(i.toFloat(), 8f))
            } else if (i == 28) {
                values.add(Entry(i.toFloat(), 5f))
            } else if (i == 29) {
                values.add(Entry(i.toFloat(), 0f))
            }
        }

        val set1 = LineDataSet(values, "")

        //颜色填充的渐变
        set1.setDrawFilled(true) //必须加上这一句，否则没有填充渐变
        if (Utils.getSDKInt() >= 18) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.fade_red)
            set1.fillDrawable = drawable
        } else {
            set1.fillColor = Color.BLACK
        }
        set1.highLightColor = Color.parseColor("#9661F9")
        set1.enableDashedHighlightLine(20f, 5f, 0f)
        set1.highlightLineWidth = 1f
        set1.setDrawHorizontalHighlightIndicator(false)

        set1.color = Color.parseColor("#9661F9")
        set1.setCircleColor(Color.parseColor("#9661F9"))
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)

        val data = LineData(dataSets)

        lineChart1.data = data

        lineChart1.setVisibleXRange(5f, 5f)

        //设置滚动到最后一个数据
        lineChart1.moveViewToX(lineChart1.xChartMax)
    }

    //注册联动
    private fun initChartListener() {
        //多图表联动
        chartList[0].onChartGestureListener = MyCoupleChartGestureListener(chartList[0], chartList[1],chartList[2],chartList[3])
        chartList[1].onChartGestureListener = MyCoupleChartGestureListener(chartList[1], chartList[0],chartList[2],chartList[3])
        chartList[2].onChartGestureListener = MyCoupleChartGestureListener(chartList[2], chartList[0],chartList[1],chartList[3])
        chartList[3].onChartGestureListener = MyCoupleChartGestureListener(chartList[3], chartList[0],chartList[1],chartList[2])

        //高亮联动
        chartList[0].setOnChartValueSelectedListener(CoupleChartValueSelectedListener(chartList[0], chartList[1],chartList[2],chartList[3]))


        chartList[1].setOnChartValueSelectedListener(CoupleChartValueSelectedListener(chartList[1], chartList[0],chartList[2],chartList[3]))


        chartList[2].setOnChartValueSelectedListener(CoupleChartValueSelectedListener(chartList[2], chartList[0],chartList[1],chartList[3]))


        chartList[3].setOnChartValueSelectedListener(CoupleChartValueSelectedListener(chartList[3], chartList[0],chartList[1],chartList[2]))
    }
}
