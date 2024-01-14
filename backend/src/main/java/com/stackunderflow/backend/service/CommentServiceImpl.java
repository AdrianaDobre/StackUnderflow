package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.CommentDTO;
import com.stackunderflow.backend.DTOS.EditAnswerDTO;
import com.stackunderflow.backend.DTOS.EditCommentDTO;
import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.SaveCommentDTO;
import com.stackunderflow.backend.DTOS.SuggestionDTOAns;
import com.stackunderflow.backend.Exception.ForbiddenActionException;
import com.stackunderflow.backend.Exception.NoEditAcceptedException;
import com.stackunderflow.backend.Exception.AlreadyExistsException;
import com.stackunderflow.backend.Exception.ForbiddenActionException;
import com.stackunderflow.backend.Exception.ObjectNotFound;
import com.stackunderflow.backend.model.Comment;
import com.stackunderflow.backend.model.Post;
import com.stackunderflow.backend.model.Suggestion;
import com.stackunderflow.backend.model.Users;
import com.stackunderflow.backend.model.Vote;
import com.stackunderflow.backend.model.*;
import com.stackunderflow.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final SuggestionRepository suggestionRepository;
    private final BadgeRepository badgeRepository;
    private final UserXBadgeRepository userXBadgeRepository;

    @Override
    public Message saveComment(SaveCommentDTO comment, String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Comment newComment = Comment.builder()
                .user(user)
                .post(postRepository.findById(comment.getPostId()).orElseThrow(() -> new ObjectNotFound("Post not found")))
                .text(comment.getBody())
                .isTheBest(false)
                .date(LocalDateTime.now()).build();
        commentRepository.save(newComment);

        int answerNr = commentRepository.findAnswersByUserId(user.getId()).size();
        if(answerNr == 3){
            Badge badge = badgeRepository.findById((long)2).get();
            UserXBadgeId ubId = new UserXBadgeId(user, badge);
            UserXBadge userXBadge = new UserXBadge(ubId, LocalDateTime.now());
            userXBadgeRepository.save(userXBadge);
        }

        return new Message("Answer posted successfully");
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ObjectNotFound("Comment not found"));
        return getCommentDTO(comment);
    }

    private CommentDTO getCommentDTO(Comment comment) {
        List<Vote> votes = voteRepository.getVotesByCommentId(comment.getId());
        Long upVoteCount = (long) votes.stream().filter(Vote::getVoteType).toList().size();
        Long downVoteCount = (long) votes.stream().filter(vote -> !vote.getVoteType()).toList().size();
        List<Suggestion> edits = suggestionRepository.findByUserIdAndCommentIdOrderByAcceptedOnDateDesc(comment.getUser().getId(), comment.getId());
        return CommentDTO.builder()
                .answerId(comment.getId())
                .body(edits.isEmpty() ? comment.getText() : edits.get(0).getText())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .upVoteCount(upVoteCount)
                .downVoteCount(downVoteCount)
                .username(comment.getUser().getUsername())
                .createdDate(comment.getDate())
                .lastModified(edits.isEmpty() ? comment.getDate() : edits.get(0).getAcceptedOnDate())
                .revision((long) edits.size())
                .build();
    }

    @Override
    public Message editCommentById(Long commentId, EditCommentDTO editCommentDTO, String email) {
        Comment foundComment = commentRepository.findById(commentId).orElseThrow(() -> new ObjectNotFound("The requested answer was not found"));
        Users foundUser = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!Objects.equals(foundComment.getUser().getId(), foundUser.getId())) {
            throw new ForbiddenActionException("Cannot edit answer without ownership");
        }
        if (foundComment.getIsTheBest()) {
            throw new NoEditAcceptedException("This is the best answer of the post. You cannot edit it anymore.");
        }
        suggestionRepository.save(Suggestion.builder()
                .user(foundUser)
                .comment(foundComment)
                .text(editCommentDTO.getBody())
                .date(foundComment.getDate())
                .accepted(Boolean.TRUE)
                .acceptedOnDate(LocalDateTime.now())
                .build());
        return new Message("Answer edited successfully");
    }

    @Override
    public Message deleteCommentById(Long commentId, String email) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ObjectNotFound("The requested answer was not found"));
        Users foundUser = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!Objects.equals(comment.getUser().getId(), foundUser.getId())) {
            throw new ForbiddenActionException("Cannot delete answer without ownership");
        }
        voteRepository.deleteVoteByCommentId(commentId);
        commentRepository.delete(comment);

        int answerNr = commentRepository.findAnswersByUserId(foundUser.getId()).size();
        if(answerNr == 2){
            Badge badge = badgeRepository.findById((long)2).get();
            UserXBadgeId ubId = new UserXBadgeId(foundUser, badge);
            UserXBadge userXBadge = userXBadgeRepository.findById(ubId).get();
            userXBadgeRepository.delete(userXBadge);
        }

        return new Message("Answer deleted successfully");
    }

    @Override
    public List<CommentDTO> getAllByMostLikes(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ObjectNotFound("The requested post was not found"));
        log.info("Post with id {} not found", post.getId());
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        comments.forEach(comment -> commentDTOS.add(getCommentDTO(comment)));
        return commentDTOS.stream().sorted(Comparator.comparingLong(CommentDTO::getUpVoteCount).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<SuggestionDTOAns> getAllSuggestions(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ObjectNotFound("The requested answer was not found"));
        List<Suggestion> suggestions = suggestionRepository.findSuggestionsWithoutEdits(commentId, comment.getUser().getId());
        return suggestions.stream().map(this::mapEntityToSuggestionDTOAns).collect(Collectors.toList());
    }

    @Override
    public List<EditAnswerDTO> getAllEditsForComment(Long commentId) {
        Comment foundComment = commentRepository.findById(commentId).orElseThrow(() -> new ObjectNotFound("The requested answer was not found"));
        List<Suggestion> edits = suggestionRepository.findByUserIdAndCommentIdOrderByAcceptedOnDateDesc(foundComment.getUser().getId(), foundComment.getId());
        return edits.stream().map(this::mapEntityToEditAnswerDTO).collect(Collectors.toList());
    }

    @Override
    public Message acceptSuggestion(Long id, Long suggestionId) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ObjectNotFound("The requested answer was not found"));
        log.info("comment with id {} was not found", comment.getId());
        Suggestion suggestion = suggestionRepository.findById(suggestionId).orElseThrow(() -> new ObjectNotFound("Suggestion was not found"));
        suggestion.setAccepted(true);
        suggestion.setAcceptedOnDate(LocalDateTime.now());
        suggestionRepository.save(suggestion);
        Users user = suggestion.getUser();
        user.setPoints(user.getPoints() + 5.d);
        userRepository.save(user);
        return new Message("Suggestion accepted successfully");
    }

    private SuggestionDTOAns mapEntityToSuggestionDTOAns(Suggestion suggestion) {
        return SuggestionDTOAns.builder()
                .suggestionId(suggestion.getId())
                .body(suggestion.getText())
                .userId(suggestion.getUser().getId())
                .savedDate(suggestion.getDate())
                .accepted(suggestion.getAccepted())
                .acceptedOnDate(suggestion.getAcceptedOnDate())
                .build();
    }

    private EditAnswerDTO mapEntityToEditAnswerDTO(Suggestion suggestion) {
        return EditAnswerDTO.builder()
                .body(suggestion.getText())
                .revision(suggestion.getId())
                .editDate(suggestion.getAcceptedOnDate())
                .build();
    }

    @Override
    public Message likeComment(Long id, String email) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ObjectNotFound("Answer not found"));
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFound("User not found"));

        VoteId voteId = new VoteId(comment, user);

        Optional<Vote> oldVote = voteRepository.findById(voteId);
        if(oldVote.isPresent()){
            if(oldVote.get().getVoteType()){
                throw new AlreadyExistsException("Answer liked already");
            }
            else{
                voteRepository.deleteById(voteId);
            }
        }

        voteRepository.save(new Vote(voteId,true));
        user.setPoints(user.getPoints() + 1);
        userRepository.save(user);
        return new Message("Answer liked successfully");
    }

    @Override
    public Message dislikeComment(Long id, String email) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ObjectNotFound("Answer not found"));
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFound("User not found"));

        VoteId voteId = new VoteId(comment, user);

        Optional<Vote> oldVote = voteRepository.findById(voteId);
        if(oldVote.isPresent()){
            if(oldVote.get().getVoteType()){
                voteRepository.deleteById(voteId);
            }
            else{
                throw new AlreadyExistsException("Answer disliked already");
            }
        }

        voteRepository.save(new Vote(voteId,false));
        return new Message("Answer disliked successfully");
    }

    @Override
    public Message deleteLikeOrDislike(Long id, String email) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ObjectNotFound("Answer not found"));
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFound("User not found"));

        VoteId voteId = new VoteId(comment, user);

        Optional<Vote> oldVote = voteRepository.findById(voteId);
        if(oldVote.isPresent()){
            if(oldVote.get().getVoteType()){
                user.setPoints(user.getPoints() - 1);
                userRepository.save(user);
            }
            voteRepository.deleteById(voteId);
            return new Message("Like/Dislike deleted successfully");
        }
        else{
            throw new ObjectNotFound("Like/Dislike not found");
        }
    }
}
