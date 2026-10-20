package com.kx.system.util;

import cn.hutool.core.exceptions.ValidateException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ListSplitUtils {
    public static <T> void split(Iterable<T> iterable, int batchSize, Consumer<List<T>> consumer) {
        if (batchSize < 1) {
            throw new ValidateException("Batch size can not less than 1");
        }

        List<T> temp = new ArrayList<>();
        Iterator<T> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            temp.add(iterator.next());
            if (temp.size() == batchSize) {
                consumer.accept(temp);
                temp.clear();
            }
        }
        if (!CollectionUtils.isEmpty(temp)) {
            consumer.accept(temp);
        }
    }
}
