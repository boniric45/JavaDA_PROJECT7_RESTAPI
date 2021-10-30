package com.nnk.springboot.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DigitalFormValidatorTest {

    double userInput;
    @Mock
    DigitalFormValidator digitalFormValidator;

    @Before
    public void setup() {
        userInput = 0;
    }

    /**
     * test digital formValidator is valid
     */
    @Test
    public void testFormValidatorIsValid() {
        userInput = 12.5645654;
        assertTrue(DigitalFormValidator.formIsOk(userInput));
    }

    /**
     * test digital formValidator is not valid
     */
    @Test
    public void testFormValidatorIsKo() {
        userInput = -125.023;
        boolean formIsValidate = false;
        formIsValidate = DigitalFormValidator.formIsOk(userInput);
        assertFalse(formIsValidate);
    }

}
