package com.borderfree.kata.numerals;


import java.util.EnumSet;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

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
        AccNumeral result = romanNumerals.stream().reduce(new AccNumeral("", arabic), new BiFunction<AccNumeral, RomanNumeral, AccNumeral>(){
            @Override
            public AccNumeral apply(AccNumeral accNumeral, RomanNumeral romanNumeral) {
                return appendRomanNumeral(accNumeral, romanNumeral);
            }
        }, new BinaryOperator<AccNumeral>(){
            @Override
            public AccNumeral apply(AccNumeral accNumeral, AccNumeral accNumeral2) {
                return new AccNumeral(accNumeral.str + accNumeral2.str, accNumeral.remaining - accNumeral2.remaining);
            }
        });
        return result.str;
    }

    private static AccNumeral appendRomanNumeral(AccNumeral acc, RomanNumeral romanNumeral) {
        int remaining = acc.remaining;
        String str = acc.str;
        while (remaining >= romanNumeral.arabic ) {
            str += romanNumeral.roman;
            remaining -= romanNumeral.arabic;
        }
        return new AccNumeral(str, remaining);
    }
}