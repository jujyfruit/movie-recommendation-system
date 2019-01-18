package edu.beuth.movies;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = edu.beuth.movies.services.recommender.pb.MovieRecommenderGrpc.MovieRecommenderBlockingStub.class)
public class MoviesApplicationTests {

    @Test
    void contextLoads() {
    }
}
