package com.dev.leo.testusers;

import com.dev.leo.testusers.utils.StringUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDate(){
        String serverDate = "1965-12-20T13:32:15Z";
        String correctDate = "20/12/1965";

        assertEquals(correctDate, StringUtils.getDateString(serverDate));
    }
}