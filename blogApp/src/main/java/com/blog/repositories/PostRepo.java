package com.blog.repositories;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);//User is a field name
    List<Post> findByCategory(Category category);//Category is field name

    @Query("SELECT m FROM Post m WHERE m.title LIKE %:key%")
    List<Post> searchByTitle(@Param( "key") String title);//Title is field name
}
