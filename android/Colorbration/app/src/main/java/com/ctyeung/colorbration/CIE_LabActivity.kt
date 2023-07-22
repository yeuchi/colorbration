package com.ctyeung.colorbration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.ctyeung.colorbration.ui.theme.ColorbrationTheme
import dagger.hilt.android.AndroidEntryPoint

/*
 * TODO Calculate CIE L*a*b* value and render 3D volume
 */
@AndroidEntryPoint
class CIE_LabActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorbrationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(text = "CIE L*a*b*")

                }
            }
        }
    }
}