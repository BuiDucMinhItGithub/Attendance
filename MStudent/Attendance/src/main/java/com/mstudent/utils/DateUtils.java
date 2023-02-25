package com.mstudent.utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateUtils {
    public String getMonthAndYear(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        String[] splits = strDate.split("/");
        return splits[1].concat("-"+splits[2]);
    }

    public LocalDate changeFormatDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );
        LocalDate ld = LocalDate.parse( strDate , f );
        return ld;
    }
}
