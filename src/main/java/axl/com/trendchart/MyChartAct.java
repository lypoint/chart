package axl.com.trendchart;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import axl.com.trendchart.widget.MyLineChart;


public class MyChartAct extends AppCompatActivity {
    //    MyLineChart lineChart1;
    List<MyLineChart> chartList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_chart);
        //        lineChart1 = ;
        chartList.add((MyLineChart) findViewById(R.id.lineChart1));
        chartList.add((MyLineChart) findViewById(R.id.lineChart2));
        chartList.add((MyLineChart) findViewById(R.id.lineChart3));
        chartList.add((MyLineChart) findViewById(R.id.lineChart4));
        for (MyLineChart chartAct : chartList) {
            initKline(chartAct);
            addTestData(chartAct);
        }

    }

    private void initKline(MyLineChart lineChart1) {
        lineChart1.setScaleEnabled(true);//启用图表缩放事件
        lineChart1.setDrawBorders(false);//是否绘制边线
        lineChart1.setDragEnabled(true);
        lineChart1.setScaleYEnabled(false);
        lineChart1.setMinOffset(0);
        lineChart1.setDescription(null);

        Legend legend = lineChart1.getLegend();
        legend.setEnabled(false);

        XAxis xAxis = lineChart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);

        lineChart1.getRendererXAxis();
        xAxis.setLabelCount(5);
        xAxis.setAvoidFirstLastClipping(true);

        xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
        xAxis.enableGridDashedLine(20, 5, 0f);
        xAxis.setGridColor(Color.parseColor("#D3CFE5"));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }
        });

        //设置padding
        if (lineChart1.getId() == R.id.lineChart1) {
            lineChart1.setExtraOffsets(5, 45, 94f, 7);
            lineChart1.isFirst = true;
        } else {
            lineChart1.setExtraOffsets(5, 0, 94f, 7);
            xAxis.setDrawLabels(false);
        }

        YAxis axisRight = lineChart1.getAxisRight();
        axisRight.setDrawGridLines(false);
        axisRight.setDrawAxisLine(false);
        axisRight.setDrawZeroLine(false);
        axisRight.setDrawLabels(false);
        axisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisRight.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        YAxis axisLeft = lineChart1.getAxisLeft();
        axisLeft.setDrawLabels(false);
        axisLeft.setDrawGridLines(false);
        axisLeft.setDrawAxisLine(false);
        axisLeft.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
    }

    private void addTestData(MyLineChart lineChart1) {
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i == 0) {
                values.add(new Entry(i, 14));
            } else if (i == 1) {
                values.add(new Entry(i, 21));
            } else if (i == 2) {
                values.add(new Entry(i, 67));
            } else if (i == 3) {
                values.add(new Entry(i, 54));
            } else if (i == 4) {
                values.add(new Entry(i, 89));
            } else if (i == 5) {
                values.add(new Entry(i, 22));
            } else if (i == 6) {
                values.add(new Entry(i, 29));
            } else if (i == 7) {
                values.add(new Entry(i, 93));
            } else if (i == 8) {
                values.add(new Entry(i, 3));
            } else if (i == 9) {
                values.add(new Entry(i, 0));
            } else if (i == 10) {
                values.add(new Entry(i, 87));
            } else if (i == 11) {
                values.add(new Entry(i, 57));
            } else if (i == 12) {
                values.add(new Entry(i, 23));
            } else if (i == 13) {
                values.add(new Entry(i, 22));
            } else if (i == 14) {
                values.add(new Entry(i, 80));
            } else if (i == 15) {
                values.add(new Entry(i, 56));
            } else if (i == 16) {
                values.add(new Entry(i, 40));
            } else if (i == 17) {
                values.add(new Entry(i, 100));
            } else if (i == 18) {
                values.add(new Entry(i, 8));
            } else if (i == 19) {
                values.add(new Entry(i, 3));
            } else if (i == 20) {
                values.add(new Entry(i, 10));
            } else if (i == 21) {
                values.add(new Entry(i, 40));
            } else if (i == 22) {
                values.add(new Entry(i, 50));
            } else if (i == 23) {
                values.add(new Entry(i, 30));
            } else if (i == 24) {
                values.add(new Entry(i, 22));
            } else if (i == 25) {
                values.add(new Entry(i, 9));
            } else if (i == 26) {
                values.add(new Entry(i, 5));
            } else if (i == 27) {
                values.add(new Entry(i, 8));
            } else if (i == 28) {
                values.add(new Entry(i, 5));
            } else if (i == 29) {
                values.add(new Entry(i, 0));
            }
        }

        LineDataSet set1 = new LineDataSet(values, "");

        //颜色填充的渐变
        set1.setDrawFilled(true);//必须加上这一句，否则没有填充渐变
        if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.BLACK);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        lineChart1.setData(data);

        lineChart1.setVisibleXRange(5, 5);

        //设置滚动到最后一个数据
        lineChart1.moveViewToX(lineChart1.getXChartMax());

    }


}
