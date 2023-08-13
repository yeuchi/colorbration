package com.ctyeung.colorbration

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.ctyeung.colorbration.data.SpectralReflectance
import com.ctyeung.colorbration.data.ref.LightSources
import com.ctyeung.colorbration.ui.theme.ColorbrationTheme
import com.ctyeung.colorbration.viewmodels.SourceEvent
import com.ctyeung.colorbration.viewmodels.SourceViewModel
import dagger.hilt.android.AndroidEntryPoint

/*
 * TODO DRY (don't repeat yourself)
 *  Refactor common code between Main, Reflective and Illuminant Activites
 */
@AndroidEntryPoint
class IlluminantActivity : ComponentActivity() {
    private val viewModel: SourceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorbrationTheme {
                ComposeScreen(null)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.event.observe(this, Observer(::onViewModelEvent))
    }

    private fun onViewModelEvent(event: SourceEvent) {
        setContent {
            ColorbrationTheme {
                when (event) {
                    is SourceEvent.InProgress -> ComposeSpinner()
                    is SourceEvent.Success -> ComposeScreen(event.data)
                    is SourceEvent.Error -> ComposeError(error = event.msg)
                }
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun ComposeScreen(data: SpectralReflectance?) {
        Scaffold(
            bottomBar = { BottomNavigation(BottomNavItem.Illuminant.screen_route, this) },
        ) {
            data?.apply {
                Render(this, it)
            }
        }
    }

    @Composable
    private fun Render(data: SpectralReflectance, paddingValues: PaddingValues) {
        Box(
            // in this column we are specifying modifier
            // and aligning it center of the screen on below lines.
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            ComposeCanvas(data)
            ComposeRadio()
        }
    }

    @Composable
    private fun ComposeCanvas(data: SpectralReflectance) {
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
                val observerPath = Path().let {

                    // initial position
                    it.moveTo(paddingX, size.height - paddingY)

                    // draw curve topology
                    for (i in 0 until spectralReflectance.percent.size) {
                        val percent = spectralReflectance.percent[i]
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
                    style = Fill,
                    alpha = .5f
                )
            }
            createPath(data, Color.Red)
        }
    }

    @Composable
    private fun ComposeRadio() {
        /*
         * https://www.geeksforgeeks.org/radiobuttons-in-android-using-jetpack-compose/
         */
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
        ) {
            val radioOptions =
                LightSources.let {
                    listOf(
                        it.ILLUMINANT_A,
                        it.ILLUMINANT_B,
                        it.ILLUMINANT_C,
                        it.ILLUMINANT_D50,
                        it.ILLUMINANT_D65,
                    )
                }
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        // using modifier to add max
                        // width to our radio button.
                        .align(Alignment.End)
                        .width(200.dp)
                        // below method is use to add
                        // selectable to our radio button.
                        .selectable(
                            // this method is called when
                            // radio button is selected.
                            selected = (text == viewModel.selectedIlluminant),
                            // below method is called on
                            // clicking of radio button.
                            onClick = {
                                viewModel.updateBy(text)
                            }
                        )
                        // below line is use to add
                        // padding to radio button.
                        .padding(horizontal = 10.dp),
                ) {
                    // val context = ContextAmbient.current

                    // below line is use to
                    // generate radio button
                    RadioButton(
                        // inside this method we are
                        // adding selected with a option.
                        selected = (text == viewModel.selectedIlluminant),
                        onClick = {
                            viewModel.updateBy(text)

                            // after clicking a radio button
                            // we are displaying a toast message.
                            //Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                        }
                    )
                    // below line is use to add
                    // text to our radio buttons.
                    Text(
                        text = text,
                        modifier = Modifier.padding(15.dp)
                    )
                }
            }
        }
    }
}
