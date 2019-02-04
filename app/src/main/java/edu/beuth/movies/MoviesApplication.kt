package edu.beuth.movies

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["edu.beuth.movies"])
class MoviesApplication

fun main(args: Array<String>) {
    runApplication<MoviesApplication>(*args)
}