package com.seongmo.myshop.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT DISTINCT m FROM Member m LEFT JOIN FETCH m.items")
    List<Member> findAllWithItems();
}
