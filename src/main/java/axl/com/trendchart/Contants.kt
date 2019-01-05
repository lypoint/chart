package axl.com.trendchart

import com.github.mikephil.charting.utils.Utils

object Contants {
    /**
     * 曲线图数量
     */
    val chartNumber = 15
    /**
     * 曲线图x轴单位间距
     */
    var columnWidth = Utils.convertDpToPixel(50.toFloat())

    /**
     * 曲线图x轴单位总数
     */
    var columnCount = 30

    /**
     * 右侧数目layout宽度
     */
    var rightWidth = Utils.convertDpToPixel(100.toFloat())

    /**
     * 曲线图paddingLeft
     */
    var chartPaddingLeftDp = 15f
    /**
     * 曲线图paddingRight
     */
    var chartPaddingRightDp = 15f

    /**
     * 曲线图paddingRight
     */
    var chartWidth = columnWidth * columnCount + Utils.convertDpToPixel(chartPaddingLeftDp) + Utils.convertDpToPixel(chartPaddingRightDp)
}