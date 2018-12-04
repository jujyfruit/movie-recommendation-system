package edu.beuth.movies.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name="users")
class User(
        @Id
        val id: Long
)

