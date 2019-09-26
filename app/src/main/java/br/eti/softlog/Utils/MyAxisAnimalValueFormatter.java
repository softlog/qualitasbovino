package br.eti.softlog.Utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class MyAxisAnimalValueFormatter implements IAxisValueFormatter
{

    //private final DecimalFormat mFormat;

    public MyAxisAnimalValueFormatter() {
        //mFormat = new DecimalFormat("###,###,###,##0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (value == 0)
            return "Macho";

        if (value == 1)
            return "Fêmea";
        else
            return "";
    }
}