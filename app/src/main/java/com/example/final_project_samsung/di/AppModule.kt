package com.example.final_project_samsung.di

import android.app.Application
import androidx.room.Room
import com.example.final_project_samsung.app.data.data_source.EventDb
import com.example.final_project_samsung.app.data.repository.EventRepositoryImpl
import com.example.final_project_samsung.app.domain.repository.EventRepository
import com.example.final_project_samsung.app.domain.use_case.DeleteEvent
import com.example.final_project_samsung.app.domain.use_case.EventUseCases
import com.example.final_project_samsung.app.domain.use_case.GetEvents
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideEventDb(app: Application): EventDb {
        return Room.databaseBuilder(
            app,
            EventDb::class.java,
            EventDb.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideEventRepository(db: EventDb): EventRepository {
        return EventRepositoryImpl(db.eventDao)
    }

    @Provides
    @Singleton
    fun provideEventUseCases(repository: EventRepository): EventUseCases {
        return EventUseCases(
            getEvents = GetEvents(repository),
            deleteEvent = DeleteEvent(repository)
        )
    }
}