package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.PostDTO;
import com.stackunderflow.backend.DTOS.SavePostDTO;
import com.stackunderflow.backend.Exception.ForbiddenActionException;
import com.stackunderflow.backend.Exception.ObjectNotFound;
import com.stackunderflow.backend.model.*;
import com.stackunderflow.backend.repository.CommentRepository;
import com.stackunderflow.backend.repository.PostRepository;
import com.stackunderflow.backend.repository.PostXTopicRepository;
import com.stackunderflow.backend.repository.UserRepository;
import com.stackunderflow.backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostXTopicRepository postXTopicRepository;
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;

    @Override
    public Message savePost(SavePostDTO savePostDTO, String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post newPost = Post.builder()
                .user(user)
                .title(savePostDTO.getTitle())
                .description(savePostDTO.getBody())
                .date(LocalDateTime.now()).build();
        postRepository.save(newPost);
        savePostDTO.getTags().forEach(tag -> {
            postXTopicRepository.save(new PostXTopic(new PostXTopicId(newPost,tag)));
        });
        return new Message("Post created successfully");
    }

    @Override
    public Message editPost(SavePostDTO editPost,Long id, String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new ObjectNotFound("The requested post was not found"));
        if (!Objects.equals(post.getUser().getId(), user.getId())){
            throw new ForbiddenActionException("Cannot edit post without ownership");
        }
        post.setTitle(editPost.getTitle());
        post.setDescription(editPost.getBody());
        post.setDate(LocalDateTime.now());
        postRepository.save(post);
        postXTopicRepository.deletePostXTopicByPostId(post.getId());
        editPost.getTags().forEach(tag -> {
            postXTopicRepository.save(new PostXTopic(new PostXTopicId(post,tag)));
        });
        return new Message("Post edited successfully");
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public PostDTO getPostById(Long id, String email) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ObjectNotFound("The requested post was not found"));
        List<String> topics = postXTopicRepository.findPostXTopicByPostId(post.getId()).stream().map(object -> object.getId().getTopic().getName()).toList();
        List<Long> bestAnswers = post.getComments().stream().filter(Comment::getIsTheBest).map(Comment::getId).toList();
        List<Vote> votes =  null;
        if (email != null){
            Users user = userRepository.findByEmail(email).get();
            votes = voteRepository.getVotesByUserAndPostId(user.getId(), id);
        }

        return PostDTO.builder()
                .title(post.getTitle())
                .body(post.getDescription())
                .tags(topics)
                .votesByLoggedUser(votes)
                .bestAnswer(bestAnswers.isEmpty() ? null : bestAnswers.get(0)).build();
    }

    @Override
    public Message deletePost(Long id, String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new ObjectNotFound("The requested post was not found"));
        if (!Objects.equals(post.getUser().getId(), user.getId())){
            throw new ForbiddenActionException("Cannot delete post without ownership");
        }
        post.getComments().forEach(comment -> {
            voteRepository.deleteVoteByCommentId(comment.getId());
        });
        postXTopicRepository.deletePostXTopicByPostId(post.getId());
        postRepository.delete(post);
        return new Message("Post deleted successfully");
    }

    @Override
    public Message chooseBestAnswer(Long answerId, String email) {
        Comment comment = commentRepository.findById(answerId).orElseThrow(() -> new ObjectNotFound("Answer not found"));
        Users user = userRepository.findById(comment.getUser().getId()).orElseThrow(() -> new ObjectNotFound("User not found"));
        user.setPoints(user.getPoints() + 10.d);
        commentRepository.chooseBestAnswer(answerId);
        userRepository.save(user);
        return new Message("Best answer picked successfully");
    }
}
