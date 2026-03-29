package com.seongmo.myshop.item;

import com.seongmo.myshop.exception.BusinessException;
import com.seongmo.myshop.exception.ErrorCode;
import com.seongmo.myshop.item.dto.ItemCreateRequest;
import com.seongmo.myshop.item.dto.ItemResponse;
import com.seongmo.myshop.member.Member;
import com.seongmo.myshop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ItemResponse createItem(ItemCreateRequest request) {
        Member seller = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Item item = new Item(
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                seller
        );

        Item savedItem = itemRepository.save(item);
        return new ItemResponse(savedItem);
    }

    public ItemResponse getItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));
        return new ItemResponse(item);
    }

    public List<ItemResponse> getItemsBySeller(Long memberId) {
        return itemRepository.findBySellerId(memberId)
                .stream()
                .map(ItemResponse::new)
                .collect(Collectors.toList());
    }
}
