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

    fun createSmallAddCashPopUpDialog(context: Context): Dialog {
        val popUp = Dialog(context)
        popUp.setContentView(R.layout.popup_add_cash)
        popUp.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return popUp

    }
}