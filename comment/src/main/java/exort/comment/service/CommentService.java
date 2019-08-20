package exort.comment.service;

import exort.comment.entity.Comment;

import java.util.List;

public interface CommentService {
    public Comment createComment(int userId, String type, String commentedId, String content);

    public Comment deleteComment(String id);

    public List<Comment> getCommentsByTypeAndCommentedId(String type, String commentedId);

    public Comment getOneComment(String id);

    public Boolean existComment(String id);

    public List<Comment> getCommentsByUserId(int userId);

    public List<Comment> getAllReplies(String id);

    public void addReply(String commentedId, String replyId);

    public void deleteReply(String commentedId, String replyId);
}
