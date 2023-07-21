package com.ctyeung.colorbration

import android.annotation.SuppressLint
import android.graphics.Point
import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.ctyeung.colorbration.ui.theme.ColorbrationTheme
import com.ctyeung.colorbration.viewmodels.SpectralEvent
import com.ctyeung.colorbration.viewmodels.SpectralViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpectralActivity : ComponentActivity() {
    val viewModel : SpectralViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeScreen(emptyList())
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.event.observe(this, Observer(::onViewModelEvent))
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onViewModelEvent(event:SpectralEvent) {
        setContent {
            ColorbrationTheme {
                // A surface container using the 'background' color from the theme
                Column(modifier = Modifier.pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            /* collect down */
                            viewModel.apply {
                                clear()
                                add(PointF(it.rawX, it.rawY))
                            }
                        }
                        MotionEvent.ACTION_MOVE -> {
                            /* collect location */
                            viewModel.add(PointF(it.rawX, it.rawY))
                        }
                        MotionEvent.ACTION_UP -> {
                            /* end collect -> invoke render update (invalidate) */
                            viewModel.invalidate()
                        }
                        else ->  false
                    }
                    true
                }) {
                    when(event) {
                        is SpectralEvent.InProgress -> ComposeSpinner()
                        is SpectralEvent.Success -> ComposeScreen(event.points)
                        is SpectralEvent.Error -> ComposeError(error = event.msg)
                    }
                }
            }
        }
    }
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun ComposeScreen(points: List<PointF>) {
        Scaffold(
            bottomBar = { BottomNavigation(BottomNavItem.Spectral.screen_route, this) },
            content = { Render(points) }
        )
    }

    @Composable
    private fun Render(points:List<PointF>) {
//        Column(
//            // in this column we are specifying modifier
//            // and aligning it center of the screen on below lines.
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            composeCanvas(points)
//        }
    }

    @Composable
    private fun composeCanvas(points:List<PointF>) {
        Canvas(
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp)
        ) {

            val trianglePath = Path().let {
                it.moveTo(this.size.width * .20f, this.size.height * .77f)
                it.lineTo(this.size.width * .20f, this.size.height * 0.95f)
                it.lineTo(this.size.width * .37f, this.size.height * 0.86f)
                it.close()
                it
            }

            val colors = listOf(Color(0xFF02b8f9), Color(0xFF0277fe))
            drawPath(
                path = trianglePath,
                Brush.verticalGradient(colors = colors),
                style = Stroke(width = 15f, cap = StrokeCap.Round)
            )
        }
    }
}