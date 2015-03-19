package com.borderfree.kata.numerals;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by yuri.zelikov on 18/03/2015.
 */
public class RomanNumeralsTest {

    @Test
    public void oneTwoThree() {
        assertEquals("1", "I", RomanNumerals.arabicToRoman(1));
        assertEquals("2", "II", RomanNumerals.arabicToRoman(2));
        assertEquals("3", "III", RomanNumerals.arabicToRoman(3));
    }

    @Test
    public void four() {
        assertEquals("4", "IV", RomanNumerals.arabicToRoman(4));
    }

    @Test
    public void five() {
        assertEquals("5", "V", RomanNumerals.arabicToRoman(5));
    }

    @Test
    public void six() {
        assertEquals("6", "VI", RomanNumerals.arabicToRoman(6));
        assertEquals("7", "VII", RomanNumerals.arabicToRoman(7));
        assertEquals("8", "VIII", RomanNumerals.arabicToRoman(8));
    }

    @Test
    public void nine() {
        assertEquals("9", "IX", RomanNumerals.arabicToRoman(9));
    }

    @Test
    public void ten() {
        assertEquals("10", "X", RomanNumerals.arabicToRoman(10));
        assertEquals("11", "XI", RomanNumerals.arabicToRoman(11));
        assertEquals("12", "XII", RomanNumerals.arabicToRoman(12));
        assertEquals("13", "XIII", RomanNumerals.arabicToRoman(13));
        assertEquals("14", "XIV", RomanNumerals.arabicToRoman(14));
        assertEquals("15", "XV", RomanNumerals.arabicToRoman(15));
        assertEquals("16", "XVI", RomanNumerals.arabicToRoman(16));
        assertEquals("17", "XVII", RomanNumerals.arabicToRoman(17));
        assertEquals("18", "XVIII", RomanNumerals.arabicToRoman(18));
        assertEquals("19", "XIX", RomanNumerals.arabicToRoman(19));
    }

    @Test
    public void twenty() {
        assertEquals("20", "XX", RomanNumerals.arabicToRoman(20));
        assertEquals("21", "XXI", RomanNumerals.arabicToRoman(21));
        assertEquals("30", "XXX", RomanNumerals.arabicToRoman(30));
    }

    @Test
    public void forty() {
        assertEquals("40", "XL", RomanNumerals.arabicToRoman(40));
        assertEquals("41", "XLI", RomanNumerals.arabicToRoman(41));
    }
}
