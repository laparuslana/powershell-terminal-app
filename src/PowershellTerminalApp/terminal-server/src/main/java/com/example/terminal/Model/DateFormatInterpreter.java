package com.example.terminal.Model;

import java.util.ArrayList;
import java.util.Date;


public class DateFormatInterpreter {

    public void interpret(String commandText, Format format) {
        format.setFormat(commandText);
        format.setDate(new Date());

        ArrayList<AbstractFormat> formatOrderList = getFormatOrder(format);

        for (AbstractFormat abstractFormat : formatOrderList) {
            abstractFormat.execute(format);
        }
    }

    private ArrayList<AbstractFormat> getFormatOrder(Format format) {
        ArrayList<AbstractFormat> formatOrderList = new ArrayList<>();
        String[] strArray = format.getFormat().split("-");
        for (String string : strArray) {
            if (string.equalsIgnoreCase("MM")) {
                formatOrderList.add(new MonthFormat());
            } else if (string.equalsIgnoreCase("DD")) {
                formatOrderList.add(new DayFormat());
            } else {
                formatOrderList.add(new YearFormat());
            }
        }
        return formatOrderList;
    }
}
