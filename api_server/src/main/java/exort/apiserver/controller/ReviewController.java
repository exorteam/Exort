package exort.apiserver.controller;

import exort.api.http.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/applications")
public class ReviewController {

    @Autowired
    private ReviewService rs;

}
