package exort.apiserver.controller;

import exort.api.http.comment.entity.Comment;
import exort.api.http.comment.entity.CommentDeleteInfo;
import exort.api.http.comment.entity.CommentNeededInfo;
import exort.api.http.comment.service.CommentService;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.POST, value = "/comments")
    public ApiResponse<Comment> createComment(@RequestBody CommentNeededInfo commentNeededInfo) {
        return commentService.createComment(commentNeededInfo);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/comments/{commentedId}/replies")
    public ApiResponse<Comment> createReply(@PathVariable(value = "commentedId") String commentedId, @RequestBody CommentNeededInfo commentNeededInfo) {
        return commentService.createReply(commentedId, commentNeededInfo);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/comments")
    public ApiResponse<Comment> deleteComment(@RequestBody CommentDeleteInfo commentDeleteInfo) {
        return commentService.deleteComment(commentDeleteInfo);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/articles/{articleId}/comments")
    public ApiResponse<List<Comment>> getOneArticleAllComments(@PathVariable(value = "articleId") String articleId) {
        return commentService.getOneArticleAllComments(articleId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/activities/{activityId}/comments")
    public ApiResponse<List<Comment>> getOneActivityAllComments(@PathVariable(value = "activityId") String activityId) {
        return commentService.getOneActivityAllComments(activityId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comments/{commentId}")
    public ApiResponse<Comment> getOneComment(@PathVariable(value = "commentId") String commentId) {
        return commentService.getOneComment(commentId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/comments")
    public ApiResponse<List<Comment>> getUserComments(@PathVariable(value = "userId") int userId) {
        return commentService.getUserComments(userId);
    }
}
