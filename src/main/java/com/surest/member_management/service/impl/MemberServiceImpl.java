package com.surest.member_management.service.impl;

import com.surest.member_management.dto.MemberRequest;
import com.surest.member_management.dto.MemberResponse;
import com.surest.member_management.entity.Member;
import com.surest.member_management.exception.MemberNotFoundException;
import com.surest.member_management.exception.ResourceAlreadyExistsException;
import com.surest.member_management.mapper.MemberMapper;
import com.surest.member_management.repository.MemberRepository;
import com.surest.member_management.service.MemberService;
import com.surest.member_management.specification.MemberSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<MemberResponse> getMembers(String firstName, String lastName, Pageable pageable) {
        return memberRepository.findAll(
                MemberSpecification.filterBy(firstName, lastName),
                pageable
        ).map(Member::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "members", key = "#id")
    public MemberResponse getMemberById(UUID id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        return memberMapper.toResponse(member);
    }

    @Override
    @Transactional
    public MemberResponse createMember(MemberRequest request) {
        memberRepository.findByEmail(request.getEmail())
                .ifPresent(existingMember -> {
                    throw new ResourceAlreadyExistsException();
                });
        Member member = memberMapper.toEntity(request);
        Member savedMember = memberRepository.save(member);
        return memberMapper.toResponse(savedMember);
    }

    @Override
    @Transactional
    @CachePut(value = "members", key = "#memberId")
    public MemberResponse updateMember(UUID memberId, MemberRequest request) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        memberMapper.updateEntity(existingMember, request);
        Member updatedMember = memberRepository.save(existingMember);
        return memberMapper.toResponse(updatedMember);
    }

    @Override
    @Transactional
    @CacheEvict(value = "members", key = "#memberId")
    public void deleteMember(UUID memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        memberRepository.delete(member);
    }
}
