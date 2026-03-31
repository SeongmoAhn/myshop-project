package com.seongmo.myshop.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateRequest {
    private String title;
    private String description;
    private int price;
    private Long memberId;
}
