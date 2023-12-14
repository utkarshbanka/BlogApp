package com.Blogging.Blogging.Services;

import com.Blogging.Blogging.payloads.CommentDto;

public interface CommentService {


    public CommentDto create(CommentDto commentDto, Integer postId);


    public void deletecomment(Integer commentId);



}
