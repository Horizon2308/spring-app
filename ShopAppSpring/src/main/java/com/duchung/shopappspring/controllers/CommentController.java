package com.duchung.shopappspring.controllers;


import com.duchung.shopappspring.dtos.CommentDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.http_responses.ErrorResponse;
import com.duchung.shopappspring.http_responses.SuccessResponse;
import com.duchung.shopappspring.services.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/comments")
public class CommentController {

    private final ICommentService commentService;

    @GetMapping("")
    public ResponseEntity<?> getCommentsByProductId(
             @RequestParam(defaultValue = "0", value = "product_id") Long productId,
             @RequestParam(defaultValue = "0") Integer page,
             @RequestParam(defaultValue = "10") Integer limit) {
        return null;
    }


    @PostMapping("")
    public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO) {
        try {
            commentService.addComment(commentDTO);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>("Something wrong!"));
        }
        return ResponseEntity.ok(new SuccessResponse<>("Add comment successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long commentId) {
        try {
            commentService.deleteComment(commentId);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>("Something wrong!"));
        }
        return ResponseEntity.ok(new SuccessResponse<>("Delete comment successfully!"));
    }
}
