package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.PostDTO;
import com.stackunderflow.backend.DTOS.SavePostDTO;
import com.stackunderflow.backend.DTOS.VoteDTO;
import com.stackunderflow.backend.Exception.ForbiddenActionException;
import com.stackunderflow.backend.Exception.ObjectNotFound;
import com.stackunderflow.backend.model.*;
import com.stackunderflow.backend.model.Comment;
import com.stackunderflow.backend.model.Post;
import com.stackunderflow.backend.model.PostXTopic;
import com.stackunderflow.backend.model.PostXTopicId;
import com.stackunderflow.backend.model.Users;
import com.stackunderflow.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostXTopicRepository postXTopicRepository;
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;
    private final BadgeRepository badgeRepository;
    private final UserXBadgeRepository userXBadgeRepository;

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
            postXTopicRepository.save(new PostXTopic(new PostXTopicId(newPost, tag)));
        });

        int postsNr = postRepository.findPostsByUserId(user.getId()).size();
        if(postsNr == 3){
            Badge badge = badgeRepository.findById((long)1).get();
            UserXBadgeId ubId = new UserXBadgeId(user, badge);
            UserXBadge userXBadge = new UserXBadge(ubId, LocalDateTime.now());
            userXBadgeRepository.save(userXBadge);
        }
        return new Message(newPost.getId().toString());
    }

    @Override
    public Message editPost(SavePostDTO editPost, Long id, String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new ObjectNotFound("The requested post was not found"));
        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new ForbiddenActionException("Cannot edit post without ownership");
        }
        post.setTitle(editPost.getTitle());
        post.setDescription(editPost.getBody());
        post.setDate(LocalDateTime.now());
        postRepository.save(post);
        postXTopicRepository.deletePostXTopicByPostId(post.getId());
        editPost.getTags().forEach(tag -> {
            postXTopicRepository.save(new PostXTopic(new PostXTopicId(post, tag)));
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
        List<Vote> votes =  new ArrayList<>();
        if (email != null){
            Users user = userRepository.findByEmail(email).get();
            votes = voteRepository.getVotesByUserAndPostId(user.getId(), id);
        }

        return PostDTO.builder()
                .title(post.getTitle())
                .body(post.getDescription())
                .tags(topics)
                .votesByLoggedUser(votes.isEmpty() ? null : votes.stream().map(this::mapEntityToVoteDTO).collect(Collectors.toList()))
                .bestAnswer(bestAnswers.isEmpty() ? null : bestAnswers.get(0)).build();
                .createdDate(post.getDate())
                .userName(post.getUser().getUsername())
                .userId(post.getUser().getId())
                .build();
    }

    private VoteDTO mapEntityToVoteDTO(Vote vote) {
        return VoteDTO.builder()
                .commentId(vote.getId().getComment().getId())
                .voteType(vote.getVoteType()).build();
    }

    @Override
    public Message deletePost(Long id, String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new ObjectNotFound("The requested post was not found"));
        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new ForbiddenActionException("Cannot delete post without ownership");
        }
        post.getComments().forEach(comment -> {
            voteRepository.deleteVoteByCommentId(comment.getId());
        });
        postXTopicRepository.deletePostXTopicByPostId(post.getId());
        postRepository.delete(post);

        int postsNr = postRepository.findPostsByUserId(user.getId()).size();
        if(postsNr == 2){
            Badge badge = badgeRepository.findById((long)1).get();
            UserXBadgeId ubId = new UserXBadgeId(user, badge);
            UserXBadge userXBadge = userXBadgeRepository.findById(ubId).get();
            userXBadgeRepository.delete(userXBadge);
        }

        return new Message("Post deleted successfully");
    }

    @Override
    public Message chooseBestAnswer(Long id, Long answerId, String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new ObjectNotFound("The requested post was not found"));
        if (!Objects.equals(user.getId(), post.getUser().getId())) {
            throw new ForbiddenActionException("Cannot select best answer without ownership");
        }
        Comment comment = commentRepository.findById(answerId).orElseThrow(() -> new ObjectNotFound("Answer not found"));
        user.setPoints(user.getPoints() + 10.d);
        commentRepository.chooseBestAnswer(answerId);
        userRepository.save(user);

        int answerNr = commentRepository.findBestAnswersByUserId(comment.getUser().getId()).size();
        if(answerNr == 1){
            Badge badge = badgeRepository.findById((long)3).get();
            UserXBadgeId ubId = new UserXBadgeId(user, badge);
            UserXBadge userXBadge = new UserXBadge(ubId, LocalDateTime.now());
            userXBadgeRepository.save(userXBadge);
        }

        return new Message("Best answer picked successfully");
    }

    @Override
    public Message deleteBestAnswer(Long id, Long answerId, String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new ObjectNotFound("The requested post was not found"));
        if (!Objects.equals(user.getId(), post.getUser().getId())) {
            throw new ForbiddenActionException("Cannot delete best answer without ownership");
        }
        Comment comment = commentRepository.findById(answerId).orElseThrow(() -> new ObjectNotFound("Answer not found"));
        user.setPoints(user.getPoints() - 10.d);
        commentRepository.deleteBestAnswer(answerId);
        userRepository.save(user);

        int answerNr = commentRepository.findBestAnswersByUserId(comment.getUser().getId()).size();
        if(answerNr == 0){
            Badge badge = badgeRepository.findById((long)3).get();
            UserXBadgeId ubId = new UserXBadgeId(user, badge);
            UserXBadge userXBadge = userXBadgeRepository.findById(ubId).get();
            userXBadgeRepository.delete(userXBadge);
        }

        return new Message("Best answer deleted successfully");
    }
}
