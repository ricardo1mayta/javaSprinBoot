package pe.com.avivel.sistemas.guiaselectronicas.commons.fechas;

import pe.com.avivel.sistemas.guiaselectronicas.commons.exception.FechaException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Fecha {

    private static final Calendar CALENDAR = Calendar.getInstance(new Locale("es", "US"));

    public static String toString(Date date) {
        if (date != null) {
            return FECHA.format(date);
        } else {
            return "";
        }
    }

    public static String toStringSimple(Date date) {
        if (date != null) {
            return FECHA_CORTA.format(date);
        } else {
            return "";
        }
    }

    /**
     * Devuelve un cadena con la fecha formateada de la forma dd/MM/yyyy
     * HH:mm:ss
     *
     * @param date La fecha que se desea formatear
     * @return Fecha formateada
     */
    public static String toStringFechaHora(Date date) {
        if (date != null) {
            return FECHA_HORA.format(date);
        } else {
            return "";
        }
    }

    public static String toStringHora(Date date) {
        if (date != null) {
            return HORA.format(date);
        } else {
            return "";
        }
    }

    /**
     * Devuelve un cadena con la fecha formateada de la forma dd/MM/yy HH:mm
     *
     * @param date La fecha que se desea formatear
     * @return Fecha formateada
     */
    public static String toStringFechaHoraCorta(Date date) {
        if (date != null) {
            return FECHA_HORA_CORTA.format(date);
        } else {
            return "";
        }
    }

    /**
     * Devuelve un cadena con la fecha formateada de la forma yyyyMMddHHmmss
     *
     * @param date La fecha que se desea formatear
     * @return Fecha formateada
     */
    public static String toStringFechaHora2(Date date) {
        if (date != null) {
            return FECHA_HORA_2.format(date);
        } else {
            return "";
        }
    }

    /**
     * Devuelve un cadena con la fecha formateada de la forma yyMMdd
     *
     * @param date La fecha que se desea formatear
     * @return Fecha formateada
     */
    public static String toStringConcar(Date date) {
        if (date != null) {
            return CONCAR_CORTA.format(date);
        } else {
            return "";
        }
    }

    /**
     * Devuelve un cadena con la fecha formateada de la forma yyyyMMdd
     *
     * @param date La fecha que se desea formatear
     * @return Fecha formateada
     */
    public static String toStringConcarLarga(Date date) {
        if (date != null) {
            return CONCAR_LARGA.format(date);
        } else {
            return "";
        }
    }

    public static String toStringDia(Date date) {
        if (date != null) {
            return DIA.format(date);
        } else {
            return "";
        }
    }

    /**
     * Devuelve la fecha en formato dd/MM
     *
     * @param date
     * @return
     */
    public static String toStringDiaMes(Date date) {
        if (date != null) {
            return DIA_MES.format(date);
        } else {
            return "";
        }
    }

    public static String obtenerMes(Date fecha) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(fecha);

        int mes = calendar.get(GregorianCalendar.MONTH) + 1;
        return mes < 10 ? "0" + mes : "" + mes;
    }

    public static String obtenerAnio(Date fecha) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(fecha);

        int anio = calendar.get(GregorianCalendar.YEAR);
        return "" + anio;
    }

    public static Date toDate(String fecha) throws FechaException {
        try {
            FECHA.setLenient(false);
            return FECHA.parse(fecha);
        } catch (ParseException e) {
            throw new FechaException("La expresión '" + fecha + "' no es una fecha válida.");
        }
    }


    public static Date toDateSimple(String fecha) throws FechaException {
        try {
            FECHA_CORTA.setLenient(false);
            return FECHA_CORTA.parse(fecha);
        } catch (ParseException e) {
            throw new FechaException("La expresión " + fecha + " no es una fecha válida.");
        }
    }

    /**
     *
     * @param sustraendo
     * @param minuendo
     * @return
     */
    public static int restarFechas(Date sustraendo, Date minuendo) {
        GregorianCalendar date1 = new GregorianCalendar();
        date1.setTime(sustraendo);
        GregorianCalendar date2 = new GregorianCalendar();
        date2.setTime(minuendo);
        if (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)) {
            return date2.get(Calendar.DAY_OF_YEAR) - date1.get(Calendar.DAY_OF_YEAR);
        } else {
            /* SI ESTAMOS EN DISTINTO ANYO COMPROBAMOS QUE EL ANYO DEL DATEINI
             * NO SEA BISIESTO
             * SI ES BISIESTO SON 366 DIAS EL ANYO
             * SINO SON 365
             */
            int diasAnyo = date1.isLeapYear(date1.get(Calendar.YEAR)) ? 366 : 365;

            /* CALCULAMOS EL RANGO DE ANYOS */
            int rangoAnyos = date2.get(Calendar.YEAR) - date1.get(Calendar.YEAR);

            /* CALCULAMOS EL RANGO DE DIAS QUE HAY */
            int rango = (rangoAnyos * diasAnyo)
                    + (date2.get(Calendar.DAY_OF_YEAR) - date1.get(Calendar.DAY_OF_YEAR));

            return rango;
        }
    }

    public static java.sql.Date fechaSQL(Date fecha) {
        if (fecha == null) {
            return null;
        } else {
            return new java.sql.Date(fecha.getTime());
        }
    }

    public static String getMesLetras(String periodo) {
        int mes = Integer.parseInt(periodo);
        switch (mes) {
            case 1:
                return "ENERO";
            case 2:
                return "FEBRERO";
            case 3:
                return "MARZO";
            case 4:
                return "ABRIL";
            case 5:
                return "MAYO";
            case 6:
                return "JUNIO";
            case 7:
                return "JULIO";
            case 8:
                return "AGOSTO";
            case 9:
                return "SEPTIEMBRE";
            case 10:
                return "OCTUBRE";
            case 11:
                return "NOVIEMBRE";
            default:
                return "DICIEMBRE";
        }
    }

    /**
     * Método que devuelve el código del mes
     *
     * @param nombreMes Nombre del mes
     * @return Código del mes
     */
    public static String getCodigoMes(String nombreMes) {
        switch (nombreMes) {
            case "ENERO":
                return "01";
            case "FEBRERO":
                return "02";
            case "MARZO":
                return "03";
            case "ABRIL":
                return "04";
            case "MAYO":
                return "05";
            case "JUNIO":
                return "06";
            case "JULIO":
                return "07";
            case "AGOSTO":
                return "08";
            case "SEPTIEMBRE":
                return "09";
            case "OCTUBRE":
                return "10";
            case "NOVIEMBRE":
                return "11";
            default:
                return "12";
        }
    }

    public static Date calcularFecha(Date fecha, int dias) {
        GregorianCalendar fechaHoy = new GregorianCalendar();
        fechaHoy.setTime(fecha);
        fechaHoy.add(GregorianCalendar.DATE, dias);
        return fechaHoy.getTime();
    }

    /**
     * Calcula la fecha luego de añadirle (restarle) los dias/meses/años
     * deseados
     *
     * @param fechaActual Fecha actual
     * @param numeroDias Número de días que se desean agregar(+)/restar(-)
     * @param numeroMeses Número de meses que se desean agregar(+)/restar(-)
     * @param numeroAnios Número de años que se desean agregar(+)/restar(-)
     * @return fecha La fecha calculada
     */
    public static Date calcularFecha(Date fechaActual, int numeroDias, int numeroMeses, int numeroAnios) {
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.setTime(fechaActual);
        fecha.add(GregorianCalendar.DATE, numeroDias);
        fecha.add(GregorianCalendar.MONTH, numeroMeses);
        fecha.add(GregorianCalendar.YEAR, numeroAnios);
        return fecha.getTime();
    }

    /**
     * Retorna la fecha y hora actuales
     *
     * @return La fecha actual
     */
    public static Date getFechaActual() {
        return new Date();
    }

    public static Integer getNumeroSemanaActual() {
        CALENDAR.clear();
        CALENDAR.setTime(new Date());
        return CALENDAR.get(Calendar.WEEK_OF_YEAR);
    }

    public static Integer getNumeroSemanaActualISO() {
        CALENDAR.clear();
        CALENDAR.setTime(new Date());
        CALENDAR.setMinimalDaysInFirstWeek(4); // For ISO 8601
        CALENDAR.setFirstDayOfWeek(Calendar.MONDAY);
        return CALENDAR.get(Calendar.WEEK_OF_YEAR);
    }

    public static Integer getNumeroSemana(Date fecha) {
        CALENDAR.clear();
        CALENDAR.setTime(fecha);
        return CALENDAR.get(Calendar.WEEK_OF_YEAR);
    }

    public static Integer getNumeroSemanaISO(Date fecha) {
        CALENDAR.clear();
        CALENDAR.setTime(fecha);
        CALENDAR.setMinimalDaysInFirstWeek(4); // For ISO 8601
        CALENDAR.setFirstDayOfWeek(Calendar.MONDAY);
        return CALENDAR.get(Calendar.WEEK_OF_YEAR);
    }

    public static Integer getAnioActual() {
        CALENDAR.clear();
        CALENDAR.setTime(new Date());
        return CALENDAR.get(Calendar.YEAR);
    }

    public static Date[] getDatesByYearAndWeeknumber(Integer year, Integer week) {
        CALENDAR.clear();
        CALENDAR.set(Calendar.YEAR, year);
        CALENDAR.set(Calendar.WEEK_OF_YEAR, week);
        Date[] fechas = new Date[2];
        fechas[0] = CALENDAR.getTime();
        CALENDAR.add(Calendar.DAY_OF_WEEK, 6);
        fechas[1] = CALENDAR.getTime();
        return fechas;
    }

    public static Integer[] getSemanasByYear(Integer year) {
        CALENDAR.set(Calendar.YEAR, year);
        Integer ultimaSemana = CALENDAR.getActualMaximum(Calendar.WEEK_OF_YEAR);
        Integer[] semanas;
        semanas = new Integer[ultimaSemana];
        int j = 0;
        for (int i = ultimaSemana; i >= 1; i--) {
            semanas[j] = i;
            j++;
        }
        return semanas;
    }

    public static String toStringMySQL(Date date) {
        if (date != null) {
            return FECHA_MYSQL.format(date);
        } else {
            return null;
        }
    }

    public static String toStringTimeFromMySQL(Date date){
        if(date!=null){
            return HORA.format(date);
        }else {
            return "00:00:00";
        }
    }

    public static Date getFirstDayOfMonth() {
        return getFirstDayOfMonth(Fecha.getFechaActual());
    }

    public static Date getFirstDayOfMonth(Date date) {
        if (date != null) {
            CALENDAR.clear();
            CALENDAR.setTime(date);
            CALENDAR.set(Calendar.DAY_OF_MONTH, CALENDAR.getActualMinimum(Calendar.DAY_OF_MONTH));
            return CALENDAR.getTime();
        } else {
            return null;
        }
    }

    public static Date getLastDayOfMonth() {
        return getLastDayOfMonth(Fecha.getFechaActual());
    }

    public static Date getLastDayOfMonth(Date date) {
        if (date != null) {
            CALENDAR.clear();
            CALENDAR.setTime(date);
            CALENDAR.set(Calendar.DAY_OF_MONTH, CALENDAR.getActualMaximum(Calendar.DAY_OF_MONTH));
            return CALENDAR.getTime();
        } else {
            return null;
        }
    }

    private static final SimpleDateFormat CONCAR_CORTA = new SimpleDateFormat("yyMMdd");
    private static final SimpleDateFormat CONCAR_LARGA = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat DIA = new SimpleDateFormat("dd");
    private static final SimpleDateFormat DIA_MES = new SimpleDateFormat("dd/MM");
    private static final SimpleDateFormat FECHA = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat FECHA_CORTA = new SimpleDateFormat("dd/MM/yy");
    private static final SimpleDateFormat FECHA_HORA = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final SimpleDateFormat FECHA_HORA_CORTA = new SimpleDateFormat("dd/MM/yy HH:mm");
    private static final SimpleDateFormat HORA = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
    private static final SimpleDateFormat FECHA_HORA_2 = new SimpleDateFormat("yyyy-MM-dd HH;mm;ss");
    private static final SimpleDateFormat FECHA_MYSQL = new SimpleDateFormat("yyyy-MM-dd");
}
