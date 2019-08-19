package exort.comment.repository;

import exort.comment.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository  extends MongoRepository<Comment,String> {
    public List<Comment> findAllByTypeAndCommentedId(String type,String commentedId);
    public List<Comment> findAllByUserId(int userId);
}
