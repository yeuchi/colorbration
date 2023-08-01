package com.ctyeung.colorbration

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.lifecycle.Observer
import com.ctyeung.colorbration.data.SpectralData
import com.ctyeung.colorbration.ui.theme.ColorbrationTheme
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
        //startActivity(Intent(this@MainActivity, SpectralActivity::class.java))
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
                when (event) {
                    is ObserverEvent.InProgress -> ComposeSpinner()
                    is ObserverEvent.Success -> ComposeScreen(event.data)
                    is ObserverEvent.Error -> ComposeError(error = event.msg)
                    else -> {}
                }
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun ComposeScreen(data: List<SpectralData>) {
        Scaffold(
            bottomBar = { BottomNavigation(BottomNavItem.Spectral.screen_route, this) },
        ) {
            Render(data, it)
        }
    }

    @Composable
    private fun Render(data: List<SpectralData>, paddingValues: PaddingValues) {
        Column(
            // in this column we are specifying modifier
            // and aligning it center of the screen on below lines.
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ComposeCanvas(data)
        }
    }

    @Composable
    private fun ComposeCanvas(data: List<SpectralData>) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            val paddingX = (size.width / 20.0).toFloat()
            val paddingY = (size.height / 20.0).toFloat()
            val paint = Paint().asFrameworkPaint().apply {
                color = 0xff525ce6.toInt()
                textAlign = android.graphics.Paint.Align.LEFT
                textSize = 40f
            }

            // y-axis
            val unit25Y = (size.height - 2 * paddingX) / 4.0
            drawLine(
                start = Offset(x = paddingX, y = 0f),
                end = Offset(x = paddingX, y = size.height - paddingY),
                color = Color.Blue,
                strokeWidth = 5F
            )

            for (y in 0..5) {
                val str = "${y * 0.5}"
                val xpos = 5f
                val ypos = (unit25Y * (5 - y)).toFloat()
                drawIntoCanvas {
                    it.nativeCanvas.drawText(str, xpos, ypos, paint)
                }
            }

            // x-axis
            val unit100X = (size.width - 2 * paddingX) / 3.0
            drawLine(
                start = Offset(x = paddingX, y = size.height - paddingY),
                end = Offset(x = size.width, y = size.height - paddingY),
                color = Color.Red,
                strokeWidth = 5F
            )
            for (x in 0 until 4) {
                val xx = x + 4
                val str = "${xx * 100}nm"
                val xpos = (unit100X * x + paddingX).toFloat()
                val ypos = (size.height).toFloat()
                drawIntoCanvas {
                    it.nativeCanvas.drawText(str, xpos, ypos, paint)
                }
            }

            val one_percent = (size.height - 2 * paddingY) / 200.0
            val ten_nm = (size.width - 2 * paddingX) / 30.0

            fun createPath(spectralData: SpectralData, colors:List<Color>) {
                val observerPath = androidx.compose.ui.graphics.Path().let {

                    // initial position
                    it.moveTo(paddingX, size.height - paddingY)

                    // draw curve topology
                    for (i in 0 until spectralData.percent.size) {
                        val percent = (2.0 - spectralData.percent[i]) * 100.0
                        val ypos = percent * one_percent + paddingY
                        val xpos = ten_nm * i + paddingX
                        it.lineTo(xpos.toFloat(), ypos.toFloat())
                    }

                    // end position
                    it.lineTo(size.width - paddingX, size.height - paddingY)
                    it.close()
                    it
                }
                drawPath(
                    path = observerPath,
                    Brush.verticalGradient(colors = colors),
                    style = Stroke(width = 15f, cap = StrokeCap.Round)
                )
            }
            createPath(data[2], listOf(Color.Blue, Color.Blue))
            createPath(data[1], listOf(Color.Green, Color.Green))
            createPath(data[0], listOf(Color.Red, Color.Red))
        }
    }
}
