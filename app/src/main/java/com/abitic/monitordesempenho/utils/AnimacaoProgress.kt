package com.abitic.monitordesempenho.utils

import android.content.Context
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import com.abitic.monitordesempenho.MainActivity


class AnimacaoProgress : Animation {

    private var context: Context
    private var progressbar: ProgressBar
    private var de:Float

    private var para:Float

    constructor(

        context: Context,
        progressBar: ProgressBar,
        de: Float,
        para: Float

    ){
        this.context = context
        this.progressbar = progressBar
        this.de = de
        this.para = para
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)

        var valor:Float=de + (para - de) * interpolatedTime

        var inteiro = valor.toInt()
        progressbar.setProgress(inteiro)


        if(inteiro == para.toInt()){
            var intent: Intent? = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }


    }
}

