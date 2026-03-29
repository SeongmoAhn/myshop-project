package com.seongmo.myshop.member;

import com.seongmo.myshop.member.dto.MemberJoinRequest;
import com.seongmo.myshop.member.dto.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberJoinResponse> join(@RequestBody MemberJoinRequest request) {
        MemberJoinResponse response = memberService.join(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberJoinResponse> getMember(@PathVariable Long id) {
        MemberJoinResponse response = memberService.getMember(id);
        return ResponseEntity.ok(response);
    }
}
