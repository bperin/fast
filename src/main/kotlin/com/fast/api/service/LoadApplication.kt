package com.fast.api.service

import com.fast.api.model.Movie
import com.fast.api.repo.MoviesRepo
import com.fast.api.repo.UsersRepo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.lang.reflect.Type
import java.nio.file.Files


/**
 * On an app start check if we have any movies in the db
 * if not add the ones from the json list ideally a migration
 * script would be best
 * Also create the primary owner from env vars
 */
@Service
class LoadApplication {

    @Autowired
    lateinit var moviesRepo: MoviesRepo

    @Autowired
    lateinit var usersRepo: UsersRepo

    @Autowired
    lateinit var imdbService: ImdbService

    @Value("\${primary.owner.email}")
    lateinit var primaryOwnerEmail: String


    @Value("\${primary.owner.password}")
    lateinit var primaryOwnerPassword: String

    @EventListener
    fun onApplicationEvent(event: ContextRefreshedEvent?) {

        if (usersRepo.findAll().toList().isNullOrEmpty()) {

        }
        if (moviesRepo.findAll().toList().isNullOrEmpty()) {

            moviesRepo.deleteAll()

            val resource = ClassPathResource("movies.json").file
            val text = String(Files.readAllBytes(resource.toPath()))

            val userListType: Type = object : TypeToken<MutableList<Movie>>() {}.type

            val movies = Gson().fromJson(text, userListType) as MutableList<Movie>

            movies.forEach {
                moviesRepo.save(it)
            }
        }
        /**
         *
         */
        fun getMovieDetails(movie: Movie) {
            val response = imdbService.apiClient.getMovie(movie.imdbId).execute()

            if (response.isSuccessful) {
                println(response.body())
            } else {
                println(response.errorBody().toString())
            }
        }
    }
}