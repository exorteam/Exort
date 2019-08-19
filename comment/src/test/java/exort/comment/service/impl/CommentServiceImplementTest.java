package exort.comment.service.impl;

import exort.comment.entity.Comment;
import exort.comment.repository.CommentRepository;
import exort.comment.service.CommentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceImplementTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Before
    public void before() {
        commentRepository.deleteAll();
    }

    @After
    public void after() {
        commentRepository.deleteAll();
    }

    @Test
    public void createComment() {
        int userId = 1;
        String type = "asd";
        String commentedId = "asdf";
        String content = "asddf";

        Comment comment = commentService.createComment(userId, type, commentedId, content);

        Calendar cal1=Calendar.getInstance();
        cal1.setTime(comment.getTime());

        Calendar cal2=Calendar.getInstance();
        cal2.setTime(new Date());

        assertEquals(cal2.get(Calendar.DAY_OF_MONTH),cal1.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void deleteComment() {
        int userId = 1;
        String type = "asd";
        String commentedId = "asdf";
        String content = "asddf";

        Comment comment = commentService.createComment(userId, type, commentedId, content);

        int before=commentRepository.findAll().size();

        commentService.deleteComment(comment.getId());

        int after=commentRepository.findAll().size();

        assertEquals(before-1,after);

    }

    @Test
    public void getCommentsByTypeAndCommentedId() {
        int userId = 1;
        String type = "asd";
        String commentedId = "asdf";
        String content = "asddf";

        Comment comment= commentService.createComment(userId, type, commentedId, content);

        String thetype=comment.getType();
        String thecommentedId=comment.getCommentedId();

        Comment comment1= commentService.getCommentsByTypeAndCommentedId(thetype,thecommentedId).get(0);

        assertEquals(comment,comment1);
    }

    @Test
    public void getOneComment() {
        int userId = 1;
        String type = "asd";
        String commentedId = "asdf";
        String content = "asddf";

        Comment comment= commentService.createComment(userId, type, commentedId, content);

        String id=comment.getId();

        Comment theComment=commentService.getOneComment(id);

        assertEquals(userId,theComment.getUserId());
    }

    @Test
    public void getCommentsByUserId() {
        int userId = 1;
        String type = "asd";
        String commentedId = "asdf";
        String content = "asddf";

        commentService.createComment(userId, type, commentedId, content);
        type = "sd";
        commentService.createComment(userId, type, commentedId, content);

        assertEquals(2,commentService.getCommentsByUserId(1).size());
    }
}