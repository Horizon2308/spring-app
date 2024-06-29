package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.CommentDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommentService {
    Page<Comment> getCommentsByProductId(Long productId, Pageable pageable) throws DataNotFoundException;
    void addComment(CommentDTO commentDTO) throws DataNotFoundException;
    void deleteComment(Long commentId) throws DataNotFoundException;
}
