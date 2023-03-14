package com.vangelnum.unsplash.feature_contact.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel

class ContactViewModel: ViewModel() {
    fun goToMyVk(context: Context) {
        val uri: Uri = Uri.parse("https://vk.com/vangelnum")
        val browser = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(browser)

    }

    fun emailSend(context: Context) {
        val mailto = "mailto:vangelnum@gmail.com" +
                "?cc=" +
                "&subject=" + Uri.encode("WallPaper Unsplash")
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse(mailto)
        context.startActivity(emailIntent)
    }

}