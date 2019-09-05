package exort.api.http.comment.service;


import exort.api.http.comment.entity.Comment;
import exort.api.http.comment.entity.CommentDeleteInfo;
import exort.api.http.comment.entity.CommentNeededInfo;
import exort.api.http.common.entity.ApiResponse;

import java.util.List;

public interface CommentService {
    public ApiResponse<Comment> createComment(CommentNeededInfo commentNeededInfo);

    public ApiResponse<Comment> createReply(String commentedId, CommentNeededInfo commentNeededInfo);

    public ApiResponse<Comment> deleteComment(CommentDeleteInfo commentDeleteInfo);

    public ApiResponse<List<Comment>> getOneArticleAllComments(String articleId);

    public ApiResponse<List<Comment>> getOneActivityAllComments(String activityId);

    public ApiResponse<Comment> getOneComment(String commentId);

    public ApiResponse<List<Comment>> getUserComments(int userId);

}
