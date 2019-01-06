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
import axl.com.trendchart.widget.MyXAxisRenderer

class MyChartAct : AppCompatActivity() {
    var chartList: MutableList<MyLineChart> = arrayListOf()
    var visiableCount = 4
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_chart)
        chartList.add(findViewById<View>(R.id.floatChart1) as MyLineChart)
//        chartList.add(findViewById<View>(R.id.lineChart1) as MyLineChart)
//        chartList.add(findViewById<View>(R.id.lineChart2) as MyLineChart)
//        chartList.add(findViewById<View>(R.id.lineChart3) as MyLineChart)
//        chartList.add(findViewById<View>(R.id.lineChart4) as MyLineChart)
//        chartList.add(findViewById<View>(R.id.lineChart5) as MyLineChart)
//        chartList.add(findViewById<View>(R.id.lineChart6) as MyLineChart)
//        chartList.add(findViewById<View>(R.id.lineChart7) as MyLineChart)
//        chartList.add(findViewById<View>(R.id.lineChart8) as MyLineChart)

        for (chartAct in chartList) {
            initKline(chartAct)
            addTestData(chartAct)
        }
        initChartListener()
    }

    var xAxisData = HashMap<String, XAXisModel>()
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
        xAxis.labelCount = visiableCount
        //        xAxis.setAvoidFirstLastClipping(true)
        xAxis.setDrawAxisLine(false)

        xAxis.setDrawGridLines(true) //设置x轴上每个点对应的线
        xAxis.gridLineWidth = 1f
        xAxis.gridColor = Color.parseColor("#D3CFE5")

        xAxis.enableGridDashedLine(20f, 5f, 0f)
        xAxis.gridColor = Color.parseColor("#D3CFE5")
        addXAXisTestData()
        xAxis.valueFormatter = IAxisValueFormatter { value, axis ->
            when (value) {
                29f -> {
                    "昨日"
                }
                28f -> {

                    "3日"
                }
                27f -> {

                    "2日"
                }
                26f -> {
                    "12月1日"
                }
                25f -> {
                    "30日"
                }
                24f -> {
                    "29日"
                }
                23f -> {
                    "28日"
                }
                22f -> {
                    "27日"
                }
                21f -> {
                    "26日"
                }
                20f -> {
                    "25日"
                }

                19f -> {
                    "24日"
                }
                18f -> {
                    "23日"
                }
                17f -> {
                    "22日"
                }
                16f -> {
                    "21日"
                }
                15f -> {
                    "20日"
                }
                14f -> {
                    "19日"
                }
                13f -> {

                    "18日"
                }
                12f -> {
                    "17日"
                }
                11f -> {
                    "16日"
                }
                10f -> {

                    "15日"
                }
                9f -> {
                    "14日"
                }
                8f -> {
                    "13日"
                }
                7f -> {

                    "12日"
                }
                6f -> {
                    "11日"
                }
                5f -> {
                    "10日"
                }
                4f -> {
                    "9日"
                }
                3f -> {
                    "8日"
                }
                2f -> {
                    "7日"
                }
                1f -> {

                    "6日"
                }
                0f -> {
                    "5日"
                }
                else -> ""
            }
        }
        //设置padding
        if (lineChart1.id == R.id.lineChart1) {
            lineChart1.isFirst = true
            lineChart1.setViewPortOffsets(Utils.convertDpToPixel(5f), Utils.convertDpToPixel(45f), Utils.convertDpToPixel(94f), Utils.convertDpToPixel(0f))
            var myXAxisRenderer = MyXAxisRenderer(xAxisData, lineChart1.viewPortHandler, xAxis, lineChart1.getTransformer(YAxis.AxisDependency.LEFT))
            lineChart1.setXAxisRenderer(myXAxisRenderer)
        } else {
            lineChart1.setViewPortOffsets(Utils.convertDpToPixel(5f), Utils.convertDpToPixel(0f), Utils.convertDpToPixel(94f), Utils.convertDpToPixel(0f))
        }

        val axisRight = lineChart1.axisRight
        axisRight.setDrawGridLines(false)
        axisRight.setDrawAxisLine(false)
        axisRight.setDrawZeroLine(false)
        axisRight.setDrawLabels(false)
        axisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        axisRight.setLabelCount(4, false) //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        val axisLeft = lineChart1.axisLeft
        axisLeft.setDrawLabels(false)
        axisLeft.setDrawGridLines(false)
        axisLeft.setDrawZeroLine(false)
        axisLeft.setDrawAxisLine(false)
        axisLeft.setLabelCount(4, false) //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
    }

    private fun addXAXisTestData() {
        xAxisData["昨日"] = XAXisModel("", "昨日", false)
        xAxisData["3日"] = XAXisModel("", "3日", false)
        xAxisData["2日"] = XAXisModel("", "2日", false)
        xAxisData["12月1日"] = XAXisModel("(12月)", "1日", true)
        xAxisData["30日"] = XAXisModel("", "30日", false)
        xAxisData["29日"] = XAXisModel("", "29日", false)
        xAxisData["28日"] = XAXisModel("", "28日", true)
        xAxisData["27日"] = XAXisModel("", "27日", false)
        xAxisData["26日"] = XAXisModel("", "26日", false)
        xAxisData["25日"] = XAXisModel("", "25日", false)
        xAxisData["24日"] = XAXisModel("", "24日", false)
        xAxisData["23日"] = XAXisModel("", "23日", false)
        xAxisData["22日"] = XAXisModel("", "22日", false)
        xAxisData["21日"] = XAXisModel("", "21日", false)
        xAxisData["20日"] = XAXisModel("", "20日", false)
        xAxisData["19日"] = XAXisModel("", "19日", false)
        xAxisData["18日"] = XAXisModel("", "18日", false)
        xAxisData["17日"] = XAXisModel("", "17日", false)
        xAxisData["16日"] = XAXisModel("", "16日", false)
        xAxisData["15日"] = XAXisModel("", "15日", false)
        xAxisData["14日"] = XAXisModel("", "14日", false)
        xAxisData["13日"] = XAXisModel("", "13日", false)
        xAxisData["12日"] = XAXisModel("", "12日", false)
        xAxisData["11日"] = XAXisModel("", "11日", false)
        xAxisData["10日"] = XAXisModel("", "10日", false)
        xAxisData["9日"] = XAXisModel("", "9日", false)
        xAxisData["8日"] = XAXisModel("", "8日", false)
        xAxisData["7日"] = XAXisModel("", "7日", false)
        xAxisData["6日"] = XAXisModel("", "6日", false)
        xAxisData["5日"] = XAXisModel("", "5日", false)
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
        set1.mode = LineDataSet.Mode.HORIZONTAL_BEZIER

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)

        val data = LineData(dataSets)

        lineChart1.data = data

        lineChart1.setVisibleXRange(visiableCount.toFloat(), visiableCount.toFloat())

        //设置滚动到最后一个数据
        lineChart1.moveViewToX(lineChart1.xChartMax)
    }

    //注册联动
    private fun initChartListener() {
        for (src in chartList) {
            var array = arrayListOf<Chart<*>>()
            for (item in chartList) {
                if (src != item) {
                    array.add(item)
                }
            }
            //多图表联动
            src.onChartGestureListener = MyCoupleChartGestureListener(src, array)
            //高亮联动
            src.setOnChartValueSelectedListener(CoupleChartValueSelectedListener(valueSelectedListener, src, array))
        }
    }

    //高亮选中的回调
    val valueSelectedListener = object : CoupleChartValueSelectedListener.ValueSelectedListener {
        override fun nothingSelected() {
        }

        override fun valueSelected(e: Entry?) {
        }

    }
}
