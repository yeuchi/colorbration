package com.ctyeung.colorbration

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BottomNavigation(currentSelection: String, activity: Activity) {
    val items = listOf(
        BottomNavItem.Spectral,
        BottomNavItem.Tristimulus,
        BottomNavItem.Chromaticity,
        BottomNavItem.CIE_Lab,
        BottomNavItem.sRGB,
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),

                alwaysShowLabel = true,
                selected = currentSelection == item.screen_route,
                onClick = {
                    if (currentSelection != item.screen_route) {
                        //navController.navigate(item.screen_route)
                        when (item.screen_route) {
                            BottomNavItem.Spectral.screen_route -> {
                                activity.startActivity(
                                    Intent(activity, SpectralActivity::class.java)
                                )
                            }

                            BottomNavItem.Tristimulus.screen_route -> {
                                activity.startActivity(
                                    Intent(activity, TristimulusActivity::class.java)
                                )
                            }

                            BottomNavItem.Chromaticity.screen_route -> {
                                activity.startActivity(
                                    Intent(activity, ChromaticityActivity::class.java)
                                )
                            }

                            BottomNavItem.CIE_Lab.screen_route -> {
                                activity.startActivity(
                                    Intent(
                                        activity,
                                        CIE_LabActivity::class.java
                                    )
                                )
                            }

                            BottomNavItem.sRGB.screen_route -> {
                                activity.startActivity(
                                    Intent(activity, HP_sRGBActivity::class.java)
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ComposeError(error: String) {
    Row(
        modifier = Modifier
            .padding(2.dp, 2.dp, 2.dp, 2.dp)
            .fillMaxWidth()
            .background(Color.DarkGray),
    ) {
        Text(
            color = Color.White,
            text = stringResource(id = R.string.text_result),
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                .widthIn(),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Text(
            color = Color.White,
            text = error,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                .widthIn(),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
    }
}

@Composable
fun ComposeSpinner() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}



@Composable
fun ComposeSnackbar(msg: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 100.dp, 0.dp, 0.dp)
            .height(100.dp),
    ) {
        val snackbarVisible = remember { mutableStateOf(false) }
        Snackbar(action = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("snack_error"),
                contentAlignment = Alignment.CenterEnd,
            ) {
                IconButton(
                    onClick = {
                        snackbarVisible.value = false
                    },
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_error),
                        contentDescription = stringResource(R.string.error),
                        tint = Color.White,
                    )
                }
            }
            Text(text = "failed", color = Color.White)
        }, modifier = Modifier.padding(10.dp)) {
            Text(text = "failed", color = Color.White)
        }
    }
}

