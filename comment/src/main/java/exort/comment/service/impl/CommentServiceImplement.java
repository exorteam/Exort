package exort.comment.service.impl;

import exort.comment.entity.Comment;
import exort.comment.repository.CommentRepository;
import exort.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImplement implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(int userId, String type, String commentedId, String content) {

        Comment comment = new Comment(userId, type, commentedId, content);
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteComment(String id) {

        Comment comment = commentRepository.findById(id).get();
        commentRepository.delete(comment);

        return comment;
    }

    @Override
    public List<Comment> getCommentsByTypeAndCommentedId(String type, String commentedId) {
        return commentRepository.findAllByTypeAndCommentedId(type,commentedId);
    }

    @Override
    public Comment getOneComment(String id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public List<Comment> getCommentsByUserId(int userId) {
        return commentRepository.findAllByUserId(userId);
    }
}
