package io.renren.modules.walk.utils;

import io.renren.common.annotation.ExcelColumn;
import io.renren.modules.walk.entity.ExcelData;
import io.renren.common.annotation.ExcelSheet;
import io.renren.common.annotation.ExcelTemplate;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@UtilityClass
public class ExcelUtils {

    /**
     * xls 后缀
     */
    private final String XLS = ".xls";
    /**
     * xlsx 后缀
     */
    private final String XLS_X = ".xlsx";

    /**
     * sheet页的第一行
     */
    private final int FIRST_ROW = 0;

    /**
     * 第一个工作簿
     */
    private final int FIRST_SHEET = 0;

    /**
     * sheet页的第一列
     */
    private final int FIRST_COL = 0;

    /**
     * 科学计数
     */
    private final static String E = "e";

    private final String TIMEF_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final String DATE_FORMAT = "yyyy-MM-dd";

    public <T> List<T> importExcel(MultipartFile file, Class<T> clazz) {
        //检查文件是否存在
        checkFile(file);
        //根据传入的文件类型创建工作簿
        Workbook workbook = getWorkBook(file);
        List<T> list = new ArrayList<T>();
        //判断当前类属性是否标记excel注解
        Field[] fields = getFields(clazz);
        if (Objects.nonNull(workbook)) {
            Sheet sheet = getSheet(workbook, clazz);
            if (sheet == null || sheet.getLastRowNum() == 0) {
                return list;
            }
            // 获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            // 获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                // 获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                Object obj;
                try {
                    obj = clazz.newInstance();
                } catch (IllegalAccessException e) {
                    log.error("【excel导入】clazz映射地址：{},{}", clazz.getCanonicalName(), "excel导入异常！");
                    throw new RuntimeException("excel导入异常", e);
                } catch (InstantiationException e) {
                    log.error("【excel导入】clazz映射地址：{},{}", clazz.getCanonicalName(), "excel导入异常！");
                    throw new RuntimeException("excel导入异常", e);
                }
                boolean setValue = false;
                for (Field field : fields) {
                    ExcelColumn excelColumn = field.getDeclaredAnnotation(ExcelColumn.class);
                    if (Objects.isNull(excelColumn)) {
                        return null;
                    }
                    Cell cell = row.getCell(excelColumn.index());
                    if (excelColumn.rowIndex() >= rowNum) {
                        break;
                    }
                    if (!setValue) {
                        setValue = true;
                    }
                    Object value = getCellValue(cell, field);
                    createBean(field, obj, value);
                }
                if (setValue) {
                    list.add((T) obj);
                }
            }
        }
        return list;
    }

    private <T> void createBean(Field field, T newInstance, Object value) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            if (value == null) {
                field.set(newInstance, null);
            } else if (Long.class.equals(field.getType())) {
                field.set(newInstance, Long.valueOf(String.valueOf(value)));
            } else if (String.class.equals(field.getType())) {
                field.set(newInstance, String.valueOf(value));
            } else if (Integer.class.equals(field.getType())) {
                field.set(newInstance, Integer.valueOf(String.valueOf(value)));
            } else if (int.class.equals(field.getType())) {
                field.set(newInstance, Integer.parseInt(String.valueOf(value)));
            } else if (Date.class.equals(field.getType())) {
                field.set(newInstance, (Date) value);
            } else if (Boolean.class.equals(field.getType())) {
                field.set(newInstance, (Boolean) value);
            } else if (Double.class.equals(field.getType())) {
                field.set(newInstance, Double.valueOf(String.valueOf(value)));
            } else if (LocalDate.class.equals(field.getType())) {
                field.set(newInstance, ((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else if (LocalDateTime.class.equals(field.getType())) {
                field.set(newInstance, ((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            } else {
                field.set(newInstance, value);
            }
        } catch (IllegalAccessException e) {
            log.error("【excel导入】clazz映射地址：{},{},{}", newInstance, "excel实体赋值类型转换异常！", e);
            throw new RuntimeException("excel实体赋值类型转换异常", e);
        }
    }

    private Object getCellValue(Cell cell, Field field) {
        Object cellValue = null;
        if (cell == null) {
            return cellValue;
        }
        // 把数字当成String来读，避免出现1读成1.0的情况
        // 判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC:
                if (cell.getCellType() == CellType.NUMERIC) {
                    if (DateUtil.isValidExcelDate(cell.getNumericCellValue())) {
                        CellStyle style = cell.getCellStyle();
                        if (style == null) {
                            return false;
                        }
                        int i = style.getDataFormat();
                        String f = style.getDataFormatString();
                        boolean isDate = DateUtil.isADateFormat(i, f);
                        if (isDate) {
                            Date date = cell.getDateCellValue();
                            return cellValue = date;
                        }
                    }
                }
                // 防止科学计数进入
                if (String.valueOf(cell.getNumericCellValue()).toLowerCase().contains(E)) {
                    throw new RuntimeException("excel数据类型错误，请将数字转文本类型！！");
                }
                if ((int) cell.getNumericCellValue() != cell.getNumericCellValue()) {
                    // double 类型
                    cellValue = cell.getNumericCellValue();
                } else {
                    cellValue = (int) cell.getNumericCellValue();
                }
                break;
            // 字符串
            case STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            // Boolean
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            // 公式
            case FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            // 空值
            case BLANK:
                cellValue = null;
                break;
            // 故障
            case ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    private <T> Sheet getSheet(Workbook workbook, Class<T> clazz) {
        Sheet sheet = null;
        if (clazz.isAnnotationPresent(ExcelSheet.class)) {
            ExcelSheet excelSheet = clazz.getDeclaredAnnotation(ExcelSheet.class);
            //获取第几个表
            sheet = workbook.getSheetAt(excelSheet.index());
        } else {
            sheet = workbook.getSheetAt(FIRST_SHEET);
        }
        return sheet;
    }

    private <T> Field[] getFields(Class<T> clazz) {
        //获取对象总数量
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            log.error("【excel导入】clazz映射地址：{},{}", clazz.getCanonicalName(), "实体空异常！");
            throw new RuntimeException("excel导入】clazz映射地址：" + clazz.getCanonicalName() + ",实体空异常！");
        }
        for (Field field : fields) {
            if (!field.isAnnotationPresent(ExcelColumn.class)) {
                log.error("【excel导入】clazz映射地址：{},{}", clazz.getCanonicalName(), "实体空Excel注解异常！");
                throw new RuntimeException("【excel导入】clazz映射地址：" + clazz.getCanonicalName() + ", 实体空Excel注解异常！");
            }
        }
        return fields;
    }

    private Workbook getWorkBook(MultipartFile file) {
        // 获得文件名
        String fileName = file.getOriginalFilename();
        // 创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        // 获取excel文件的io流
        InputStream is;
        try {
            is = file.getInputStream();
            // 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(XLS)) {
                // 2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLS_X)) {
                // 2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("excel 转换 HSSFWorkbook 异常！", e);
        }
        return workbook;
    }

    private void checkFile(MultipartFile file) {
        // 判断文件是否存在
        if (null == file) {
            throw new RuntimeException("文件不存在!!");
        }
        // 获得文件名
        String fileName = file.getOriginalFilename();
        // 判断文件是否是excel文件
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLS_X)) {
            throw new RuntimeException(fileName + "不是excel文件");
        }
    }


    public <T> void exportExcel(HttpServletResponse response, ExcelData data, Class<T> clazz) {
        log.info("导出解析开始，fileName:{}", data.getFileName());
        try {
            //实例化XSSFWorkbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            //创建一个Excel表单，参数为sheet的名字
            XSSFSheet sheet = setSheet(clazz, workbook);
            //设置单元格并赋值
            setData(workbook, sheet, data.getData(), setTitle(workbook, sheet, clazz));
            //设置浏览器下载
            setBrowser(response, workbook, data.getFileName() + XLS_X);
            log.info("导出解析成功!");
        } catch (Exception e) {
            log.info("导出解析失败!");
            e.printStackTrace();
        }
    }

    private <T> XSSFSheet setSheet(Class<T> clazz, XSSFWorkbook workbook) {
        if (clazz.isAnnotationPresent(ExcelSheet.class)) {
            ExcelSheet excelSheet = clazz.getDeclaredAnnotation(ExcelSheet.class);
            return workbook.createSheet(excelSheet.title());
        }
        return workbook.createSheet("sheet");
    }

    private Field[] setTitle(XSSFWorkbook workbook, XSSFSheet sheet, Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        try {
            XSSFCellStyle style = createXssfCellStyle(workbook);
            setHeaderTemplate(sheet, clazz, style);
            setColumnTemplate(sheet, fields, style);
            setColumnTitle(sheet, fields, style);
        } catch (Exception e) {
            log.info("导出时设置表头失败！");
            e.printStackTrace();
        } finally {
            return fields;
        }
    }

    private XSSFCellStyle createXssfCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        XSSFDataFormat fmt = workbook.createDataFormat();
        style.setDataFormat(fmt.getFormat("m/d/yy h:mm"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private void setColumnTemplate(XSSFSheet sheet, Field[] fields, XSSFCellStyle style) {
        int nextRow = sheet.getLastRowNum() + 1;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ExcelTemplate.class)) {
                ExcelTemplate template = field.getDeclaredAnnotation(ExcelTemplate.class);
                CellRangeAddress region = new CellRangeAddress(nextRow, nextRow + template.rowspan(), template.colIndex(), template.colIndex() + template.colspan());
                XSSFRow row = sheet.getRow(nextRow);
                if (Objects.isNull(row)) {
                    row = sheet.createRow(nextRow);
                }
                sheet.addMergedRegion(region);
                XSSFCell cell = row.createCell(template.colIndex());
                cell.setCellValue(template.value());
                cell.setCellStyle(style);
                XSSFRow lastRow = sheet.getRow(nextRow + template.rowspan());
                if (Objects.isNull(lastRow)) {
                    sheet.createRow(nextRow + template.rowspan());
                }
            }
        }
    }

    private void setColumnTitle(XSSFSheet sheet, Field[] fields, XSSFCellStyle style) {
        int nextRow = sheet.getLastRowNum() + 1;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn excelColumn = field.getDeclaredAnnotation(ExcelColumn.class);
                sheet.setColumnWidth(excelColumn.index(), 15 * 256);
                XSSFRow row = sheet.getRow(nextRow);
                if (Objects.isNull(row)) {
                    row = sheet.createRow(nextRow);
                }
                XSSFCell cell = row.createCell(excelColumn.index());
                cell.setCellValue(excelColumn.title());
                cell.setCellStyle(style);
            }
        }
    }

    private void setHeaderTemplate(XSSFSheet sheet, Class clazz, XSSFCellStyle style) {
        if (clazz.isAnnotationPresent(ExcelTemplate.class)) {
            ExcelTemplate template = (ExcelTemplate) clazz.getDeclaredAnnotation(ExcelTemplate.class);
            CellRangeAddress region = new CellRangeAddress(FIRST_ROW, FIRST_ROW + template.rowspan(), template.colIndex(), template.colIndex() + template.colspan());
            XSSFRow row = sheet.createRow(FIRST_ROW);
            sheet.addMergedRegion(region);
            XSSFCell cell = row.createCell(FIRST_COL);
            cell.setCellValue(template.value());
            cell.setCellStyle(style);
        }
    }

    private <T> void setData(XSSFWorkbook workbook, XSSFSheet sheet, List<T> data, Field[] fields) {
        try {
            int lastRow = sheet.getLastRowNum();
            for (int i = 0; i < data.size(); i++) {
                XSSFRow row = sheet.createRow(lastRow + i + 1);
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(ExcelColumn.class)) {
                        ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                        Object value = field.get(data.get(i));
                        if (Objects.isNull(value)) {
                            continue;
                        }
                        if (field.getType().equals(Double.class)) {
                            XSSFCell cell = row.createCell(excelColumn.index());
                            cell.setCellValue((Double) value);
                            setDataCellStyle(workbook, excelColumn, cell);
                        } else if (field.getType().equals(Date.class)) {
                            XSSFCell cell = row.createCell(excelColumn.index());
                            cell.setCellValue((Date) value);
                            setDataCellStyle(workbook, cell,
                                    StringUtils.isNoneBlank(excelColumn.format()) ? excelColumn.format() : TIMEF_FORMAT);
                        } else if (field.getType().equals(LocalDate.class)) {
                            XSSFCell cell = row.createCell(excelColumn.index());
                            cell.setCellValue((LocalDate) value);
                            setDataCellStyle(workbook, cell,
                                    StringUtils.isNoneBlank(excelColumn.format()) ? excelColumn.format() : DATE_FORMAT);
                        } else if (field.getType().equals(LocalDateTime.class)) {
                            XSSFCell cell = row.createCell(excelColumn.index());
                            cell.setCellValue((LocalDateTime) value);
                            setDataCellStyle(workbook, cell,
                                    StringUtils.isNoneBlank(excelColumn.format()) ? excelColumn.format() : TIMEF_FORMAT);
                        } else if (field.getType().equals(Integer.class)) {
                            XSSFCell cell = row.createCell(excelColumn.index());
                            cell.setCellValue((Integer) value);
                            setDataCellStyle(workbook, excelColumn, cell);
                        } else {
                            XSSFCell cell = row.createCell(excelColumn.index());
                            cell.setCellValue((String) value);
                            setDataCellStyle(workbook, excelColumn, cell);
                        }
                    }
                }
            }
            log.info("表格赋值成功！");
        } catch (Exception e) {
            log.info("表格赋值失败！");
            e.printStackTrace();
        }
    }

    private void setDataCellStyle(XSSFWorkbook workbook, XSSFCell cell, String format) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFDataFormat fmt = workbook.createDataFormat();
        style.setDataFormat(fmt.getFormat(format));
        cell.setCellStyle(style);
    }

    private void setDataCellStyle(XSSFWorkbook workbook, ExcelColumn excelColumn, XSSFCell cell) {
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFDataFormat fmt = workbook.createDataFormat();
        if (StringUtils.isNoneBlank(excelColumn.format())) {
            style.setDataFormat(fmt.getFormat(excelColumn.format()));
        }
        cell.setCellStyle(style);
    }

    private void setBrowser(HttpServletResponse response, XSSFWorkbook workbook, String fileName) {
        try {
            //清空response
            response.reset();
            //设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            //将excel写入到输出流中
            workbook.write(os);
            os.flush();
            os.close();
            log.info("设置浏览器下载成功！");
        } catch (Exception e) {
            log.info("设置浏览器下载失败！");
            e.printStackTrace();
        }
    }
}
