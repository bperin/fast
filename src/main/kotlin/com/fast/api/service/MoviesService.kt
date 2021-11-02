package com.fast.api.service

import com.fast.api.repo.MoviesRepo
import com.fast.api.repo.UsersRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MoviesService {

    @Autowired
    lateinit var moviesRepo: MoviesRepo

}
