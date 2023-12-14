package com.Blogging.Blogging.Repositry;

import com.Blogging.Blogging.Model.Comment;
import com.Blogging.Blogging.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo  extends JpaRepository<Comment,Integer> {


     public List<Comment> findByPost(Post post);
}
