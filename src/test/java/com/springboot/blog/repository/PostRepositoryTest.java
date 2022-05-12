package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    public void setup() {
        post = new Post();
        post.setTitle("test title");
        post.setDescription("test description");
        post.setContent("test content");
    }

    // JUnit test for save post operation
    @Test
    public void givenPost_whenSave_thenReturnSavedPost() {

        // when - action or the behavior that we are going to test
        Post savedPost = postRepository.save(post);

        // then - verify the output
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getId()).isGreaterThan(0);
        System.out.println(savedPost);
    }

    @Test
    public void givenPosts_whenFindAll_thenReturnPosts() {

        Post post1 = new Post();
        post1.setTitle("test title1");
        post1.setDescription("test description1");
        post1.setContent("test content1");

        postRepository.saveAll(List.of(post, post1));

        List<Post> posts = postRepository.findAll();
        System.out.println(posts);
        System.out.println(posts.size());

        assertThat(posts).isNotNull();
        assertThat(posts).isNotEmpty();
        assertThat(posts.size()).isNotEqualTo(0);
    }

    @Test
    public void givenPostObject_whenFindById_thenReturnPostObject() {
        postRepository.save(post);

        Post postDB = postRepository.findById(post.getId()).get();

        assertThat(postDB).isNotNull();
    }

    @Test
    public void givenPostObject_whenUpdatePost_thenReturnUpdatedPost() {

        postRepository.save(post);
        Post savedPost = postRepository.findById(post.getId()).get();

        savedPost.setTitle("changed title");
        savedPost.setDescription("changed description");
        savedPost.setContent("changed content");

        Post updatedPost = postRepository.save(savedPost);
        assertThat(updatedPost.getTitle()).isEqualTo("changed title");
        assertThat(updatedPost.getDescription()).isEqualTo("changed description");
        assertThat(updatedPost.getContent()).isEqualTo("changed content");
    }

    @Test
    public void givenPostObject_whenDelete_thenRemovePost() {

        postRepository.save(post);

        postRepository.delete(post);

        Optional<Post> postOptional = postRepository.findById(post.getId());

        assertThat(postOptional).isEmpty();
    }
}