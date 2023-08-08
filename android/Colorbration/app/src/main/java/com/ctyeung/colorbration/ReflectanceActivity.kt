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
    protected var paddingX: Float = 0f
    protected var paddingY: Float = 0f
    protected var width: Float = 0f
    protected var height: Float = 0f
    protected var unit100X: Double = 0.0
    protected var unit25Y: Double = 0.0

    protected var iconPaddingY: Float = 0f

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

    private fun convertCoord(x: Float, y: Float): MyPoint? {
        when {
            x < paddingX -> {
                // do nothing
            }

            x > (width - paddingX) -> {
                // do nothing
            }

            else -> {
                val wavelength = 400 + (x - paddingX) / unit100X * 100
                return MyPoint(wavelength, convertCoordY(y))
            }
        }
        return null
    }

    private fun convertCoordY(y: Float): Double {
        when {
            y < paddingY -> {
                return 1.0
            }

            y > (height - paddingY) -> {
                return 0.0
            }

            else -> {
                /*
                 * TODO - this needs to be fixed
                 */
                return 1.0 - (y - paddingY) / unit25Y * .20
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onViewModelEvent(event: SpectralEvent) {
        setContent {
            ColorbrationTheme {
                // A surface container using the 'background' color from the theme
                Column(modifier = Modifier.pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            viewModel.apply {
                                convertCoord(it.rawX, it.rawY)?.let {
                                    add(it)
                                }
                            }
                        }

                        MotionEvent.ACTION_MOVE -> {
                            viewModel.apply {
                                convertCoord(it.rawX, it.rawY)?.let {
                                    add(it)
                                }
                            }
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
            width = size.width
            height = size.height
            paddingX = (size.width / 20.0).toFloat()
            paddingY = (size.height / 20.0).toFloat()
            val paint = Paint().asFrameworkPaint().apply {
                color = 0xff000000.toInt()
                textAlign = android.graphics.Paint.Align.LEFT
                textSize = 40f
            }

            // y-axis
            unit25Y = (size.height - 2 * paddingX) / 4.0
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
            unit100X = (size.width - 2 * paddingX) / 3.0
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

            val one_percent = (size.height - 2 * paddingY) / 100.0
            val ten_nm = (size.width - 2 * paddingX) / 30.0

            fun createPath(spectralReflectance: SpectralReflectance, color: Color) {
                /*
                 * TODO step through more points for smoother lines - use cubic spline interpolation
                 */
                val observerPath = androidx.compose.ui.graphics.Path().let {

                    // initial position
                    it.moveTo(paddingX, size.height - paddingY)

                    // draw curve topology
                    var i = 0
                    for (knot in spectralReflectance.knots) {
                        val percent = (1.0 - knot.second) * 100.0
                        val ypos = percent * one_percent + paddingY
                        val xpos = ten_nm * (knot.first - 400)/10 + paddingX
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