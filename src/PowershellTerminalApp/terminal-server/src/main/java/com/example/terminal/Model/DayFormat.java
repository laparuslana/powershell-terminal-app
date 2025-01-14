package com.example.terminal.Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DayFormat extends AbstractFormat {

    @Override
    public void execute(Format format) {
        String formatPattern = format.getFormat();
        LocalDate date = format.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
        String formattedDay = dayFormatter.format(date);

        String updatedFormat = formatPattern.replace("DD", formattedDay);

        format.setFormat(updatedFormat);
    }
}

