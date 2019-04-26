package com.ifast.demo.controller;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * excel 表格工具类
 *
 * @author zenglong
 */
public class PoiUtils {

    /**
     * 通用读取方法
     * Excel要求 ：
     * 第一行为表头，表头字段即map的键；
     * 第二行为数据内容；
     * 注意：
     * 这里默认只读取第一个工作簿；
     *
     * @param filePath
     * @return ListMap
     */
    public static List<Map<String, Object>> getList(String filePath) {
        File excelFile = new File(filePath);
        FileInputStream is = null;
        Workbook workbook = null;
        List<Map<String, Object>> listMap = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        try {
            is = new FileInputStream(excelFile);
            if (is != null) {
                workbook = WorkbookFactory.create(is);
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rows = sheet.rowIterator();
                System.out.println("开始读取表头 ");
                Row title = rows.next();
                // 列数
                int cols = 0;
                for (int i = 0; i < title.getPhysicalNumberOfCells(); i++) {
                    titles.add(title.getCell(i).toString());
                    cols++;
                }
                System.out.println("表头：" + titles);

                System.out.println("开始读取数据 ");
                while (rows.hasNext()) {
                    Map<String, Object> map = new HashMap<>(256);
                    org.apache.poi.ss.usermodel.Row row = rows.next();
                    for (int i = 0; i < cols; i++) {
                        Cell cell = row.getCell(i);
                        if (cell != null) {
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                String temp = new DecimalFormat("#.########").format(cell.getNumericCellValue());
                                map.put(titles.get(i), temp);
                            } else {
                                map.put(titles.get(i), cell.getStringCellValue());
                            }
                        } else {
                            map.put(titles.get(i), "");
                        }
                    }
                    listMap.add(map);
                }
                System.out.println("读取完毕 ,总数：" + listMap.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("流关闭失败");
                }
            }
        }
        return listMap.size() == 0 ? null : listMap;
    }
}
