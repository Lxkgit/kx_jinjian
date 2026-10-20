package com.kx.system.util.convert;

import com.kx.common.core.utils.DateUtils;
import com.opencsv.bean.AbstractBeanField;

public class CsvJavaUtilDateConvert extends AbstractBeanField {

    @Override
    protected Object convert(String value) {
        return DateUtils.parseDate(value);
    }
}
