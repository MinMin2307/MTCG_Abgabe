package com.if23b212.mtcg.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class CommonUtils {

    public static boolean checkList(List<?> list) {
        return list != null && list.size() > 0;
    }

    public static boolean compareString(String s1, String s2) {
        return s1 != null && s2 != null && s1.equals(s2);
    }

    public static boolean checkString(String s) {
        return  s != null && s.length() > 0;
    }

    public static LocalDateTime toLocalDateTime(Timestamp timeStamp) {
    	return timeStamp.toLocalDateTime();

    }

}
