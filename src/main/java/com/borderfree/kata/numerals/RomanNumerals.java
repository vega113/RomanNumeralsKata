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

    final static EnumSet<RomanNumeral> ROMAN_NUMERALS = EnumSet.allOf(RomanNumeral.class);

    public static String arabicToRoman(final int arabic) {
        final AccNumeral result = ROMAN_NUMERALS.stream().reduce(new AccNumeral(arabic < 0 ? "-" : "", Math.abs(arabic)),
                RomanNumerals::appendRomanNumeral,
                (accNumeral, accNumeral2) ->
                        new AccNumeral(accNumeral.str + accNumeral2.str, accNumeral.remaining - accNumeral2.remaining));
        return result.str;
    }

    private static AccNumeral appendRomanNumeral(final AccNumeral acc, final RomanNumeral romanNumeral) {
        if (acc.remaining >= romanNumeral.arabic) {
            return appendRomanNumeral(new AccNumeral(acc.str + romanNumeral.roman,
                    acc.remaining - romanNumeral.arabic), romanNumeral);
        } else {
            return acc;
        }
    }
}
