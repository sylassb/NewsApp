package com.sylas.newsapp.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(messsage: String, duration: Int = Toast.LENGTH_LONG){
    Toast.makeText(
        requireContext(),
        messsage,
        duration
    ).show()
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}