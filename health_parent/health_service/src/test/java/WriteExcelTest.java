import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author HY
 * @ClassName WriteExcelTest
 * @Description TODE
 * @DateTime 2021/1/9 23:32
 * Version 1.0
 */
public class WriteExcelTest {

    @Test
    public void testWrite() throws Exception {
        // 创建工作簿，内存中
        Workbook workbook = new XSSFWorkbook();
        // 创建工作表
        Sheet sht = workbook.createSheet("测试写excel");
        // 在工作表下创建行
        Row row = sht.createRow(0);// 行的下标是从0开始
        // 使用行创建单元格
        Cell cell = row.createCell(0);// 单元格的下标也是从0开始, 多个单元格合并后成为1个单元格
        // 给单元格赋值
        // 表头
        cell.setCellValue("姓名");
        row.createCell(1).setCellValue("年龄");
        row.createCell(2).setCellValue("所在地");

        row = sht.createRow(1);
        row.createCell(0).setCellValue("小明");
        row.createCell(1).setCellValue(20);
        row.createCell(2).setCellValue("北京");

        row = sht.createRow(2);
        row.createCell(0).setCellValue("小李");
        row.createCell(1).setCellValue(30);
        row.createCell(2).setCellValue("南京");
        // 保存工作簿，持久化本地硬盘里
        workbook.write(new FileOutputStream(new File("d:\\createExcel.xlsx")));
        // 关闭工作簿
        workbook.close();
    }
}
