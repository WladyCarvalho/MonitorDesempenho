package com.abitic.monitordesempenho.data.dao

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import com.abitic.monitordesempenho.data.model.Nivel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


private const val TAG = "PreferenceManager"

data class userSettings(val nome:String,val curso:String,val ano:String,val semestre:String, val position:Int)

@Singleton
class ConfiguracaoUser @Inject constructor(@ApplicationContext context: Context){

    private val datastore = context.createDataStore("user_settings")
    val preferencesFlow = datastore.data
        .catch {
                exception ->
            if (exception is IOException){

                Log.e(TAG, ":Erro lendo as preferencias ",exception )
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map {
            preference ->
            val nome = preference[PreferencesKeys.NOME_USER]?:""
            val ano = preference[PreferencesKeys.ANO_USER]?:""
            val curso = preference[PreferencesKeys.CURSO_USER]?:""
            val semestre = preference[PreferencesKeys.SEMESTRE_USER]?:""
            val position = preference[PreferencesKeys.POSITION_USER]?:0

            userSettings(nome,curso,ano,semestre,position)
        }

    //Actualizar as configurações do usuário
    suspend fun updateNome(nome:String){
        datastore.edit { preference ->
            preference[PreferencesKeys.NOME_USER] = nome
        }
    }

    suspend fun updateCurso(curso:String){
        datastore.edit { preference ->
            preference[PreferencesKeys.CURSO_USER] = curso
        }
    }

    suspend fun updateAno(ano:String){
        datastore.edit { preference ->
            preference[PreferencesKeys.ANO_USER] = ano
        }
    }

    suspend fun updateSemestre(semestre:String){
        datastore.edit { preference ->
            preference[PreferencesKeys.SEMESTRE_USER] = semestre
        }

    }
    suspend fun updatePosition(position:Int){
        datastore.edit { preference ->
            preference[PreferencesKeys.POSITION_USER] = position
        }
    }

    private object PreferencesKeys{
        val NOME_USER = preferencesKey<String>("nome_user")
        val CURSO_USER = preferencesKey<String>("curso_user")
        val SEMESTRE_USER = preferencesKey<String>("semestre_user")
        val ANO_USER = preferencesKey<String>("ano_user")
        val POSITION_USER = preferencesKey<Int>("position_user")
    }
}