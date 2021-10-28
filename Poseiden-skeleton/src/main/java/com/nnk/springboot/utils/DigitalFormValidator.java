package com.nnk.springboot.utils;

/**
 * Form validator
 */
public class DigitalFormValidator {

    public static boolean formIsOk(double userInput) {
        return userInput > 0; // verify number is négatif
    }
}
