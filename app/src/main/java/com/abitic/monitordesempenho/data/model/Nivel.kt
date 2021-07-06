package com.abitic.monitordesempenho.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "tb_nivel")
class Nivel(val curso:String,
            val ano:String,
            val semestre: String,
            val criado:Long=System.currentTimeMillis(),
            @PrimaryKey(autoGenerate = true) val nivelId:Long=0):Parcelable