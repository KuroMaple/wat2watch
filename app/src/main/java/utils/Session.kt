package utils

import android.util.Log
import kotlin.random.Random

data class Session(
    val isActive: Boolean,
    val users: List<String>,
    val swipes: Map<String, Map<String, Any>>,
    val countGoal: Int,
    val sessionId: String,
    val sessionAlias: String,
    val selectedMovie: Movie?
)

private val animalList = listOf(
    "walrus", "lion", "horse", "tiger", "elephant", "panda", "fox", "giraffe", "dolphin", "rabbit"
)

private val actionList = listOf(
    "eats", "cooks", "bakes", "grills", "chews", "devours", "gobbles", "licks", "savors", "slurps"
)

private val foodList = listOf(
    "pizza", "pasta", "sushi", "burger", "taco", "noodles", "salad", "burrito", "cheesecake", "donut"
)

/*
* Creates a more user-friendly user id for joining purposes
* */
fun generateSessionIdAlias(sessionId: String): String {
    // Generate random indexes for each list
    val index1 = Random.nextInt(animalList.size)
    val index2 = Random.nextInt(actionList.size)
    val index3 = Random.nextInt(foodList.size)

    Log.d("generateSessionIdAlias", "index1: $index1, index2: $index2, index3: $index3")
    return "${animalList[index1]}-${actionList[index2]}-${foodList[index3]}"
}




