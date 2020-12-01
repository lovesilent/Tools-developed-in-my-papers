package com.lovesilent.GameAnalysis;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.*;
import java.util.List;

import static com.lovesilent.GameAnalysis.Constants2.K;
import static com.lovesilent.GameAnalysis.Constants2.S;


public class UtilityFunctions {

    public String DecimalToKBinaryString(Integer n) {
        String result = Integer.toBinaryString(n);
        int count = 2 * K - result.length();
        while (count > 0) {
            result = "0" + result;
            count--;
        }
        return result;
    }

//    public Integer KBinaryStringToDecimal(String s) {
//        String newStr = s.replaceAll("^(0+)", "");
//        return Integer.parseInt(newStr,2);
//    }

    public void WirteToExcel(List<State> allStates) {
        //创建HSSFWorkbook
        //sheetName
        String sheetName = "results";
        //excel 标题
        String[] title = {"id", "binary", "F_pro_1", "F_pro_2", "F_pro_3", "F_pro_4", "F_pro_5", "F_pro_6", "F_pro_7", "F_inv_1", "F_inv_2", "F_inv_3", "F_inv_4", "F_inv_5", "F_inv_6", "F_inv_7", "V_pro", "V_inv"};
        HSSFWorkbook wb = getHSSFWorkbook(sheetName, title, allStates, null);

        File file = new File("D:\\wuyu\\codes\\GameAna_1\\");
        //filename
        String filename = "results" + System.currentTimeMillis() + ".xls";
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(new File(file, filename));
            //document.write(stream);
            wb.write(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) ;
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public HSSFWorkbook getHSSFWorkbook(String sheetName, String[] titles, List<State> allStates, HSSFWorkbook wb) {
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < titles.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int n = 0; n < S; n++) {
            State cur_state = allStates.get(n);
            Tags tag = cur_state.getCur_tag();
            List<Double> fpro = cur_state.getF_pro();
            List<Double> finv = cur_state.getF_inv();
            row = sheet.createRow(n + 1);
            //将内容按顺序赋给对应的列对象
            row.createCell(0).setCellValue(tag.getId());
            row.createCell(1).setCellValue(tag.getBinary());
            for (int a = 0; a < K; ++a) {
                row.createCell(a + 2).setCellValue(fpro.get(a));
            }
            for (int b = 0; b < K; ++b) {
                row.createCell(b + K + 2).setCellValue(finv.get(b));
            }
            row.createCell(2 * K + 2).setCellValue(cur_state.getValue_pro());
            row.createCell(2 * K + 3).setCellValue(cur_state.getValue_inv());

        }
        return wb;
    }

    public String getGapTime(long time) {
        long hours = time / (1000 * 60 * 60);
        long minutes = (time - hours * (1000 * 60 * 60)) / (1000 * 60);
        String diffTime = "";
        if (minutes < 10) {
            diffTime = hours + ":0" + minutes;
        } else {
            diffTime = hours + ":" + minutes;
        }
        return diffTime;
    }


}
