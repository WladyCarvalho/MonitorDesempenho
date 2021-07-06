package com.abitic.monitordesempenho.utils

import android.content.Context
import android.content.Intent
import com.abitic.monitordesempenho.MainActivity


//Start Main activity function
fun Context.startMainActivit() =

    Intent(this, MainActivity::class.java).also{
        it.flags= Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

