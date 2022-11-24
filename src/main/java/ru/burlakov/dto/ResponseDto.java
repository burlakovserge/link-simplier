package ru.burlakov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private String link;
    private String original;
    private Long count;
    private Long rank;
}
