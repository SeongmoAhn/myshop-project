package com.seongmo.myshop.member.dto;

import com.seongmo.myshop.member.Member;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberResponse {
    private Long id;
    private String email;
    private String nickname;
    private List<String> itemTitles;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.itemTitles = member.getItems().stream()
                .map(item -> item.getTitle())
                .collect(Collectors.toList());
    }
}
