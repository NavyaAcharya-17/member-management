package com.surest.member_management.controller;

import com.surest.member_management.dto.MemberRequest;
import com.surest.member_management.dto.MemberResponse;
import com.surest.member_management.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // Get all members with optional filters (accessible by USER, ADMIN)
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<MemberResponse> getMembers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @PageableDefault(size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "lastName", direction = Sort.Direction.ASC)
            }) Pageable pageable
    ) {
        return memberService.getMembers(firstName, lastName, pageable);
    }

    //Get member details by ID (accessible by USER, ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable UUID id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    // Create a new member (accessible by ADMIN only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest request) {
        MemberResponse response = memberService.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Update an existing member (accessible by ADMIN only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable UUID id, @Valid @RequestBody MemberRequest request) {
        MemberResponse updatedMember = memberService.updateMember(id, request);
        return ResponseEntity.ok(updatedMember);
    }

    //Update an existing member (accessible by ADMIN only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") @NotNull UUID id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
