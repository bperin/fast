package com.fast.api.service

import com.fast.api.repo.UsersRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsersService {

    @Autowired
    lateinit var usersRepo: UsersRepo


    fun getUser(id: UUID) {
        
    }
}
