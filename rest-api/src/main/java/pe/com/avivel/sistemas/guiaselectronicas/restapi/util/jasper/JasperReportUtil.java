package pe.com.avivel.sistemas.guiaselectronicas.restapi.util.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.com.avivel.sistemas.guiaselectronicas.service.exceptions.ReportException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("DuplicateBranchesInSwitch")
public class JasperReportUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JasperReportUtil.class);

    public static byte[] exportReport(JasperReportType type, Connection connection, InputStream inputStream, Map<String, Object> parameters){
        if (parameters == null) parameters = new HashMap<>();

        switch (type){
            case PDF:
                return exportReportToPdf(connection, inputStream, parameters);
            case XLSX:
                return exportReportToXlsx(connection, inputStream, parameters);
            default:
                return exportReportToPdf(connection, inputStream, parameters);
        }

    }

    public static byte[] exportReport(JasperReportType type, JRDataSource dataSource, InputStream inputStream, Map<String, Object> parameters){
        if (parameters == null) parameters = new HashMap<>();

        switch (type){
            case PDF:
                return exportReportToPdf(dataSource, inputStream, parameters);
            case XLSX:
                return exportReportToXlsx(dataSource, inputStream, parameters);
            default:
                return exportReportToPdf(dataSource, inputStream, parameters);
        }

    }

    public static String getExtensionFile(JasperReportType type){
        switch (type){
            case PDF:
                return ".pdf";
            case XLSX:
                return ".xlsx";
            default:
                return ".pdf";
        }

    }

    public static byte[] exportReportToPdf(Connection connection, InputStream inputStream, Map<String, Object> parameters) {
        try {

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
            JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();

            JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
            jrPropertiesUtil.setProperty("net.sf.jasperreports.query.executer.factory.plsql", "net.sf.jasperreports.engine.query.PlSqlQueryExecuterFactory");

            JasperPrint jasperPrint = null;

            if (connection != null && !connection.isClosed()) {
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            }

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            LOG.error("### Error en exportReportToPdf: {0}", e);
            throw new ReportException(e);
        }
    }

    public static byte[] exportReportToPdf(JRDataSource dataSource, InputStream inputStream, Map<String, Object> parameters) {
        try {

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
            JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();

            JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
            jrPropertiesUtil.setProperty("net.sf.jasperreports.query.executer.factory.plsql", "net.sf.jasperreports.engine.query.PlSqlQueryExecuterFactory");

            JasperPrint jasperPrint = null;

            if (dataSource != null) {
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            }

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            LOG.error("### Error en exportReportToPdf: {0}", e);
            throw new ReportException(e);
        }
    }

    public static byte[] exportReportToXlsx(Connection connection, InputStream inputStream, Map<String, Object> parameters) {
        try {

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
            JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();

            JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
            jrPropertiesUtil.setProperty("net.sf.jasperreports.query.executer.factory.plsql", "net.sf.jasperreports.engine.query.PlSqlQueryExecuterFactory");

            JasperPrint jasperPrint = null;

            SimpleXlsxReportConfiguration xlsxReportConfig = new SimpleXlsxReportConfiguration();
            xlsxReportConfig.setRemoveEmptySpaceBetweenColumns(true);
            xlsxReportConfig.setForcePageBreaks(false);
            xlsxReportConfig.setWrapText(false);
            xlsxReportConfig.setCollapseRowSpan(true);
            xlsxReportConfig.setDetectCellType(true);
            xlsxReportConfig.setIgnorePageMargins(true);

            if (connection != null && !connection.isClosed()) {
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            xlsxExporter.setConfiguration(xlsxReportConfig);
            xlsxExporter.exportReport();

            byte[] rawBytes = outputStream.toByteArray();

            outputStream.write(0);
            outputStream.flush();
            outputStream.close();
            xlsxExporter.reset();

            return rawBytes;
        } catch (Exception e) {
            LOG.error("### Error en exportReportToXls: {0}", e);
            throw new ReportException(e);
        }
    }

    public static byte[] exportReportToXlsx(JRDataSource dataSource, InputStream inputStream, Map<String, Object> parameters) {
        try {

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
            JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();

            JRPropertiesUtil jrPropertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
            jrPropertiesUtil.setProperty("net.sf.jasperreports.query.executer.factory.plsql", "net.sf.jasperreports.engine.query.PlSqlQueryExecuterFactory");

            JasperPrint jasperPrint = null;

            SimpleXlsxReportConfiguration xlsxReportConfig = new SimpleXlsxReportConfiguration();
            xlsxReportConfig.setRemoveEmptySpaceBetweenColumns(true);
            xlsxReportConfig.setForcePageBreaks(false);
            xlsxReportConfig.setWrapText(false);
            xlsxReportConfig.setCollapseRowSpan(true);
            xlsxReportConfig.setDetectCellType(true);
            xlsxReportConfig.setIgnorePageMargins(true);

            if (dataSource != null) {
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            xlsxExporter.setConfiguration(xlsxReportConfig);
            xlsxExporter.exportReport();

            byte[] rawBytes = outputStream.toByteArray();

            outputStream.write(0);
            outputStream.flush();
            outputStream.close();
            xlsxExporter.reset();

            return rawBytes;
        } catch (Exception e) {
            LOG.error("### Error en exportReportToXls: {0}", e);
            throw new ReportException(e);
        }
    }
}
