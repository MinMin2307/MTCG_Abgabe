package util;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;

import com.if23b212.mtcg.util.CommonUtils;

import org.junit.Test;

import static org.junit.Assert.*;


public class CommonUtilsTest {

    @Test
    public void checkListNotEmpty() {
        assertTrue(CommonUtils.checkList(Arrays.asList("item")));
    }

    @Test
    public void checkListEmpty() {
        assertFalse(CommonUtils.checkList(Collections.emptyList()));
    }

    @Test
    public void checkListNull() {
        assertFalse(CommonUtils.checkList(null));
    }

    @Test
    public void compareStringEqual() {
        assertTrue(CommonUtils.compareString("test", "test"));
    }

    @Test
    public void compareStringNotEqual() {
        assertFalse(CommonUtils.compareString("test", "diff"));
    }

    @Test
    public void compareStringOneNull() {
        assertFalse(CommonUtils.compareString("test", null));
    }

    @Test
    public void checkStringNotEmpty() {
        assertTrue(CommonUtils.checkString("test"));
    }

    @Test
    public void checkStringEmpty() {
        assertFalse(CommonUtils.checkString(""));
    }

    @Test
    public void checkStringNull() {
        assertFalse(CommonUtils.checkString(null));
    }

    @Test
    public void toLocalDateTimeNotNull() {
        assertNotNull(CommonUtils.toLocalDateTime(Timestamp.valueOf("2023-01-01 10:10:10.0")));
    }

}