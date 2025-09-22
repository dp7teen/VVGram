package com.dp.vvgram.helpers;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderByHelper {
    public static List<Sort.Order> orderBy(List<String> sortBy) {
        return sortBy.stream()
                .map(s -> {
                    String[] parts = s.split(",");
                    String field = parts[0];
                    Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("asc")
                            ? Sort.Direction.ASC : Sort.Direction.DESC;
                    return new Sort.Order(direction, field);
                })
                .toList();
    }
}
