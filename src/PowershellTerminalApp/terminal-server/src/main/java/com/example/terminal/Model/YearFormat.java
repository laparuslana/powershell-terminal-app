package com.example.terminal.Model;

import java.util.Date;


public class YearFormat extends AbstractFormat {

    @Override
    public void execute(Format format) {
        String format1 = format.getFormat();
        Date date = format.getDate();
        int year = date.getYear() + 1900;
        String tempFormat = format1.replaceAll("YYYY", Integer.toString(year));
        format.setFormat(tempFormat);
    }
}

