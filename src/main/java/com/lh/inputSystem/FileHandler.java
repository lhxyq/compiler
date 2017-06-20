package com.lh.inputSystem;

/**
 * Created by LH 2446059046@qq.com on 2017/6/20.
 */
public interface FileHandler {
    /**
     * 打开输入流
     *
     */
    void open();

    /**
     * 关闭输入流
     *
     * @return
     */
    int close();

    /**
     * 读取输入流中的字符，并返回实际读取的长度
     *
     * @param buf
     * @param begin 开始处
     * @param len 读取的长度
     * @return
     */
    int read(byte[] buf, int begin, int len);
}
