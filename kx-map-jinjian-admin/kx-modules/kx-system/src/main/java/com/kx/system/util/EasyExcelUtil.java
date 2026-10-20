package com.kx.system.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
public class EasyExcelUtil {

    /**
     * 将模板sheet复制多个
     *
     * @param inputStream 模板文件名
     * @param cloneIndex  需要被复制的sheet索引
     * @param sheets      sheet名集合
     * @return
     */
    public static ByteArrayInputStream generateSheetsTemplate(InputStream inputStream, Integer cloneIndex, List<String> sheets) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (int i = 0; i < sheets.size(); i++) {
            workbook.cloneSheet(cloneIndex, sheets.get(i));
        }
        //写到流
        workbook.removeSheetAt(cloneIndex);
        workbook.write(bos);
        return new ByteArrayInputStream(bos.toByteArray());
    }

    public static ByteArrayInputStream generateSheetsTemplate(String templateFileName, Integer cloneIndex, List<String> sheets) throws IOException {
        return generateSheetsTemplate(new FileInputStream(templateFileName), cloneIndex, sheets);
    }

    public static String generateFileName(String fileName) {
        return UUID.randomUUID() + "_" + fileName + ".xlsx";
    }

    /**
     * 将数据写入sheet
     *
     * @param excelWriter 表对象
     * @param fillConfig  填充配置
     * @param sheetName   sheet名
     * @param list        列表
     * @param map         对象
     */
    public static void writeSheetToTemplate(ExcelWriter excelWriter, FillConfig fillConfig, String sheetName, List list, Object map) {
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        if (fillConfig == null) {
            excelWriter.fill(list, writeSheet);
        } else {
            excelWriter.fill(list, fillConfig, writeSheet);
        }
        if (map != null) {
            excelWriter.fill(map, writeSheet);
        }
    }


    public static String getTemplatePath(String filename) {
        String dirPath = System.getProperty("user.dir");
        Path path = Paths.get(dirPath + "/bg-panorama-admin/src/main/resources/excelTemplate/" + filename);
        return path.toAbsolutePath().toString();
    }


    /**
     * 为map填充键值对, 若key重复 则重命名
     *
     * @param sheetMap
     * @param sheetName
     * @param data
     */
    public static void putIfContainsKey(Map sheetMap, String sheetName, Object data) {
        if (sheetMap.containsKey(sheetName)) {
            putIfContainsKey(sheetMap, sheetName + "(1)", data);
        } else {
            sheetMap.put(sheetName, data);
        }
    }

    /**
     * 表头校验
     * @param headList 需要表头
     * @param headMap 请求表头
     */
    public static void verifyHead(List<String> headList, Map<Integer, ReadCellData<?>> headMap) {
        List<String> targetHeadList = headMap.values().stream().map(CellData::getStringValue).collect(Collectors.toList());
        try{
            headList.removeAll(targetHeadList);
        }catch (UnsupportedOperationException uoe){
            return;
        }
        if(!CollectionUtils.isEmpty(headList)) {
            StringBuffer sb = new StringBuffer("导入表格缺少以下字段:\n");
            headList.forEach(head -> sb.append(head).append("\n"));
            sb.append("请参照模板给出的字段导入!");
            throw new RuntimeException(sb.toString());
        }
    }


    /**
     * 表头校验
     * @param headList 需要表头
     * @param headMap 请求表头
     */
    public static String verifyHeadRe(List<String> headList, Map<Integer, ReadCellData<?>> headMap) {
        String reason = "";
        List<String> targetHeadList = headMap.values().stream().map(CellData::getStringValue).collect(Collectors.toList());
        try{
            headList.removeAll(targetHeadList);
        }catch (UnsupportedOperationException uoe){
            return reason;
        }
        if(!CollectionUtils.isEmpty(headList)) {
            StringBuffer sb = new StringBuffer("导入表格缺少以下字段:\n");
            headList.forEach(head -> sb.append(head).append("\n"));
            sb.append("请参照模板给出的字段导入!");
            return sb.toString();
        }
        return reason;
    }
}
