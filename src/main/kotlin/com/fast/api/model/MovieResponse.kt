package com.fast.api.model


import com.fasterxml.jackson.annotation.JsonProperty

data class MovieResponse(
    @JsonProperty("actors")
    val actors: String,
    @JsonProperty("awards")
    val awards: String,
    @JsonProperty("box_office")
    val boxOffice: String,
    @JsonProperty("country")
    val country: String,
    @JsonProperty("dvd")
    val dVD: String,
    @JsonProperty("director")
    val director: String,
    @JsonProperty("genre")
    val genre: String,
    @JsonProperty("imdb_id")
    val imdbID: String,
    @JsonProperty("imdb_rating")
    val imdbRating: String,
    @JsonProperty("imdb_votes")
    val imdbVotes: String,
    @JsonProperty("language")
    val language: String,
    @JsonProperty("metascore")
    val metascore: String,
    @JsonProperty("plot")
    val plot: String,
    @JsonProperty("poster")
    val poster: String,
    @JsonProperty("production")
    val production: String,
    @JsonProperty("rated")
    val rated: String,
    @JsonProperty("rating")
    val ratings: List<Rating>,
    @JsonProperty("released")
    val released: String,
    @JsonProperty("response")
    val response: String,
    @JsonProperty("runtime")
    val runtime: String,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("type")
    val type: String,
    @JsonProperty("website")
    val website: String,
    @JsonProperty("writer")
    val writer: String,
    @JsonProperty("year")
    val year: String
) {
    data class Rating(
        @JsonProperty("source")
        val source: String,
        @JsonProperty("value")
        val value: String
    )
}