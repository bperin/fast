package com.fast.api.service

import com.fast.api.repo.CinemasRepo
import com.fast.api.repo.UsersRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CinemasService {

    @Autowired
    lateinit var cinemasRepo: CinemasRepo

}
