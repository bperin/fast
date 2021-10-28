package com.fast.api.repo

import com.fast.api.model.ShowTime
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface ShowTimesRepo : CrudRepository<ShowTime, UUID> {

}