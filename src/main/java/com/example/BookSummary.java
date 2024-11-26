package com.example;

public class BookSummary {
    private Long id;
    private String title;

    public BookSummary(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}

