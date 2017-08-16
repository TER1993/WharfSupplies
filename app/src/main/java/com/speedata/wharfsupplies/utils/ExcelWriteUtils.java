package com.speedata.wharfsupplies.utils;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by xu on 2017/8/15.
 */

public class ExcelWriteUtils {


    public static int writeExcel(String file_name, List<List<Object>> data_list) {

        try {

            WritableWorkbook book = Workbook.createWorkbook(new File(file_name));

            WritableSheet sheet1 = book.createSheet("sheet1", 0);

            for (int i = 0; i < data_list.size(); i++) {

                List<Object> obj_list = data_list.get(i);

                for (int j = 0; j < obj_list.size(); j++) {

                    Label label = new Label(j, i, obj_list.get(j).toString());

                    sheet1.addCell(label);
                }
            }
            book.write();
            book.close();

        } catch (Exception e) {

            e.printStackTrace();
            return -1;
        }
        return 0;
    }

}
