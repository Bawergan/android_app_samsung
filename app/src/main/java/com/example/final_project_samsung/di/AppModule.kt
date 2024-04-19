package com.example.final_project_samsung.di

import android.app.Application
import androidx.room.Room
import com.example.final_project_samsung.app.data.data_source.TheAppDb
import com.example.final_project_samsung.app.data.repository.EventRepositoryImpl
import com.example.final_project_samsung.app.data.repository.GroupRepositoryImpl
import com.example.final_project_samsung.app.domain.repository.EventRepository
import com.example.final_project_samsung.app.domain.repository.GroupRepository
import com.example.final_project_samsung.app.domain.use_case.forEvent.AddEvent
import com.example.final_project_samsung.app.domain.use_case.forEvent.DeleteEvent
import com.example.final_project_samsung.app.domain.use_case.forEvent.EventUseCases
import com.example.final_project_samsung.app.domain.use_case.forEvent.GetEventById
import com.example.final_project_samsung.app.domain.use_case.forEvent.GetEvents
import com.example.final_project_samsung.app.domain.use_case.forGroup.AddGroup
import com.example.final_project_samsung.app.domain.use_case.forGroup.DeleteGroup
import com.example.final_project_samsung.app.domain.use_case.forGroup.GetGroupById
import com.example.final_project_samsung.app.domain.use_case.forGroup.GetGroups
import com.example.final_project_samsung.app.domain.use_case.forGroup.GroupUseCases
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
    fun provideEventDb(app: Application): TheAppDb {
        return Room.databaseBuilder(
            app,
            TheAppDb::class.java,
            TheAppDb.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideEventRepository(db: TheAppDb): EventRepository {
        return EventRepositoryImpl(db.eventDao)
    }

    @Provides
    @Singleton
    fun provideGroupRepository(db: TheAppDb): GroupRepository {
        return GroupRepositoryImpl(db.groupDao)
    }

    @Provides
    @Singleton
    fun provideEventUseCases(repository: EventRepository): EventUseCases {
        return EventUseCases(
            getEvents = GetEvents(repository),
            deleteEvent = DeleteEvent(repository),
            addEvent = AddEvent(repository),
            getEventById = GetEventById(repository)
        )
    }

    @Provides
    @Singleton
    fun provideGroupUseCases(repository: GroupRepository): GroupUseCases {
        return GroupUseCases(
            getGroups = GetGroups(repository),
            deleteGroup = DeleteGroup(repository),
            addGroup = AddGroup(repository),
            getGroupById = GetGroupById(repository)
        )
    }
}