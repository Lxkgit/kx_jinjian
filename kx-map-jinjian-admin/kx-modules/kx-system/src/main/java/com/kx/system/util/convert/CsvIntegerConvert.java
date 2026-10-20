package com.kx.system.util.convert;

import com.kx.common.core.utils.StringUtils;
import com.opencsv.bean.AbstractBeanField;

public class CsvIntegerConvert extends AbstractBeanField {

    @Override
    protected Object convert(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        if (value.contains(".")) {
            value = value.substring(0, value.indexOf("."));
        }
        return Integer.parseInt(value);
    }
}
