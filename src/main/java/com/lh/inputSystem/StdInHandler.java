package com.lh.inputSystem;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * 从控制台接受输入
 *
 * Created by LH 2446059046@qq.com on 2017/6/20.
 */
public class StdInHandler implements FileHandler {
    private String input_buffer = "";//接收控制台的输入
    private int curPos = 0;//输入流当前的位置
    private final String BYTE_CODE = "UTF8";

    @Override
    public void open() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            String line = scan.nextLine();

            if ("end".equals(line))
                break;

            input_buffer += line;
        }

        scan.close();
    }

    @Override
    public int close() {
        return 0;
    }

    @Override
    public int read(byte[] buf, int begin, int len) {
        if (curPos >= input_buffer.length()) {
            return 0;
        }

        int readCnt = 0;
        try {
            byte[] inputBuf = input_buffer.getBytes("UTF8");
            while (curPos + readCnt < input_buffer.length() && readCnt < len) {
                buf[begin + readCnt] = inputBuf[curPos + readCnt];
                readCnt++;
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return readCnt;
    }
}
