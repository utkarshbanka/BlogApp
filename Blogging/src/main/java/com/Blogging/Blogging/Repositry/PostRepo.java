package com.Blogging.Blogging.Repositry;

import com.Blogging.Blogging.Model.Category;
import com.Blogging.Blogging.Model.Post;
import com.Blogging.Blogging.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {

     List<Post>  findByUser(User user);
     List<Post> findByCategory(Category cat);


     List<Post> findBytitleContaining(String keyword);

}
