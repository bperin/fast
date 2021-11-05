package com.fast.api.ext

import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration
import org.modelmapper.convention.MatchingStrategies
import java.util.*

class MapperDto() : ModelMapper() {
    init {
        configuration.matchingStrategy = MatchingStrategies.STRICT
        configuration.fieldAccessLevel = Configuration.AccessLevel.PRIVATE
        configuration.isFieldMatchingEnabled = true
        configuration.isSkipNullEnabled = true
    }
}

object Mapper {
    val mapper = MapperDto()
    inline fun <S, reified T> convert(source: S): T = mapper.map(source, T::class.java)
    inline fun <S, reified T> convert(source: S, target: T): Unit = mapper.map(source, target)
}

fun String.toUUID(): UUID {
    return UUID.fromString(this)
}

