package com.ctyeung.colorbration

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.ctyeung.colorbration.ui.theme.ColorbrationTheme
import com.ctyeung.colorbration.viewmodels.ChromaticEvent
import com.ctyeung.colorbration.viewmodels.MainViewModel
import com.ctyeung.colorbration.viewmodels.ObserverEvent
import dagger.hilt.android.AndroidEntryPoint

/*
 * TODO Render standard observer / configuration
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@MainActivity, SpectralActivity::class.java))
        setContent {
            ColorbrationTheme {
                ComposeScreen(emptyList())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.event.observe(this, Observer(::onViewModelEvent))
    }

    fun onViewModelEvent(event: ObserverEvent) {
        setContent {
            ColorbrationTheme {
                when(event) {
                    is ObserverEvent.InProgress -> ComposeSpinner()
                    is ObserverEvent.Success -> ComposeScreen(event.points)
                    is ObserverEvent.Error -> ComposeError(error = event.msg)
                    else -> {}
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
        Column(
            // in this column we are specifying modifier
            // and aligning it center of the screen on below lines.
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ComposeCanvas(points)
        }
    }

    @Composable
    private fun ComposeCanvas(points:List<PointF>) {
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
