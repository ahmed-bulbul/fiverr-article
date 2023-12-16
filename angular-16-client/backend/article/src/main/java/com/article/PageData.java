package com.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
public class PageData {
    Collection<?> model;
    int totalPages;
    int currentPage;
    long totalElements;

    public static PageData empty() {
        Page<?> pagedData = Page.empty();
        return PageData.builder()
                .model(pagedData.getContent())
                .totalPages(pagedData.getTotalPages())
                .totalElements(pagedData.getTotalElements())
                .currentPage(1)
                .build();
    }
}
