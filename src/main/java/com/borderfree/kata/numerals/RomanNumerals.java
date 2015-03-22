package com.borderfree.kata.numerals;


import java.util.EnumSet;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by yuri.zelikov on 18/03/2015.
 */
public class RomanNumerals {

    private static class Accumulator {
        final String roman;
        final int arabic;

        public Accumulator(String roman, int arabic) {
            this.roman = roman;
            this.arabic = arabic;
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
        Accumulator accumulate(final Accumulator acc, final RomanNumeral romanNumeral);
    }

    private static class RomanNumeralsAccumulatorImpl implements RomanNumeralsAccumulator {
        BiFunction<Accumulator, RomanNumeral, Boolean> predicate;
        BiFunction<Accumulator, RomanNumeral, Accumulator> combiner;

        public RomanNumeralsAccumulatorImpl(BiFunction<Accumulator, RomanNumeral, Boolean> predicate,
                                            BiFunction<Accumulator, RomanNumeral, Accumulator> combiner) {
            this.predicate = predicate;
            this.combiner = combiner;
        }

        @Override
        public Accumulator accumulate(Accumulator acc, RomanNumeral romanNumeral) {
            if (predicate.apply(acc, romanNumeral)) {
                return accumulate(combiner.apply(acc, romanNumeral), romanNumeral);
            } else {
                return acc;
            }
        }
    }

    private final static RomanNumeralsAccumulator arabicToRomanAccumulator =
            new RomanNumeralsAccumulatorImpl((acc, romanNumeral) -> acc.arabic >= romanNumeral.arabic,
                    (acc, numeral) -> new Accumulator(acc.roman.concat(numeral.roman),
                            acc.arabic - numeral.arabic));

    private final static RomanNumeralsAccumulator romanToArabicAccumulator =
            new RomanNumeralsAccumulatorImpl((acc, romanNumeral) -> acc.roman.startsWith(romanNumeral.roman),
                    (acc, numeral) -> new Accumulator(acc.roman.replaceFirst(numeral.roman, ""), acc.arabic + numeral.arabic));

    private static <T> T reduce(Accumulator accNumeral, BiFunction<Accumulator, RomanNumeral, Accumulator> accumulator,
                                Function<Accumulator, T> extractResult) {
        final Accumulator result = ROMAN_NUMERALS.stream().reduce(accNumeral,
                accumulator::apply,
                (accNumeral1, accNumeral2) ->
                        new Accumulator(accNumeral1.roman + accNumeral2.roman, accNumeral.arabic - accNumeral2.arabic));
        return extractResult.apply(result);
    }

    public static String arabicToRoman(final int arabic) {
        return reduce(new Accumulator(arabic < 0 ? "-" : "", Math.abs(arabic)), arabicToRomanAccumulator::accumulate,
                x -> x.roman);
    }

    public static int romanToArabic(String roman) {
        return reduce(new Accumulator(roman, 0), romanToArabicAccumulator::accumulate, x -> x.arabic);
    }
}