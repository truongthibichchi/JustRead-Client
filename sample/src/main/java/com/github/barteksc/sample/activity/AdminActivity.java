package com.github.barteksc.sample.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.utilities.NumberFormatUtility;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_admin)
public class AdminActivity extends AppCompatActivity {

    @ViewById(R.id.piechart)
    static PieChart pieChart;
    @ViewById(R.id.btn_change)
    Button changeType;
    static boolean isPercentValues = false;
    static PieData data;
    static PieDataSet dataSet;
    static ArrayList<Integer> colors = new ArrayList<>();
    static List<PieEntry> entries = new ArrayList<>();

    @Click(R.id.btn_change)
    public void changeTypeButton(){
        isPercentValues = !isPercentValues;
    }

    @AfterViews
    protected void init() {
        dummyData();
        setUpPieChart();
        setUpData();
    }

    private void setUpPieChart() {

        pieChart.setUsePercentValues(isPercentValues);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        pieChart.setData(data);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(20f);

    }

    private void setUpData() {
        dataSet = new PieDataSet(entries, "");
        dataSet.setValueFormatter(new MyFormatter());
        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);


        data = new PieData(dataSet);
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

        pieChart.highlightValues(null);

        pieChart.invalidate();

    }

    private void dummyData(){
        entries.clear();
        entries.add(new PieEntry(96, configCategory(0)));
        entries.add(new PieEntry(98, configCategory(1)));
        entries.add(new PieEntry(123, configCategory(2)));
        entries.add(new PieEntry(78, configCategory(3)));
        entries.add(new PieEntry(85, configCategory(4)));
        entries.add(new PieEntry(95, configCategory(5)));
    }

    private String configCategory(int x) {
        switch (x) {
            case 0:
                return "Kinh tế";
            case 1:
                return "Nuôi dạy con";
            case 2:
                return "Sách thiếu nhi";
            case 3:
                return "Tiểu sử - Hồi ký";
            case 4:
                return "Tâm lý - Kỹ năng sống";
            case 5:
                return "Văn học";
            default:
                return "user";
        }
    }
}

class MyFormatter extends ValueFormatter {
    @Override
    public String getFormattedValue(float value) {
        return "" + ((int) value);
    }
}