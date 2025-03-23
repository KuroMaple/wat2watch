package utils

import com.team1.wat2watch.BuildConfig

data class Movie(
    val id: Int = 0,
    val title: String = "",
    val release_date: String = "",
    val poster_path: String? = "",
    val overview: String = "",
    val adult: Boolean = false,
    val addedOn: String = "",
    // Todo: Fetch run time seperately for displaying purposes in future
) {
    fun getFullImageUrl(): String {
        // Build the full image URL using the base URL from BuildConfig otherwise return the placeholder URL
        return poster_path?.let { "${BuildConfig.IMAGE_BASE_URL}$it" } ?:
        "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg?20200913095930"
    }
}