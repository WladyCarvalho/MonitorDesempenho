package com.abitic.monitordesempenho.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat


@Parcelize
@Entity(tableName = "tb_nota")
data class Nota(
    val nota:Double,
    val disciplina: String,
    val ownerDisciplinaId:Long,
    val criado:Long=System.currentTimeMillis(),
@PrimaryKey(autoGenerate = true) val id:Long=0):Parcelable{
        val dataCriadaFormatado:String
        get() = DateFormat.getDateTimeInstance().format(criado)
    }

