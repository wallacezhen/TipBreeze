package com.example.walla.tipbreeze;

import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

/**
 * Created by walla on 9/18/2016.
 */
public class PieGraph {
    public GraphicalView getView(Context context, int avgTip, int totalTip,
                                 int avgPayTotal, int totalPayTotal){

        int[] values = {avgTip, totalTip, avgTip, totalPayTotal};

        CategorySeries series = new CategorySeries("Pie Graph");

        int k = 0;
        for (int value : values){
            series.add("Section " + ++k, value);
        }

        int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA,
                Color.YELLOW};

        DefaultRenderer renderer = new DefaultRenderer();

        for (int color: colors){
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }

        return ChartFactory.getPieChartView(context, series, renderer);
    }
}
