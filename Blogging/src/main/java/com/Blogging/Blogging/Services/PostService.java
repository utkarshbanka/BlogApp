package com.Blogging.Blogging.Services;

import com.Blogging.Blogging.Model.Post;
import com.Blogging.Blogging.payloads.PostDto;
import com.Blogging.Blogging.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto  createPost(PostDto postdto,Integer userid, Integer CategorId);

    PostDto updatePost(PostDto postDto , Integer id);

    void deletPost(Integer id);

    PostResponse getAllPost(Integer pageNumber , Integer pageSize,String sortby,String sortdirection);

    PostDto getPostbyid(Integer id);

    List<PostDto> getPostByCategory(Integer catId);


    List<PostDto> getpostbyUser(Integer userid);

    List<PostDto> searchPost(String keyword);




}
