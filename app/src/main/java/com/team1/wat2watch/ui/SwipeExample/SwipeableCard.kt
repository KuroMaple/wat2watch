package com.team1.wat2watch.ui.SwipeExample

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import utils.Movie
import com.team1.wat2watch.ui.SwipeExample.Constants.TOP_CARD_INDEX
import com.team1.wat2watch.ui.SwipeExample.Constants.TOP_Z_INDEX
import com.team1.wat2watch.ui.SwipeExample.Constants.cardHeight
import com.team1.wat2watch.ui.SwipeExample.Constants.paddingOffset
import com.team1.wat2watch.ui.match.MatchViewModel
import com.team1.wat2watch.ui.match.card.MovieCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import wat2watch.utils.FirestoreHelper.addToWatchList
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun SwipeableCard(
    dataSource: List<Movie>,
    matchViewModel: MatchViewModel
) {

    val visibleCard: Int = StrictMath.min(3, dataSource.size)
    val scope = rememberCoroutineScope()
    val firstCard = remember { mutableIntStateOf(0) }
    val offset: Animatable<Offset, AnimationVector2D> = remember {
        Animatable(
            Offset(0f, 0f),
            Offset.VectorConverter
        )
    }
    val animationSpec: FiniteAnimationSpec<Offset> = tween(
        durationMillis = 150,
        easing = LinearEasing
    )
    val undoSwipe = matchViewModel.undoSwipe.collectAsState()

    fun rearrangeBackward() {
        if(firstCard.intValue == 0){
            return
        }
        if (firstCard.intValue == -(dataSource.size - 1)) {
            firstCard.intValue = dataSource.size - 1
        } else firstCard.intValue--
    }

    if (undoSwipe.value) {
        LaunchedEffect (Unit){
            scope.launch {
                rearrangeBackward()
                offset.animateTo(
                    targetValue = Offset(-600f, 600f),
                    animationSpec = snap()
                )
                offset.animateTo(
                    targetValue = Offset(0f, 0f),
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            }
            matchViewModel.resetTriggerUndo()
        }
    }

    fun rearrangeForward() {
        if (firstCard.intValue == dataSource.size - 1) {
            firstCard.intValue = 0
        } else firstCard.intValue++
    }



    fun onSwipeRight(movie: Movie) {
        println("First Card: ${firstCard.intValue}")
        val targetMovie = dataSource[firstCard.intValue]
        addToWatchList(
            movieId = targetMovie.id.toString(),
            title = targetMovie.title,
            release_date = targetMovie.release_date,
            poster_path = targetMovie.poster_path ?: "",
            overview = targetMovie.overview,
            adult = targetMovie.adult
        )
    }

    fun onSwipeLeft(movie: Movie) {
        println("Swiped Left on card ${movie.title}")
    }

    Box(Modifier.fillMaxWidth()) {
        repeat(visibleCard) { index ->
            val zIndex = TOP_Z_INDEX - index
            val scale = calculateScale(index)
            val offsetY = calculateOffset(index)
            val curMovie = dataSource[firstCard.intValue]
            println("curMovie: $curMovie")
            val cardModifier =
                makeCardModifier(
                    scope = scope,
                    cardIndex = index,
                    scale = scale,
                    zIndex = zIndex,
                    offsetY = offsetY,
                    offset = offset,
                    rearrangeForward = { rearrangeForward() },
                    rearrangeBackward = { rearrangeBackward() },
                    onSwipeRight = { onSwipeRight(curMovie) },
                    onSwipeLeft = { onSwipeLeft(curMovie) },
                    animationSpec = animationSpec
                )

            MovieCard(
                modifier = cardModifier,
                movie = dataSource[firstCard.intValue + index],
                cardIndex = index
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("ModifierFactoryExtensionFunction")
fun makeCardModifier(
    scope: CoroutineScope,
    cardIndex: Int,
    scale: Float,
    zIndex: Float,
    offset: Animatable<Offset, AnimationVector2D>,
    animationSpec: FiniteAnimationSpec<Offset>,
    offsetY: Int,
    rearrangeForward: () -> Unit,
    rearrangeBackward: () -> Unit,
    onSwipeRight: () -> Unit,
    onSwipeLeft: () -> Unit
): Modifier {

    return if (cardIndex > TOP_CARD_INDEX) Modifier
        .graphicsLayer {
            translationY =
                if (offset.value.y != 0f) min(
                    abs(offset.value.y),
                    paddingOffset * 1.1f
                ) else 0f
            scaleX = if (offset.value.y != 0f) {
                min(scale + (abs(offset.value.y) / 1000), 1.06f - (cardIndex * 0.03f))
            } else scale
            scaleY = if (offset.value.y != 0f) {
                min(scale + (abs(offset.value.y) / 1000), 1.06f - (cardIndex * 0.03f))
            } else scale
        }
        .scale(scale)
        .offset { IntOffset(0, offsetY) }
        .zIndex(zIndex)
        .fillMaxWidth()
        .height(cardHeight)
    else Modifier
        .scale(scale)
        .offset { IntOffset(offset.value.x.roundToInt(), offset.value.y.roundToInt()) }
        .zIndex(zIndex)
        .fillMaxWidth()
        .height(cardHeight)
//        .pointerInput(Unit) {
//            detectTapGestures(
//                onTap = {
//                    scope.launch {
//                        rearrangeBackward()
//                        offset.animateTo(
//                            targetValue = Offset(-600f, 600f),
//                            animationSpec = snap()
//                        )
//                        offset.animateTo(
//                            targetValue = Offset(0f, 0f),
//                            animationSpec = tween(
//                                durationMillis = 300,
//                                easing = LinearEasing
//                            )
//                        )
//                    }
//                }
//            )
//        }
        .pointerInput(Unit) {
            detectDragGestures { change, _ ->
                val dragOffset = Offset(
                    offset.value.x + change.positionChange().x,
                    offset.value.y + change.positionChange().y
                )
                scope.launch {
                    offset.snapTo(dragOffset)
                    if (change.positionChange() != Offset.Zero) change.consume()
                    val x = when {

                        offset.value.x > 250 -> size.width.toFloat()
                        offset.value.x < -250 -> -size.width.toFloat()
                        else -> 0f
                    }
                    val y = when {

                        offset.value.y > 250 -> size.height.toFloat()
                        offset.value.y < -250 -> -size.height.toFloat()
                        else -> 0f
                    }

                    offset.animateTo(
                        targetValue = Offset(x, y),
                        animationSpec = animationSpec
                    )
                    if (abs(offset.value.x) == size.width.toFloat() || abs(offset.value.y) == size.height.toFloat()) {

                        when (x) {
                            size.width.toFloat() -> {
                                onSwipeRight()
                                rearrangeForward()
                            }
                            -size.width.toFloat() -> {
                                onSwipeLeft()
                                rearrangeForward()
                            }
                        }

                        offset.animateTo(
                            targetValue = Offset(0f, 0f),
                            animationSpec = snap()
                        )
                    }


                }
            }
        }
}

private fun calculateScale(idx: Int): Float {
    return when (idx) {
        1 -> 0.97f
        2 -> 0.94f
        else -> 1f
    }
}

private fun calculateOffset(idx: Int): Int {
    return when (idx) {
        1 -> -(paddingOffset * idx * 1.1).toInt()
        2 -> -(paddingOffset * idx * 1.1).toInt()
        else -> -paddingOffset.toInt()
    }
}

@Preview
@Composable
fun SwipeableCardPreview(){
    val movieList = listOf(
        Movie(
            title = "Inception",
            overview = "A mind-bending thriller about dream thieves.",
            poster_path = "inception_poster_url",
            adult = false,
            release_date = "2010-07-16",
            id = 1
        ),
        Movie(
            title = "The Dark Knight",
            overview = "Batman faces his most dangerous foe, the Joker.",
            poster_path = "dark_knight_poster_url",
            adult = true,
            release_date = "2008-07-18",
            id = 2
        ),
        Movie(
            title = "The Matrix",
            overview = "A hacker discovers the shocking truth about reality.",
            poster_path = "matrix_poster_url",
            adult = true,
            release_date = "1999-03-31",
            id = 3
        ),
        Movie(
            title = "Toy Story",
            overview = "A group of toys come to life when their owner isn't around.",
            poster_path = "toy_story_poster_url",
            adult = false,
            release_date = "1995-11-22",
            id = 4
        ),
        Movie(
            title = "The Shawshank Redemption",
            overview = "Two men form an unlikely friendship in prison.",
            poster_path = "shawshank_poster_url",
            adult = true,
            release_date = "1994-09-23",
            id = 5
        )
    )

    SwipeableCard(
        dataSource = movieList,
        matchViewModel = MatchViewModel()
    )
}