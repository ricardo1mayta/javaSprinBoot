package pe.com.avivel.sistemas.guiaselectronicas.commons.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("unused")
@Slf4j
public class Util {

    public static final String ROLE_PREFIX = "ROLE_";
    private final static String INFINITY_FUTURE_DATE = "31/12/2999";
    private final static String INFINITY_PAST_DATE = "01/01/1900";
    public final static String DEFAULT_DATE_PATTERN = "dd/MM/yyyy";
    public final static String DEFAULT_DATETIME_PATTERN = "dd/MM/yyyy HH:mm:ss.SSS";
    public final static String ISO_DATE_PATTERN = "yyyy-MM-dd";
    public final static String DEFAULT_DATETIME_WMS_PATTERN = "dd/MM/yyyy HH:mm:ss";
    public final static String FULL_DATE_PATTERN = "EEEE, dd MMMMM yyyy HH:mm";
    public final static SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
    public final static SimpleDateFormat DEFAULT_DATETIME_FORMAT = new SimpleDateFormat(DEFAULT_DATETIME_PATTERN);
    public final static SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat(ISO_DATE_PATTERN);
    public final static SimpleDateFormat DEFAULT_DATETIME_WMS_FORMAT = new SimpleDateFormat(DEFAULT_DATETIME_WMS_PATTERN);
    public final static SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat(FULL_DATE_PATTERN);

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static Date stringToDate(String dt, SimpleDateFormat formatter) {
        if (StringUtils.isEmpty(dt)) return null;

        try{
            return formatter.parse(dt);
        }catch (ParseException ex){
            log.error("unhandle ParseException : {0}",ex);
        }

        return null;
    }

    public static String dateToString(Date date, SimpleDateFormat formatter) {
        if (date == null) return null;
        return formatter.format(date);
    }

    public static Date dateSoloFecha(Date date){
        if (date == null) return null;
        String format = DEFAULT_DATE_FORMAT.format(date);
        try {
            return DEFAULT_DATE_FORMAT.parse(format);
        } catch (ParseException e) {
            //throw new RuntimeException(e);
            log.error("Error en obtener la fecha", e);
        }
        return null;
    }

    public static Date agregarMinutos(Date fecha, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }

    public static Date agregarDias(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DATE, dias);
        return calendar.getTime();
    }

    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }

    public static BigInteger getBigInteger(Object value) {
        BigInteger ret = null;
        if (value != null) {
            if (value instanceof BigInteger) {
                ret = (BigInteger) value;
            } else if (value instanceof String) {
                ret = new BigInteger((String) value);
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigInteger.");
            }
        }
        return ret;
    }

    public static String randomUUID() {
        return (UUID.randomUUID().toString().replaceAll("-", ""));
    }

    public static String leerFichero(String pathFile) {
        StringBuilder buffer = new StringBuilder();
        String line = StringUtils.EMPTY;
        FileReader fReader = null;
        BufferedReader bReader;
        try {
            fReader = new FileReader(pathFile);
            bReader = new BufferedReader(fReader);
            while ((line = bReader.readLine()) != null) {
                buffer.append(line);
            }
            bReader.close();
            fReader.close();
            return buffer.toString();
        } catch (IOException e) {
            log.error("### error en leerFichero: ", e);
        } finally {
            try {
                if (null != fReader) {
                    fReader.close();
                }
            } catch (Exception e2) {
                log.error("### error en leerFichero: ", e2);
                return null;
            }
        }

        return null;
    }

    public static String getMesNombre(Integer mes) {
        if(mes<1 || mes>12){
            return "";
        }
        return new DateFormatSymbols().getMonths()[mes - 1];
    }

    public static String toLineCsv(Object[] objects, String separator) {
        StringBuilder ret = new StringBuilder();
        for (Object o : objects) {
            if (o != null) {
                ret.append(o).append(separator);
            } else {
                ret.append(separator);
            }
        }
        ret.append("\r\n");
        return ret.toString();
    }
}
