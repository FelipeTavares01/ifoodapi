package com.ifoodapi.core.translate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;
import java.util.stream.Collectors;

public class PageableTranslator {

    public static Pageable translate(Pageable pageable, Map<String, String> sortMapping) {

        var orders = pageable.getSort().stream()
                .filter(order -> sortMapping.containsKey(order.getProperty()))
                .map(order -> new Sort.Order(order.getDirection(),
                        sortMapping.get(order.getProperty())))
                .collect(Collectors.toList());

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
}
