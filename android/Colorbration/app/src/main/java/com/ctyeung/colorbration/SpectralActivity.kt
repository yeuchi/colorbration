package com.ctyeung.colorbration

import android.graphics.Point
import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import com.ctyeung.colorbration.ui.theme.ColorbrationTheme

class SpectralActivity : ComponentActivity() {
    var touchPoints = ArrayList<PointF>()
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorbrationTheme {
                // A surface container using the 'background' color from the theme
                Column(modifier = Modifier.pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            /* collect down */
                            touchPoints.apply {
                                clear()
                                add(PointF(it.rawX, it.rawY))
                            }
                        }
                        MotionEvent.ACTION_MOVE -> {
                            /* collect location */
                            touchPoints.add(PointF(it.rawX, it.rawY))
                        }
                        MotionEvent.ACTION_UP -> {
                            /* end collect -> invoke render update (invalidate) */
                        }
                        else ->  false
                    }
                    true
                }) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Text(text = "Spectral Integration")
                        composeCanvas()
                    }
                }
            }
        }
    }

    @Composable
    private fun composeCanvas() {
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