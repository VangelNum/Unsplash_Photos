package com.vangelnum.unsplash.core.presentation

import com.vangelnum.unsplash.R

sealed class MenuItems(val title: String, val icon: Int) {
    object Contacts : MenuItems("Контакты", R.drawable.ic_baseline_message_24)
    object Share : MenuItems("Поделиться", R.drawable.ic_outline_share_24)
    object SoundBoard: MenuItems("Zxcursed SoundBoard", R.drawable.soundboard)
    object Wallpaper: MenuItems("Zxcursed Wallpaper", R.drawable.soundboard)
    object DrumPad: MenuItems("Zxcursed DrumPad", R.drawable.drumpad)
    object Pizza: MenuItems("Pizza Recipes", R.drawable.pizza)
}