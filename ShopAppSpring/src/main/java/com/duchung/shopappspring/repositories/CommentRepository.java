package com.duchung.shopappspring.repositories;


import com.duchung.shopappspring.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.id = :id ORDER BY c.likes DESC")
    Page<Comment> findAllByIdOrderByLikesDesc(Long id, Pageable pageable);
}
