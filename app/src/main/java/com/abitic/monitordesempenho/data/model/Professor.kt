package com.abitic.monitordesempenho.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "tb_professor")
data class Professor(
    val nome:String,
@PrimaryKey(autoGenerate = true) val professorId:Long=0): Parcelable
{
    override fun toString(): String {
        return nome
    }
}
