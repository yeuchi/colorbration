package com.ctyeung.colorbration

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.lifecycle.Observer
import com.ctyeung.colorbration.data.math.MyPoint
import com.ctyeung.colorbration.data.BaseSpectralData
import com.ctyeung.colorbration.data.SpectralReflectance
import com.ctyeung.colorbration.ui.theme.ColorbrationTheme
import com.ctyeung.colorbration.viewmodels.ReflectanceViewModel
import com.ctyeung.colorbration.viewmodels.SpectralEvent
import dagger.hilt.android.AndroidEntryPoint


/*
 * TODO DRY (don't repeat yourself)
 *  Refactor common code between Main, Reflective and Illuminant Activites
 */

/*
 * TODO User touch to render spectral curve
 */
@AndroidEntryPoint
class ReflectanceActivity : ComponentActivity() {
    protected val viewModel: ReflectanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorbrationTheme {
                ComposeScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.event.observe(this, Observer(::onViewModelEvent))
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onViewModelEvent(event: SpectralEvent) {
        setContent {
            ColorbrationTheme {
                // A surface container using the 'background' color from the theme
                Column(modifier = Modifier.pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            /* collect down */
                            viewModel.apply {
                                clear()
                                add(MyPoint(it.rawX.toDouble(), it.rawY.toDouble()))
                            }
                        }

                        MotionEvent.ACTION_MOVE -> {
                            /* collect location */
                            viewModel.add(MyPoint(it.rawX.toDouble(), it.rawY.toDouble()))
                        }

                        MotionEvent.ACTION_UP -> {
                            /* end collect -> invoke render update (invalidate) */
                        }

                        else -> false
                    }
                    true
                }) {
                    when (event) {
                        is SpectralEvent.InProgress -> ComposeSpinner()
                        is SpectralEvent.Success -> ComposeScreen(event.curve)
                        is SpectralEvent.Error -> ComposeError(error = event.msg)
                        else -> {}
                    }
                }
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun ComposeScreen(curve: SpectralReflectance? = null) {
        Scaffold(
            bottomBar = { BottomNavigation(BottomNavItem.Reflectance.screen_route, this) },
        ) {
            Render(curve, it)
        }
    }

    @Composable
    private fun Render(curve: SpectralReflectance?, paddingValues: PaddingValues) {
        Column(
            // in this column we are specifying modifier
            // and aligning it center of the screen on below lines.
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            curve?.apply {
                ComposeCanvas(this)
            }
        }
    }

    @Composable
    private fun ComposeCanvas(curve: SpectralReflectance) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val paddingX = (size.width / 20.0).toFloat()
            val paddingY = (size.height / 20.0).toFloat()
            val paint = Paint().asFrameworkPaint().apply {
                color = 0xff000000.toInt()
                textAlign = android.graphics.Paint.Align.LEFT
                textSize = 40f
            }

            // y-axis
            val unit25Y = (size.height - 2 * paddingX) / 4.0
            drawLine(
                start = Offset(x = paddingX, y = 0f),
                end = Offset(x = paddingX, y = size.height - paddingY),
                color = Color.Black,
                strokeWidth = 5F
            )

            for (y in 0..5) {
                val str = "${y * 25}%"
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
                color = Color.Black,
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

            fun createPath(spectralReflectance: SpectralReflectance, color: Color) {
                /*
                 * TODO step through more points for smoother lines - use cubic spline interpolation
                 */
                val observerPath = androidx.compose.ui.graphics.Path().let {

                    // initial position
                    it.moveTo(paddingX, size.height - paddingY)

                    // draw curve topology
                    for (i in 0 until spectralReflectance.percent.size) {
                        val percent = (1.0 - spectralReflectance.percent[i]) * 100.0
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
                    color = color,
                    style = androidx.compose.ui.graphics.drawscope.Fill,
                    alpha = .5f
                )
            }
            /*
             * TODO Replace with calculated sRGB color
             */
            createPath(curve, Color.LightGray)
        }
    }
}