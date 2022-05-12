package com.study.realworld.domain.member.controller;

import com.study.realworld.domain.member.dto.MemberCreateDto;
import com.study.realworld.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @PostMapping("/api/member/signin")
    public ResponseEntity<Long> signIn(@RequestBody MemberCreateDto dto){
        Long id = memberService.join(dto);
        return ResponseEntity.ok(id);
    }


}
