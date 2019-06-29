package com.github.barteksc.sample.activity;

import android.graphics.Color;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.barteksc.sample.R;
import com.github.barteksc.sample.api.APIService;
import com.github.barteksc.sample.api.ApiUtils;
import com.github.barteksc.sample.model.ReportReturnModel;
import com.github.barteksc.sample.utilities.NumberFormatUtility;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.github.barteksc.sample.activity.LogInActivity.apiService;

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
    public APIService apiService;
    @ViewById(R.id.tv_user_count)
    TextView userCount;
    @ViewById(R.id.loadingcircle)
    ContentLoadingProgressBar contentLoadingProgressBar;

    @Click(R.id.btn_change)
    public void changeTypeButton() {
        isPercentValues = !isPercentValues;
        setUpPieChart();
        setUpData();
    }

    @AfterViews
    protected void init() {
        contentLoadingProgressBar.show();
        contentLoadingProgressBar.setVisibility(View.VISIBLE);
        apiService = ApiUtils.getAPIService();
        getData();
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

    private void getData() {
        apiService.getReport().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    entries.clear();
                    entries.add(new PieEntry(response.body().get("Kinh tế").getAsInt(), "Kinh tế"));
                    entries.add(new PieEntry(response.body().get("Nuôi dạy con").getAsInt(), "Nuôi dạy con"));
                    entries.add(new PieEntry(response.body().get("Sách thiếu nhi").getAsInt(), "Sách thiếu nhi"));
                    entries.add(new PieEntry(response.body().get("Tâm lý - Kỹ năng sống").getAsInt(), "Tâm lý - Kỹ năng sống"));
                    entries.add(new PieEntry(response.body().get("Văn học").getAsInt(), "Văn học"));
                    userCount.setText(new StringBuilder().append("Người dùng ứng dụng: ").append(response.body().get("user").getAsInt()));
                }
                setUpData();
                setUpPieChart();
                contentLoadingProgressBar.hide();
                contentLoadingProgressBar.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}

class MyFormatter extends ValueFormatter {
    @Override
    public String getFormattedValue(float value) {
        return "" + ((int) value);
    }
}