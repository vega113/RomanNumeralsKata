package com.borderfree.kata.numerals;


import java.util.EnumSet;

/**
 * Created by yuri.zelikov on 18/03/2015.
 */
public class RomanNumerals {
    private static class AccNumeral {
        final String str;
        final int remaining;

        public AccNumeral(String str, int remaining) {
            this.str = str;
            this.remaining = remaining;
        }
    }

    private enum RomanNumeral {
        OneThousand(1000, "M"),
        NineHundred(900, "CM"),
        FiveHundred(500, "D"),
        FourHundred(400, "CD"),
        OneHundred(100, "C"),
        Ninety(90, "XC"),
        Fifty(50, "L"),
        Forty(40, "XL"),
        Ten(10, "X"),
        Nine(9, "IX"),
        Five(5, "V"),
        Four(4, "IV"),
        One(1, "I");

        final int arabic;
        final String roman;

        RomanNumeral(int arabic, String roman) {
            this.arabic = arabic;
            this.roman = roman;
        }
    }

    static EnumSet<RomanNumeral> romanNumerals = EnumSet.allOf(RomanNumeral.class);

    public static String arabicToRoman(int arabic) {
        AccNumeral accNumeral = new AccNumeral("", arabic);
        for (RomanNumeral romanNumeral : romanNumerals) {
            while (accNumeral.remaining >= romanNumeral.arabic) {
                accNumeral = appendRomanNumerals(accNumeral, romanNumeral);
            }
        }
        return accNumeral.str;
    }

    private static AccNumeral appendRomanNumerals(AccNumeral acc, RomanNumeral romanNumeral) {
        int remaining = acc.remaining;
        String str = acc.str;
        if (remaining >= romanNumeral.arabic ) {
            str += romanNumeral.roman;
            remaining -= romanNumeral.arabic;
        }
        return new AccNumeral(str, remaining);
    }
}
