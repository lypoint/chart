package axl.com.trendchart.mychart.vo

import com.asiainnovations.ace.quotes.detail.kline.vo.KLineBean
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import java.util.*

/**
 * 数据仓库
 */
class DataParse {
    /**
     * 得到K线图数据
     *
     * @return
     */
    val kLineDatas = ArrayList<KLineBean>()
    /**
     * 得到X轴数据
     *
     * @return
     */
    var xVals = ArrayList<String>()
        private set//X轴数据
    /**
     * 得到成交量数据
     *
     * @return
     */
    var barEntries = ArrayList<BarEntry>()
        private set//成交量数据
    /**
     * 得到K线数据
     *
     * @return
     */
    var candleEntries = ArrayList<CandleEntry>()
        private set//K线数据

    /**
     * 得到K线图5日均线
     *
     * @return
     */
    var timeSharingWireDataL = ArrayList<Entry>()
        private set

    /**
     * 得到K线图5日均线
     *
     * @return
     */
    var ma7DataL = ArrayList<Entry>()
        private set
    /**
     * 得到K线图10日均线
     *
     * @return
     */
    var ma25DataL = ArrayList<Entry>()
        private set

    /**
     * 得到成交量5日均线
     *
     * @return
     */
    var ma5DataV = ArrayList<Entry>()
        private set
    /**
     * 得到成交量10日均线
     *
     * @return
     */
    var ma10DataV = ArrayList<Entry>()
        private set

    var macdData: MutableList<BarEntry> = ArrayList()
    var deaData: MutableList<Entry> = ArrayList()
    var difData: MutableList<Entry> = ArrayList()

    var barDatasKDJ: MutableList<BarEntry> = ArrayList()
    var kData: MutableList<Entry> = ArrayList()
    var dData: MutableList<Entry> = ArrayList()
    var jData: MutableList<Entry> = ArrayList()

    var barDatasRSI: MutableList<BarEntry> = ArrayList()
    var rsiData6: MutableList<Entry> = ArrayList()
    var rsiData12: MutableList<Entry> = ArrayList()
    var rsiData24: MutableList<Entry> = ArrayList()

    var barDatasBOLL: MutableList<BarEntry> = ArrayList()
    var bollDataUP: MutableList<Entry> = ArrayList()
    var bollDataMB: MutableList<Entry> = ArrayList()
    var bollDataDN: MutableList<Entry> = ArrayList()

    private var barDatasEXPMA: MutableList<BarEntry> = ArrayList()

    var expmaData7 = ArrayList<Entry>()
    var expmaData25 = ArrayList<Entry>()

    private var baseValue: Float = 0.toFloat()
    private var permaxmin: Float = 0.toFloat()

    /**
     * 得到Y轴最小值
     */
    val min: Float
        get() = baseValue - permaxmin

    /**
     * 得到Y轴最大值
     */
    val max: Float
        get() = baseValue + permaxmin

    /**
     * 得到百分百最大值
     */
    val percentMax: Float
        get() = permaxmin / baseValue

    /**
     * 得到百分比最小值
     */
    val percentMin: Float
        get() = -percentMax


    //得到成交量
    fun initLineDatas(datas: ArrayList<KLineBean>?) {
        if (null == datas) {
            return
        }
        kLineDatas.clear()
        kLineDatas.addAll(datas)
        xVals = ArrayList()//X轴数据
        barEntries = ArrayList()//成交量数据
        candleEntries = ArrayList()//K线数据
        var i = 0
        var j = 0
        while (i < datas.size) {
            xVals.add(datas[i].date + "")
            barEntries.add(BarEntry(i.toFloat(), datas[i].vol, datas[i].date))
            candleEntries.add(CandleEntry(i.toFloat(), datas[i].high, datas[i].low, datas[i].open, datas[i].close,datas[i].date))
            i++
            j++
        }
    }

    fun initTimeSharingWireData(datas: ArrayList<KLineBean>?) {
        if (null == datas) {
            return
        }

        timeSharingWireDataL = arrayListOf()

        datas.mapIndexed { index, kLineBean ->
            timeSharingWireDataL.add(Entry(index.toFloat(), kLineBean.close))
        }
    }




}
