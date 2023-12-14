package com.Blogging.Blogging.Services;

import com.Blogging.Blogging.Exception.ResourceNotFoundException;
import com.Blogging.Blogging.Model.Comment;
import com.Blogging.Blogging.Model.Post;
import com.Blogging.Blogging.Model.User;
import com.Blogging.Blogging.Repositry.CommentRepo;
import com.Blogging.Blogging.Repositry.PostRepo;
import com.Blogging.Blogging.Repositry.UserRepo;
import com.Blogging.Blogging.payloads.CommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommenServiceImpl  implements  CommentService{


    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Override
    public CommentDto create(CommentDto commentDto, Integer postId) {

        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post Not found","With Id: ",postId.longValue()));

        User user = userRepo.findById(post.getUser().getId()).orElseThrow(()->new ResourceNotFoundException("User","Not Exits",post.getUser().getId().longValue()));
        Comment comment = modelMapper.map(commentDto,Comment.class);


        post.getComment().add(comment);

        post.setUser(user);
        comment.setPost(post);
        comment.setUser(user);

        postRepo.save(post);

          
        commentRepo.save(comment);
         
        CommentDto commentd = modelMapper.map(comment,CommentDto.class);

        return commentd;
    }

    @Override
    public void deletecomment(Integer commentId) {

        Comment comment = commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Does Not Exit",commentId.longValue()));

         commentRepo.delete(comment);
    }
}
