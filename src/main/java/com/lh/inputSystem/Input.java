package com.lh.inputSystem;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by LH 2446059046@qq.com on 2017/6/20.
 */
public class Input {
    private final int MAX_LOOK = 16;//look ahead(预读取)的最多字符数
    private final int MAX_LEX = 1024;//分词后字符串的最大长度
    private final int BUF_SIZE = 3 * MAX_LEX + 2 * MAX_LOOK;//缓冲区大小
    private int END_BUF = BUF_SIZE;//缓冲区逻辑结束地址
    private final int DANGER = END_BUF - MAX_LOOK;//报警线
    private final int END = BUF_SIZE;//缓冲区结束地址
    private final byte[] START_BUF = new byte[BUF_SIZE];//缓冲区
    private int next = END;//指向当前要读入的字符位置
    private int sMark = END;//当前字符串的起始位置
    private int eMark = END;//当前字符串的结束位置
    private int pMark = END;//上个字符串的起始位置
    private int pLineNo = 0;//上个字符串的行号
    private int pLength = 0;//上个字符串的长度
    private int lineNo = 1;//当前字符串的行号
    private int myLine = 1;//当前的行号

    private FileHandler fileHandler = null;//输入流对象

    private boolean eof_read = false;//输入流中是否还有可读信息

    /**
     * 缓冲区中是否还有可读的字符
     *
     * @return
     */
    private boolean noMoreChars() {
        return eof_read && next >= END_BUF;
    }

    /**
     * 选择输入流
     *
     * @param fileName
     * @return
     */
    private FileHandler getFileHandler(String fileName) {
        if (null == fileName)
            return new StdInHandler();
        else
            return null;
    }

    /**
     * 初始化操作
     *
     * @param fileName
     */
    public void ii_newFile(String fileName) {
        if (null != fileHandler)
            fileHandler.close();

        fileHandler = getFileHandler(fileName);
        fileHandler.open();

        eof_read = false;
        next = END;
        sMark = END;
        eMark = END;
        END_BUF = END;
        lineNo = 1;
        myLine = 1;
    }

    /**
     * 返回当前的字符串
     *
     * @return
     */
    public String ii_text() {
        byte[] bytes = Arrays.copyOfRange(START_BUF, sMark, sMark + ii_length());
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 当前字符串的长度
     *
     * @return
     */
    public int ii_length() {
        return eMark - sMark;
    }

    /**
     * 当前字符串的长度
     *
     * @return
     */
    public int ii_lineNo() {
        return lineNo;
    }

    /**
     * 获取前一个字符串
     *
     * @return
     */
    public String ii_ptext() {
        byte[] bytes = Arrays.copyOfRange(START_BUF, pMark, pMark + this.ii_plength());
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 上个字符串的长度
     *
     * @return
     */
    public int ii_plength() {
        return pLength;
    }

    /**
     * 上个字符串行号
     *
     * @return
     */
    public int ii_plineno() {
        return pLineNo;
    }

    /**
     * 定位到下一个字符串
     *
     * @return
     */
    public int ii_mark_start() {
        myLine = lineNo;
        sMark = eMark = next;
        return sMark;
    }

    public int ii_mark_end() {
        myLine = lineNo;
        eMark = next;
        return eMark;
    }

    public int ii_move_start() {
        if (sMark >= eMark)
            return -1;
        else
            sMark++;

        return sMark;
    }

    public int ii_to_mark() {
        lineNo = myLine;
        next = eMark;
        return next;
    }

    /**
     * 执行这个函数后，上一个被词法解析器解析的字符串将无法在缓冲区中找到
     *
     * @return
     */
    public int ii_mark_prev() {
        pMark = sMark;
        pLineNo = lineNo;
        pLength = eMark - sMark;
        return pMark;
    }


}
