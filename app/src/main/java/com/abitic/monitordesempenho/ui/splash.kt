package com.abitic.monitordesempenho.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.abitic.monitordesempenho.R
import com.abitic.monitordesempenho.utils.AnimacaoProgress

class splash : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar=findViewById(R.id.progresso)
        progressBar.max=100
        progressBar.scaleY=3F
        progressoAnim()
    }

    //Animação da barra de progresso
    fun progressoAnim ()
    {
        val anim = AnimacaoProgress(this,progressBar,0F, 100F)
        anim.duration = 5000
        progressBar.animation = anim
    }
}