package com.seongmo.myshop.item.dto;

import com.seongmo.myshop.item.Item;
import lombok.Getter;
import java.io.Serializable;

@Getter
public class ItemResponse implements Serializable {
    private Long id;
    private String title;
    private String description;
    private int price;
    private String status;
    private String sellerNickname;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.title = item.getTitle();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.status = item.getStatus().name();
        this.sellerNickname = item.getSeller().getNickname();
    }
}
