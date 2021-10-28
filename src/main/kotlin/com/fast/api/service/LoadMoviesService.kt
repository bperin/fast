package com.fast.api.service

import com.fast.api.model.Movie
import com.fast.api.repo.MoviesRepo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.springframework.beans.factory.annotation.Autowired
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
 */
@Service
class LoadMoviesService {

    @Autowired
    lateinit var moviesRepo: MoviesRepo

    @EventListener
    fun onApplicationEvent(event: ContextRefreshedEvent?) {

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
    }
}