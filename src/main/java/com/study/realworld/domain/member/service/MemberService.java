package com.study.realworld.domain.member.service;

import com.study.realworld.domain.member.Member;
import com.study.realworld.domain.member.dto.MemberCreateDto;
import com.study.realworld.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long join(MemberCreateDto dto){

        if(dto.getPassword() == null || dto.getUsername() == null){
            throw new IllegalArgumentException("there is no username or password");
        }

        Member newMember = MemberCreateDto.from(dto);
        newMember.setPassword(passwordEncoder.encode(dto.getPassword()));

        return memberRepository.save(newMember).getId();
    }


}
