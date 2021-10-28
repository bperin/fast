package com.fast.api.repo

import com.fast.api.model.Movie
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface MoviesRepo : CrudRepository<Movie, UUID> {

}