package com.fast.api.repo

import com.fast.api.model.Cinema
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface CinemasRepo : CrudRepository<Cinema, UUID> {

}