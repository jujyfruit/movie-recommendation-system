package edu.beuth.movies;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MoviesApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieApplicationTests {

    @Test
    public void contextLoads() {
        // tries to load the application
    }
}