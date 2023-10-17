package com.currency.currency_module;

public class NumberToWords {

    private static final String[] a = {"", "one ", "two ", "three ", "four ", "five ", "six ", "seven ", "eight ", "nine ", "ten ", "eleven ", "twelve ", "thirteen ", "fourteen ", "fifteen ", "sixteen ", "seventeen ", "eighteen ", "nineteen "};
    private static final String[] b = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

    public  String inWords(int num) {
        if (num > 999999999) {
            return "overflow";
        }
        
        String numStr = String.format("%09d", num);
        String[] n = numStr.split("(?<=\\G..)");

        StringBuilder str = new StringBuilder();
        str.append(n[0] != "00" ? (a[Integer.parseInt(n[0])] != "" ? a[Integer.parseInt(n[0])] : b[n[0].charAt(0) - '0'] + " " + a[n[0].charAt(1) - '0']) + "crore " : "");
        str.append(n[1] != "00" ? (a[Integer.parseInt(n[1])] != "" ? a[Integer.parseInt(n[1])] : b[n[1].charAt(0) - '0'] + " " + a[n[1].charAt(1) - '0']) + "lakh " : "");
        str.append(n[2] != "00" ? (a[Integer.parseInt(n[2])] != "" ? a[Integer.parseInt(n[2])] : b[n[2].charAt(0) - '0'] + " " + a[n[2].charAt(1) - '0']) + "thousand " : "");
        str.append(n[3] != "00" ? (a[Integer.parseInt(n[3])] != "" ? a[Integer.parseInt(n[3])] : b[n[3].charAt(0) - '0'] + " " + a[n[3].charAt(1) - '0']) + "hundred " : "");
        str.append(n[4] != "00" ? ((str.length() != 0) ? "and " : "") + (a[Integer.parseInt(n[4])] != "" ? a[Integer.parseInt(n[4])] : b[n[4].charAt(0) - '0'] + " " + a[n[4].charAt(1) - '0']) + "only " : "");

        return str.toString();
    }


}
