package exort.api.http.comment.service;

import com.google.common.reflect.TypeToken;
import exort.api.http.comment.entity.Comment;
import exort.api.http.comment.entity.CommentDeleteInfo;
import exort.api.http.comment.entity.CommentNeededInfo;
import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceImplement extends RestTemplate implements CommentService {

    @Value("${exort.comment.protocol:http}")
    public void setProtocol(String protocol) {
        super.setProtocol(protocol);
    }

    @Value("${exort.comment.endpoint:localhost}")
    public void setEndpoint(String endpoint) {
        super.setEndpoint(endpoint);
    }

    @Override
    public ApiResponse<Comment> createComment(CommentNeededInfo commentNeededInfo) {
        return request(new TypeToken<Comment>() {
        }, commentNeededInfo, HttpMethod.POST, "/comments");
    }

    @Override
    public ApiResponse<Comment> createReply(String commentedId, CommentNeededInfo commentNeededInfo) {
        return request(new TypeToken<Comment>() {
        }, commentNeededInfo, HttpMethod.POST, "/comments/{commentedId}/replies", commentedId);
    }

    @Override
    public ApiResponse<Comment> deleteComment(CommentDeleteInfo commentDeleteInfo) {
        return request(new TypeToken<Comment>() {
        }, commentDeleteInfo, HttpMethod.DELETE, "/comments");
    }

    @Override
    public ApiResponse<List<Comment>> getOneArticleAllComments(String articleId) {
        return request(new TypeToken<List<Comment>>() {
        }, HttpMethod.GET, "/articles/{articleId}/comments", articleId);
    }

    @Override
    public ApiResponse<List<Comment>> getOneActivityAllComments(String activityId) {
        return request(new TypeToken<List<Comment>>() {
        }, HttpMethod.GET, "/activities/{activityId}/comments", activityId);
    }

    @Override
    public ApiResponse<Comment> getOneComment(String commentId) {
        return request(new TypeToken<Comment>() {
        }, HttpMethod.GET, "/comments/{commentId}", commentId);
    }

    @Override
    public ApiResponse<List<Comment>> getUserComments(int userId) {
        return request(new TypeToken<List<Comment>>() {
        }, HttpMethod.GET, "/users/{userId}/comments", userId);
    }
}
