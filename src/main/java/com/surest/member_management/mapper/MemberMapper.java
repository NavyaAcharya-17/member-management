package com.surest.member_management.mapper;

import com.surest.member_management.dto.MemberRequest;
import com.surest.member_management.dto.MemberResponse;
import com.surest.member_management.entity.Member;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class MemberMapper {
    public MemberResponse toResponse(Member member) {
        if (member == null) {
            return null;
        }
        return MemberResponse.builder()
                .memberId(member.getId())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .dateOfBirth(member.getDateOfBirth())
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    public Member toEntity(MemberRequest request) {
        Timestamp now = Timestamp.from(Instant.now());
        return Member.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public void updateEntity(Member member, MemberRequest request) {
        if (member == null || request == null) return;
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setDateOfBirth(request.getDateOfBirth());
        member.setEmail(request.getEmail());
        member.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }
}
