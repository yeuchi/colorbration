package com.ctyeung.colorbration

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource

sealed class BottomNavItem(var title:String, var iconId:Int, var screen_route:String) {
    object Observers : BottomNavItem("Observers", R.drawable.ic_observers, "observers")
    object Illuminant : BottomNavItem("Illuminant", R.drawable.ic_light, "illuminant")
    object Reflectance : BottomNavItem("Reflectance", R.drawable.ic_graph, "reflectance")
    object Tristimulus : BottomNavItem("Tristimulus", R.drawable.ic_tristimulus, "tristimulus")
    object Chromaticity : BottomNavItem("Chromaticity", R.drawable.ic_chromaticity, "chromaticity")
    object CIE_Lab : BottomNavItem("CIE Lab", R.drawable.ic_lab, "cie_lab")
    object sRGB : BottomNavItem("sRGB", R.drawable.ic_tv, "srgb")
}