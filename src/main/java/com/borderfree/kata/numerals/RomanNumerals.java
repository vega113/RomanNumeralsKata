package com.borderfree.kata.numerals;


import java.util.EnumSet;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

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

    interface RomanNumeralsAccumulator {
        AccNumeral accumulate(final AccNumeral acc, final RomanNumeral romanNumeral);
    }

    private static class RomanNumeralsAccumulatorImpl implements RomanNumeralsAccumulator {
        BiFunction<AccNumeral, RomanNumeral, Boolean> predicate;
        BinaryOperator<String> romanCombiner;
        BinaryOperator<Integer> arabicCombiner;

        public RomanNumeralsAccumulatorImpl(BiFunction<AccNumeral, RomanNumeral, Boolean> predicate,
                                            BinaryOperator<String> romanCombiner, BinaryOperator<Integer> arabicCombiner) {
            this.predicate = predicate;
            this.romanCombiner = romanCombiner;
            this.arabicCombiner = arabicCombiner;
        }

        @Override
        public AccNumeral accumulate(AccNumeral acc, RomanNumeral romanNumeral) {
            if (predicate.apply(acc, romanNumeral)) {
                return accumulate(new AccNumeral(romanCombiner.apply(acc.str, romanNumeral.roman),
                        arabicCombiner.apply(acc.remaining, romanNumeral.arabic)), romanNumeral);
            } else {
                return acc;
            }
        }
    }

    private final static RomanNumeralsAccumulator arabicToRomanAccumulator =
            new RomanNumeralsAccumulatorImpl((acc, romanNumeral) -> acc.remaining >= romanNumeral.arabic,
                    String::concat,
                    Math::subtractExact);

    private final static RomanNumeralsAccumulator romanToArabicAccumulator =
            new RomanNumeralsAccumulatorImpl((acc, romanNumeral) -> acc.str.startsWith(romanNumeral.roman),
                    (str, roman) -> str.replaceFirst(roman, ""),
                    Math::addExact);

    private static <T> T reduce(AccNumeral accNumeral, BiFunction<AccNumeral, RomanNumeral, AccNumeral> accumulator,
                                Function<AccNumeral, T> extractResult) {
        final AccNumeral result = ROMAN_NUMERALS.stream().reduce(accNumeral,
                accumulator::apply,
                (accNumeral1, accNumeral2) ->
                        new AccNumeral(accNumeral1.str + accNumeral2.str, accNumeral.remaining - accNumeral2.remaining));
        return extractResult.apply(result);
    }

    public static String arabicToRoman(final int arabic) {
        return reduce(new AccNumeral(arabic < 0 ? "-" : "", Math.abs(arabic)), arabicToRomanAccumulator::accumulate,
                x -> x.str);
    }

    public static int romanToArabic(String roman) {
        return reduce(new AccNumeral(roman, 0), romanToArabicAccumulator::accumulate, x -> x.remaining);
    }
}
