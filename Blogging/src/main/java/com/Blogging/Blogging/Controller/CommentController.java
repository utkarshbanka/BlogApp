package com.Blogging.Blogging.Controller;

import com.Blogging.Blogging.Model.Comment;
import com.Blogging.Blogging.Services.CommentService;
import com.Blogging.Blogging.payloads.ApiResponse;
import com.Blogging.Blogging.payloads.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto  comment, @PathVariable("postId") Integer postId)
    {
         CommentDto commentDto = commentService.create(comment,postId);
         return new ResponseEntity<CommentDto>(commentDto, HttpStatus.OK);
    }

    @DeleteMapping("/post/comments/{commentId}")
    public ResponseEntity<ApiResponse> deletComment(@PathVariable("commentId") Integer commentId)
    {
          commentService.deletecomment(commentId);
          ApiResponse response = new ApiResponse("Deleted", true);
          return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

}
