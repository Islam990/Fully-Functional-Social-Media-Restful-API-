package com.islamgad.restapi.restapi.services;

import com.islamgad.restapi.restapi.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
