package com.example.challenge_7.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomPage<T> {
    int number;
    int size;
    long totalElements;
    int totalPages;
    boolean first;
    boolean last;
    int numberOfElements;
    boolean empty;
    Sort sort;
    List<T> content;
}
