package com.vangelnum.unsplash.core.presentation.drawer_layout

import com.vangelnum.unsplash.R

sealed class DrawerItems(val title: String, val icon: Int) {
    object Contacts : DrawerItems("Контакты", R.drawable.ic_baseline_message_24)
    object Share : DrawerItems("Поделиться", R.drawable.ic_outline_share_24)
    object SoundBoard: DrawerItems("Zxcursed SoundBoard", R.drawable.soundboard)
    object Wallpaper: DrawerItems("Zxcursed Wallpaper", R.drawable.soundboard)
    object DrumPad: DrawerItems("Zxcursed DrumPad", R.drawable.drumpad)
    object Pizza: DrawerItems("Pizza Recipes", R.drawable.pizza)
}