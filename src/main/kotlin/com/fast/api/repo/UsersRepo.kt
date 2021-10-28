package com.fast.api.repo

import com.fast.api.model.Cinema
import com.fast.api.model.RefreshToken
import com.fast.api.model.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UsersRepo : CrudRepository<User, UUID> {
    
}