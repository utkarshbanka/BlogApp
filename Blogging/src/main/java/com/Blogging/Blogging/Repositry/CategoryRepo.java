package com.Blogging.Blogging.Repositry;

import com.Blogging.Blogging.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {


}
