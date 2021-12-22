package com.frhanklindevs.bantukuy.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.frhanklindevs.bantukuy.R

object PopUpCreator {
    fun createSmallPopUpDialog(context: Context): Dialog {
        val popUp = Dialog(context)
        popUp.setContentView(R.layout.popup_login_register)
        popUp.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return popUp
    }

    fun createSmallAddPopUpDialog(context: Context): Dialog {
        val popUp = Dialog(context)
        popUp.setContentView(R.layout.popup_add)
        popUp.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return popUp

    }
    fun createSmallDelPopUpDialog(context: Context): Dialog {
        val popUp = Dialog(context)
        popUp.setContentView(R.layout.popup_del)
        popUp.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return popUp
    }
}