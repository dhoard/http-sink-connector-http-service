package com.github.dhoard;

public class Configuration {

    public String getValue(String name, String defaultValue) {
        String result = System.getenv(name);

        if (null == result) {
            result = defaultValue;
        }

        return result;
    }

    public boolean getValue(String name, boolean defaultValue) {
        boolean result = defaultValue;

        String value = System.getenv(name);

        if (null != value) {
            if ("TRUE".equals(value.toUpperCase().trim())) {
                result = true;
            } else {
                result = false;
            }
        }

        return result;
    }

    public Integer getValue(String name, Integer defaultValue) {
        Integer result = defaultValue;

        String value = System.getenv(name);

        if (null != value) {
            result = Integer.parseInt(value);
        }

        return result;
    }
}
