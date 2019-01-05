package axl.com.trendchart

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ChartAdapter : RecyclerView.Adapter<ChartHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, type: Int): ChartHolder {
        return if (type == 0) {
            ChartHeaderHolder(LayoutInflater.from(p0.context).inflate(R.layout.chart_header, p0, false))
        } else {
            ChartHolder(LayoutInflater.from(p0.context).inflate(R.layout.chart_item, p0, false))
        }
    }

    override fun getItemCount(): Int {
        return Contants.chartNumber
    }

    override fun onBindViewHolder(holder: ChartHolder, position: Int) {
        holder.loadData()
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> 0
            else -> 1
        }
    }
}