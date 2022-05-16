package com.Library.demo.Repos;

import com.Library.demo.Models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    @Query("select p from Post p where p.title=:title")
    List<Post> SearchName(@Param("title")String title);
}
