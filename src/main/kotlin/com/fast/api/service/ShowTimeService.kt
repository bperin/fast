package com.fast.api.service

import com.fast.api.repo.ShowTimesRepo
import com.fast.api.repo.UsersRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShowTimeService {

    @Autowired
    lateinit var showTimesRepo: ShowTimesRepo

}
