package com.example.challenge_7.mapper;

import com.example.challenge_7.dto.response.CustomPage;
import org.springframework.data.domain.Page;

public class PageMapper {
    public static <T> CustomPage<T> toCustomPage(Page<T> page) {
        CustomPage<T> customPage = new CustomPage<>();
        customPage.setNumber(page.getNumber() + 1);  // Chỉnh sửa số trang (1-based index)
        customPage.setSize(page.getSize());
        customPage.setTotalElements(page.getTotalElements());
        customPage.setTotalPages(page.getTotalPages());
        customPage.setFirst(page.isFirst());
        customPage.setLast(page.isLast());
        customPage.setNumberOfElements(page.getNumberOfElements());
        customPage.setEmpty(page.isEmpty());
        customPage.setContent(page.getContent());
        customPage.setSort(page.getSort());
        return customPage;
    }
}
