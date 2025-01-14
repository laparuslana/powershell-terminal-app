package com.example.terminal.Model;

import java.util.Date;


public class MonthFormat extends AbstractFormat {

    @Override
    public void execute(Format format) {
        String format1 = format.getFormat();
        Date date = format.getDate();
        int month = date.getMonth()+1;
        String tempFormat = format1.replaceAll("MM", Integer.toString(month));
        format.setFormat(tempFormat);
    }
}

