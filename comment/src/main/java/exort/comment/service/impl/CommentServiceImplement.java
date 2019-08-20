package exort.comment.service.impl;

import exort.comment.entity.Comment;
import exort.comment.repository.CommentRepository;
import exort.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentServiceImplement implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(int userId, String type, String commentedId, String content) {

        Comment comment = new Comment(userId, type, commentedId, content);
        Comment newComment = commentRepository.save(comment);

        if (type.equals("comment")) {
            addReply(commentedId, newComment.getId());
        }

        return commentRepository.save(newComment);
    }

    @Override
    public Comment deleteComment(String id) {

        Comment comment = new Comment();

        if (commentRepository.findById(id).isPresent()) {
            comment = commentRepository.findById(id).get();
        }

        if (comment.getType().equals("comment")) {
            deleteReply(comment.getCommentedId(), comment.getId());
        }

        LinkedList<String> allreplies = new LinkedList<>(comment.getReplies());

        while (!allreplies.isEmpty()) {
            String replyId = allreplies.poll();
            if (replyId == null) {
                break;
            }
            if (commentRepository.existsById(replyId)) {
                allreplies.addAll(commentRepository.findById(replyId).get().getReplies());
            }
            commentRepository.deleteById(replyId);
        }

        commentRepository.delete(comment);

        return comment;
    }

    @Override
    public List<Comment> getCommentsByTypeAndCommentedId(String type, String commentedId) {
        return commentRepository.findAllByTypeAndCommentedId(type, commentedId);
    }

    @Override
    public Comment getOneComment(String id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public Boolean existComment(String id) {
        return commentRepository.existsById(id);
    }

    @Override
    public List<Comment> getCommentsByUserId(int userId) {
        return commentRepository.findAllByUserId(userId);
    }

    @Override
    public List<Comment> getAllReplies(String id) {
        Comment comment = commentRepository.findById(id).get();

        LinkedList<String> allReplies = new LinkedList<>(comment.getReplies());
        List<Comment> ret = new ArrayList<>();

        while (!allReplies.isEmpty()) {
            String replyId = allReplies.poll();
            if (replyId == null) {
                break;
            }
            Comment reply=commentRepository.findById(replyId).get();
            ret.add(reply);
            allReplies.addAll(reply.getReplies());
        }

        return ret;
    }

    @Override
    public void addReply(String commentedId, String replyId) {
        Comment comment = commentRepository.findById(commentedId).get();
        List<String> replies = comment.getReplies();
        replies.add(replyId);
        comment.setReplies(replies);
        commentRepository.save(comment);
    }

    @Override
    public void deleteReply(String commentedId, String replyId) {
        if (!commentRepository.existsById(commentedId)) {
            return;
        }
        Comment comment = commentRepository.findById(commentedId).get();
        List<String> replies = comment.getReplies();
        replies.remove(replyId);
        comment.setReplies(replies);
        commentRepository.save(comment);
    }
}
