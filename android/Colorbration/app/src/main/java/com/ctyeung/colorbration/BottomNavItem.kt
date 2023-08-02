package com.ctyeung.colorbration

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title:String, var icon: ImageVector, var screen_route:String) {

    object Observers : BottomNavItem("Observers", Icons.Default.Settings, "observers")
    object Spectral : BottomNavItem("Spectral", Icons.Default.Star, "spectral")
    object Tristimulus : BottomNavItem("Tristimulus", Icons.Default.Star, "tristimulus")
    object Chromaticity : BottomNavItem("Chromaticity", Icons.Default.Face, "chromaticity")
    object CIE_Lab : BottomNavItem("CIE Lab", Icons.Default.List, "cie_lab")
    object sRGB : BottomNavItem("sRGB", Icons.Default.Share, "srgb")
}