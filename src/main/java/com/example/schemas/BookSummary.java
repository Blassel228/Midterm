package com.example.schemas;

import lombok.Getter;

@Getter
public class BookSummary {
    private Long id;
    private String title;

    public BookSummary(Long id, String title) {
        this.id = id;
        this.title = title;
    }

}

