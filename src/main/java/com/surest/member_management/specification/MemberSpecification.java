package com.surest.member_management.specification;

import com.surest.member_management.entity.Member;
import org.springframework.data.jpa.domain.Specification;

public class MemberSpecification {

    public static Specification<Member> filterBy(String firstName, String lastName) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction(); // start with "true"
            if (firstName != null && !firstName.isBlank()) {
                predicates.getExpressions().add(
                        cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%")
                );
            }
            if (lastName != null && !lastName.isBlank()) {
                predicates.getExpressions().add(
                        cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%")
                );
            }
            return predicates;
        };
    }
}
