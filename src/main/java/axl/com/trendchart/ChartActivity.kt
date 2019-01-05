package axl.com.trendchart

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import com.github.mikephil.charting.utils.Utils

class ChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chart)
        Utils.init(this)

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        var params = recyclerView.layoutParams
        var listWidth = Contants.chartWidth + Contants.rightWidth
        if (params != null) {
            params.width = listWidth.toInt()
            recyclerView.layoutParams = params
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ChartAdapter()

        var scrollView = findViewById<HSView>(R.id.scrollview)
        scrollView.postDelayed({
            scrollView.fullScroll(View.FOCUS_RIGHT)
            var v2 = findViewById<HSView>(R.id.scrollview2)
            scrollView.setAnother(v2)
            v2.setAnother(scrollView)
        }, 100)


        var frameLayout = findViewById<FrameLayout>(R.id.layout)
        var i = 0
        while (i < Contants.columnCount) {
            var view = View(this)
            view.setBackgroundColor(resources.getColor(R.color.red))
            var viewWidth = Utils.convertDpToPixel(1f)
            var marginTop = Utils.convertDpToPixel(30f)
            var lp = FrameLayout.LayoutParams(viewWidth.toInt(), FrameLayout.LayoutParams.MATCH_PARENT)
            lp.topMargin = marginTop.toInt()
            var rightMargin = (Contants.rightWidth + Utils.convertDpToPixel(Contants.chartPaddingRightDp) - Contants.columnWidth / 2).toInt()
            var leftMargin = (Contants.rightWidth - rightMargin).toInt()
            leftMargin = (leftMargin + i * (Contants.chartWidth - viewWidth)).toInt()
            lp.leftMargin = leftMargin
            frameLayout.addView(view, lp)

            i++
        }
    }

}
