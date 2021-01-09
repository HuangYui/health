package com.hy;

import com.hy.utils.FileUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author HY
 * @ClassName FileUtilsTest
 * @Description TODE
 * @DateTime 2021/1/9 0:18
 * Version 1.0
 */
public class FileUtilsTest {

    @Test
    public void testFileUpload() {
        try {
            InputStream inputStream=new FileInputStream("D:\\hello.jpg");
            String name= "Second.jpg";
            String s = FileUtils.upLoadFile(inputStream, name);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
