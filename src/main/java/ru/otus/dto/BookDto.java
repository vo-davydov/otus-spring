package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;

    private String name;

    private String authorName;

    private String genreName;

    public BookDto(String name, String authorName, String genreName) {
        this.name = name;
        this.authorName = authorName;
        this.genreName = genreName;
    }
}
