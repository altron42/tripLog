package br.edu.ufam.icomp.triplog.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by micael on 28/07/15.
 */
public class DateHandler {
    private static Locale locale = Locale.getDefault();

    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
    public static final DateFormat dateFormatFull = DateFormat.getDateInstance(DateFormat.FULL, locale);
    public static final DateFormat dateFormatDefault = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
    public static final DateFormat dateFormatShort = DateFormat.getDateInstance(DateFormat.SHORT, locale);
    public static final DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
}
