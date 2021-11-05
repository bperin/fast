package com.fast.api.service

import com.fast.api.ext.toUUID
import com.fast.api.model.User
import com.fast.api.repo.UsersRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsersService {

    @Autowired
    lateinit var usersRepo: UsersRepo

    /**
     * Gets and owner from repo if exists
     */
    fun getOwner(id: String): User? {
        return usersRepo.findOwnerById(id.toUUID())
    }

    /**
     * Gets and owner from repo if exists
     */
    fun getViewer(id: String): User? {
        return usersRepo.findViewById(id.toUUID())
    }
}
