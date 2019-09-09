package exort.comment.controller;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.comment.entity.Comment;
import exort.comment.entity.CommentDeleteInfo;
import exort.comment.entity.CommentNeededInfo;
import exort.comment.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

        // check是否有次文章或者活动
//        String commentedId=commentNeededInfo.getCommentedId();
//        if(!commentService.existComment(commentedId)){
//            throw new ApiError(404 ,"notFound","没有找到被评论对象");
//        }

        if (!commentNeededInfo.getType().equals("article") && !commentNeededInfo.getType().equals("activity")) {
            throw new ApiError(400, "invalidType", "被评论对象类型非法");
        }

        return new ApiResponse<Comment>(commentService.createComment(commentNeededInfo.getUserId(),commentNeededInfo.getUserName(), commentNeededInfo.getType(), commentNeededInfo.getCommentedId(), commentNeededInfo.getContent()));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/comments/{commentedId}/replies")
    public ApiResponse<Comment> createReply(@PathVariable(value = "commentedId") String commentedId, @RequestBody CommentNeededInfo commentNeededInfo) {
        if (!commentService.existComment(commentedId)) {
            throw new ApiError(404, "notFound", "没有找到被评论对象");
        }

        Comment comment = commentService.createComment(commentNeededInfo.getUserId(),commentNeededInfo.getUserName(), "comment", commentedId, commentNeededInfo.getContent());

        return new ApiResponse<Comment>(comment);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/comments")
    public ApiResponse<Comment> deleteComment(@RequestBody CommentDeleteInfo commentDeleteInfo) {
        String id = commentDeleteInfo.getId();

        if (!commentService.existComment(id)) {
            throw new ApiError(404, "notFound", "没有此评论");
        }

        Comment comment = commentService.deleteComment(id);

        commentService.deleteReply(comment.getCommentedId(), comment.getId());

        return new ApiResponse<Comment>(comment);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/articles/{articleId}/comments")
    public ApiResponse<List<Comment>> getOneArticleAllComments(@PathVariable(value = "articleId") String articleId) {

        List<Comment> comments = commentService.getCommentsByTypeAndCommentedId("article", articleId);

        List<Comment> replies = new ArrayList<>();
        for (Comment comment : comments) {
            replies.addAll(commentService.getAllReplies(comment.getId()));
        }
        comments.addAll(replies);

        return new ApiResponse<List<Comment>>(comments);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/activities/{activityId}/comments")
    public ApiResponse<List<Comment>> getOneActivityAllComments(@PathVariable(value = "activityId") String activityId) {

        List<Comment> comments = commentService.getCommentsByTypeAndCommentedId("activity", activityId);

        List<Comment> replies = new ArrayList<>();
        for (Comment comment : comments) {
            replies.addAll(commentService.getAllReplies(comment.getId()));
        }
        comments.addAll(replies);

        return new ApiResponse<List<Comment>>(comments);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comments/{commentId}")
    public ApiResponse<Comment> getOneComment(@PathVariable(value = "commentId") String commentId) {

        if (!commentService.existComment(commentId)) {
            throw new ApiError(404, "notFound", "不存在此评论");
        }

        return new ApiResponse<Comment>(commentService.getOneComment(commentId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/comments")
    public ApiResponse<List<Comment>> getUserComments(@PathVariable(value = "userId") int userId) {
        return new ApiResponse<List<Comment>>(commentService.getCommentsByUserId(userId));
    }
}
