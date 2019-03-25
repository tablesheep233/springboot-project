package org.table.neweims;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import java.io.File;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPhotoUp {

    @Value("${upload-path}")
    private String path;

    /** 文件上传测试 */
    @Test
    public void uploadTest() throws Exception {
        File f = new File("F:/pixiv67014121.png");
        FileCopyUtils.copy(f, new File(path+"/1.png"));
    }
}
