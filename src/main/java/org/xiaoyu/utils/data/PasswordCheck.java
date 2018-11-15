package org.xiaoyu.utils.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordCheck {
    /**
     *
     * 密码级别为0，不进行校验，直接通过
     * 密码级别1~3进行相应的校验
     *
     */
    public static Boolean check(int level,String password) {
        //长度是否大于等于10
        boolean len = false;
        //是否含有数字
        boolean num = false;
        //是否含有小写字母
        boolean lLetters = false;
        //是否含有大写字母
        boolean uLetters = false;
        //是否含有特殊字符
        boolean sCharacters = false;
        boolean pass = false;
        if (password != null && password.length() > 0) {
            Pattern p1 = Pattern.compile("^(.{10}).*$");
            Pattern p2 = Pattern.compile("[0-9]+");
            Pattern p3 = Pattern.compile("[a-z]+");
            Pattern p4 = Pattern.compile("[A-Z]+");
            Matcher m = p1.matcher(password);
            if (m.find()) {
                len = true;
            }
            m.reset().usePattern(p2);
            if (m.find()) {
                num = true;
            }
            m.reset().usePattern(p3);
            if (m.find()) {
                lLetters = true;
            }
            m.reset().usePattern(p4);
            if (m.find()) {
                uLetters = true;
            }
            if (password.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() != 0) {
                sCharacters = true;
            }
            //密码长度大于等于10，且只含有数字，小写字母，大写字母，特殊字母中的一种
            if (level == 1) {
                if(len && (num||lLetters||uLetters||sCharacters)){
                    pass = true;

                }else{
                    pass = false;
                }
                return pass;
            }
            //长度大于等于10，且只含有数字，小写字母，大写字母，特殊字母中的任意两种
            if (level == 2) {
                if(len && (num&&lLetters||num&&uLetters||num&&sCharacters||lLetters&&uLetters||lLetters&&sCharacters||uLetters&&sCharacters)){
                    pass = true;

                }else{
                    pass = false;
                }
                return pass;
            }
            //长度大于等于10，且包含数字、小写字母、大写字母、特殊符号，其中任意三个
            if (level == 3) {
                if(len && ((num&&lLetters&&uLetters)||(num&&lLetters&&sCharacters)||(num&&uLetters&&sCharacters)||(lLetters&&uLetters&&sCharacters))){
                    pass = true;

                }else{
                    pass = false;
                }
                return pass;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String line1 = "1 23456";
        String line2 = "1234567890";
        String line3 = "1234567890AA";
        String line4 = "assdfhhAADSaa";
        String line5 = "~!assdfhhAADSaa";
        String line6 = "1234567890Adf!@#";
        String line7 = "123 Gdf!@#";
        PasswordCheck pa = new PasswordCheck();
        System.out.println(pa.check(3,line1));
        System.out.println(pa.check(3,line2));
        System.out.println(pa.check(3,line3));
        System.out.println(pa.check(3,line4));
        System.out.println(pa.check(3,line5));
        System.out.println(pa.check(3,line6));
        System.out.println(pa.check(3,line7));
    }
}