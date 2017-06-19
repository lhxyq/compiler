package com.lh.lexer;

import java.util.Scanner;

/**
 * Created by LH 2446059046@qq.com on 2017/6/19.
 */
public class Lexer {
    private final int EOI = 0;
    private final int SEMI = 1;
    private final int PLUS = 2;
    private final int TIMES = 3;
    private final int LP = 4;
    private final int RP = 5;
    private final int NUM_OR_ID = 6;
    private final int UNKOWN_SYMBOL = 7;

    private int lookAhead = -1;
    private String yytext = "";
    private int yyLeng = 0;
    private int yyLineNo = 0;

    private String input_buffer = "";
    private String current = "";

    /**
     * 判断字符c是否是字母或数字
     *
     * @param c
     * @return
     */
    private boolean isAlnum(char c) {
        if (Character.isDigit(c) || Character.isAlphabetic(c))
            return true;

        return false;
    }

    /**
     * 处理制台的输入
     */
    private void scann() {
        while ("".equals(current)) {
            Scanner s = new Scanner(System.in);
            while (true) {
                String line = s.nextLine();
                if ("end".equals(line))
                    break;

                input_buffer += line;
                yyLineNo++;
            }
            s.close();

            current = input_buffer;
            current.trim();
        }
    }

    /**
     * 词法解析，分割字符串
     *
     * @return
     */
    private int lex() {
        if (current.isEmpty()) {
            yytext = "end";
            return EOI;
        }

        for (int i = 0; i < current.length(); i++) {
            yyLeng = 0;
            yytext = current.substring(0, 1);

            switch (current.charAt(i)) {
                case ';':
                    current = current.substring(1);
                    return SEMI;
                case '+':
                    current = current.substring(1);
                    return PLUS;
                case '*':
                    current = current.substring(1);
                    return TIMES;
                case '(':
                    current = current.substring(1);
                    return LP;
                case ')':
                    current = current.substring(1);
                    return RP;
                case '\t':
                case '\n':
                case ' ':
                    current = current.substring(1);
                    break;
                default:
                    if (!isAlnum(current.charAt(i))) {
                        return UNKOWN_SYMBOL;
                    } else {
                        while (i < current.length() && isAlnum(current.charAt(i))) {
                            i++;
                            yyLeng++;
                        }
                        yytext = current.substring(0, yyLeng);
                        current = current.substring(yyLeng).trim();
                        return NUM_OR_ID;
                    }
            }
        }
        return UNKOWN_SYMBOL;
    }

    /**
     * 判断词法分析是否已经完成
     *
     * @return
     */
    private boolean isEnd() {
        return lookAhead == EOI;
    }

    /**
     * 解析下一个字符串
     */
    private void advance() {
        lookAhead = lex();
    }

    /**
     * 给当前字符串打上标签
     *
     * @return
     */
    private String token() {
        String token = "";
        switch (lookAhead) {
            case EOI:
                token = "EOI";
                break;
            case PLUS:
                token = "PLUS";
                break;
            case TIMES:
                token = "TIMES";
                break;
            case NUM_OR_ID:
                token = "NUM_OR_ID";
                break;
            case SEMI:
                token = "SEMI";
                break;
            case LP:
                token = "LP";
                break;
            case RP:
                token = "RP";
                break;
        }

        return token;
    }

    public void runLexer() {
        //接受输入
        this.scann();

        //词法解析
        while (!isEnd()) {
            this.advance();
            System.out.println("Token: " + token() + " ,Symbol: " + yytext);
        }
    }
}
