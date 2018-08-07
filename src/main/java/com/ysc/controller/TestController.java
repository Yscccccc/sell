package com.ysc.controller;

import com.ysc.dataobject.TestMsg;
import com.ysc.dataobject.User;
import com.ysc.repository.ProductInfoRepository;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/gg")
public class TestController {

    @Autowired
    private ProductInfoRepository repository;


    @GetMapping("/getMsg")
    public List<TestMsg> getFile(@RequestParam("familyId") int familyId) throws RuntimeException {


        List<TestMsg> TestMsgLis = new ArrayList<>();
        TestMsg testMsg1 = new TestMsg();
        testMsg1.setTerminal_code("010611A000042703");
        testMsg1.setEquipment_code("01011280000099AF");
        testMsg1.setNumber("2");
        testMsg1.setState(1);

        TestMsg testMsg2 = new TestMsg();
        testMsg2.setTerminal_code("010611A000042703");
        testMsg2.setEquipment_code("0104210001005071");
        testMsg2.setNumber("1");
        testMsg2.setState(0);

        TestMsgLis.add(testMsg1);
        TestMsgLis.add(testMsg2);
        System.out.println(familyId);
        return TestMsgLis;

    }

    @SuppressWarnings("resource")
    @RequestMapping("/export")
    public void exportExcel(HttpServletResponse response, HttpSession session, String name) throws Exception {

        String abspath=new File("").getAbsolutePath();
        File file=new File(abspath+"/controlLog");
        file.mkdir();

        String[] tableHeaders = {"id", "姓名", "年龄"};

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.RED.index);
        font.setBold(true);
        cellStyle.setFont(font);

        // 将第一行的三个单元格给合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        HSSFRow row = sheet.createRow(0);
        HSSFCell beginCell = row.createCell(0);
        beginCell.setCellValue("通讯录");
        beginCell.setCellStyle(cellStyle);

        row = sheet.createRow(1);
        // 创建表头
        for (int i = 0; i < tableHeaders.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(tableHeaders[i]);
            cell.setCellStyle(cellStyle);
        }

        List<User> users = new ArrayList<>();
        users.add(new User(1, "张三", 20));
        users.add(new User(2, "李四", 21));
        users.add(new User(3, "T0101124000001064", 22));

        for (int i = 0; i < users.size(); i++) {
            row = sheet.createRow(i + 2);

            User user = users.get(i);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getAge());
        }

        File csv = new File(abspath+"\\controlLog\\"+"-控制设备日志"+".csv");//CSV文件
        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, false));
        for (int i = 0; i < users.size(); i++) {
            bw.write(Integer.toString(users.get(i).getId())+","+users.get(i).getName()+","+Integer.toString(users.get(i).getAge()));
            bw.newLine();
        }
        bw.close();

        OutputStream outputStream = response.getOutputStream();
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=template.csv");

        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
