package com.gistone.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author zf1017@foxmail.com
 * @date 2019/12/6 0006 15:22
 * @description
 */
public class UploadFileUtil {
    public static void uploadFile(String path, MultipartFile file) {


        if (!file.isEmpty()) {
            InputStream in = null;
            OutputStream out = null;
            OutputStream out2 = null;
            try {
                File serverFile = new File(path);
                in = file.getInputStream();

                out = new FileOutputStream(serverFile);
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = in.read(b)) > 0) {
                    out.write(b, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                        out = null;
                    }
                    if (out2 != null) {
                        out2.close();
                        out2 = null;
                    }

                    if (in != null) {
                        in.close();
                        in = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
