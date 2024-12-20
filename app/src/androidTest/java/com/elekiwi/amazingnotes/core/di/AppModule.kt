package com.elekiwi.amazingnotes.core.di

import android.app.Application
import androidx.room.Room
import com.elekiwi.amazingnotes.add_note.domain.use_case.UpsertNote
import com.elekiwi.amazingnotes.core.data.local.NoteDb
import com.elekiwi.amazingnotes.core.data.remote.api.ImagesApi
import com.elekiwi.amazingnotes.core.data.repository.FakeAndroidImagesRepository
import com.elekiwi.amazingnotes.core.data.repository.FakeAndroidNoteRepository
import com.elekiwi.amazingnotes.core.domain.repository.ImagesRepository
import com.elekiwi.amazingnotes.core.domain.repository.NoteRepository
import com.elekiwi.amazingnotes.note_list.domain.use_case.DeleteNote
import com.elekiwi.amazingnotes.note_list.domain.use_case.GetAllNotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): NoteDb {
        return Room.inMemoryDatabaseBuilder(
            application,
            NoteDb::class.java
        )
            .build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(): NoteRepository {
        return FakeAndroidNoteRepository()
    }

    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(noteRepository: NoteRepository): GetAllNotes {
        return GetAllNotes(noteRepository)
    }


    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(noteRepository: NoteRepository): DeleteNote {
        return DeleteNote(noteRepository)
    }

    @Provides
    @Singleton
    fun provideUpsertNoteUseCase(noteRepository: NoteRepository): UpsertNote {
        return UpsertNote(noteRepository)
    }

    @Provides
    @Singleton
    fun provideImagesRepository(): ImagesRepository {
        return FakeAndroidImagesRepository()
    }

    @Provides
    @Singleton
    fun provideImagesApi(): ImagesApi {
        return Retrofit.Builder()
            .baseUrl(ImagesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImagesApi::class.java)
    }


}