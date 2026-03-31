package com.seongmo.myshop.item;

import com.seongmo.myshop.exception.BusinessException;
import com.seongmo.myshop.item.dto.ItemCreateRequest;
import com.seongmo.myshop.item.dto.ItemResponse;
import com.seongmo.myshop.member.Member;
import com.seongmo.myshop.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    @DisplayName("상품 등록 성공")
    void createItemSuccess() {
        // given
        Member seller = new Member(1L, "test@test.com", "1234", "성모");
        ItemCreateRequest request = new ItemCreateRequest("아이폰 16", "팝니다", 900000, 1L);
        given(memberRepository.findById(1L)).willReturn(Optional.of(seller));
        given(itemRepository.save(any(Item.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        ItemResponse response = itemService.createItem(request);

        // then
        assertThat(response.getTitle()).isEqualTo("아이폰 16");
        assertThat(response.getPrice()).isEqualTo(900000);
        assertThat(response.getSellerNickname()).isEqualTo("성모");
    }

    @Test
    @DisplayName("존재하지 않는 회원으로 상품 등록 시 예외 발생")
    void createItemMemberNotFound() {
        // given
        ItemCreateRequest request = new ItemCreateRequest("아이폰 16", "팝니다", 900000, 999L);
        given(memberRepository.findById(999L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> itemService.createItem(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 상품 조회 시 예외 발생")
    void getItemNotFound() {
        // given
        given(itemRepository.findById(999L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> itemService.getItem(999L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }
}
