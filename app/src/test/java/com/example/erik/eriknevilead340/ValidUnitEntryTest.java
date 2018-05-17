package com.example.erik.eriknevilead340;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import com.example.erik.eriknevilead340.MainActivity;

public class ValidUnitEntryTest {
    private MainActivity mainActivity = new MainActivity();

    @Test
    public void validInput_True() {
        assertThat(mainActivity.validInput("test"), is(true));
    }

    @Test
    public void emptyInput_False() {
        assertThat(mainActivity.validInput(""), is(false));
    }

    @Test
    public void numericInput_False() {
        assertThat(mainActivity.validInput("1234456789"), is(false));
    }
}
