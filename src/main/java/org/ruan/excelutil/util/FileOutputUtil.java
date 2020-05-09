package org.ruan.excelutil.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputUtil {

    /**
     *
     * @param fileOutputStream
     * @param data
     * @throws IOException
     */
    public static void upload(FileOutputStream fileOutputStream, byte[] data) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(data);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

}
