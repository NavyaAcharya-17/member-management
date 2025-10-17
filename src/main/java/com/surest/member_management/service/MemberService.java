package com.surest.member_management.service;

import com.surest.member_management.dto.MemberRequest;
import com.surest.member_management.dto.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface MemberService {

    Page<MemberResponse> getMembers(String firstName, String lastName, Pageable pageable);

    MemberResponse getMemberById(UUID id);

    MemberResponse createMember(MemberRequest request);

    MemberResponse updateMember(UUID memberId, MemberRequest request);

    void deleteMember(UUID memberId);
}
