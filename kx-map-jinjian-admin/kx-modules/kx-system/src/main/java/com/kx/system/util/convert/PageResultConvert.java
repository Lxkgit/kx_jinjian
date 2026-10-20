package com.kx.system.util.convert;

import com.github.pagehelper.Page;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageResultConvert {

    public static <E, T> List<T> convertPage(List<E> list, Function<E, T> convert) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        if (list instanceof Page) {
            Page page = (Page) list;
            for (int i = 0; i < page.size(); i++) {
                page.set(i, convert.apply(list.get(i)));
            }
            return page;
        }
        return list.stream().map(convert).collect(Collectors.toList());

    }
}
