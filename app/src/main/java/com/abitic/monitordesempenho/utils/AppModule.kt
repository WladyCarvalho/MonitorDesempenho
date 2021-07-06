package com.abitic.monitordesempenho.utils

import android.app.Application
import androidx.room.Room
import com.abitic.monitordesempenho.data.db.MonitorDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application, callback:MonitorDB.CallBack) =
        Room.databaseBuilder(app, MonitorDB::class.java,"monitor_db")
                .fallbackToDestructiveMigration()
                .addCallback(callback)
                .build()

    @Provides
    fun provideNotaDao(db:MonitorDB) = db.notaDAO()

    @Provides
    fun provideProfessorDao(db:MonitorDB) = db.professorDAO()

    @Provides
    fun provideDisciplinaDao(db:MonitorDB) = db.disciplinaDAO()
    @Provides
    fun provideNiveisDao(db:MonitorDB) = db.nivelDAO()

    @Provides
    fun provideMonitorDao(db:MonitorDB) = db.monitorDAO()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope