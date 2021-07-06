package com.abitic.monitordesempenho.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "tb_disciplina")
@Parcelize
data class Disciplina( val desc:String,
                       val professorOwnerId:Long,
                       val nivelOwnerId: Long,
                       @PrimaryKey(autoGenerate = true) val disciplinaId:Long=0):Parcelable{
    override fun toString(): String {
        return desc
    }
                       }
