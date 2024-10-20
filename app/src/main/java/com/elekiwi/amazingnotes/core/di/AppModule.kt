package com.elekiwi.amazingnotes.core.di

import android.app.Application
import androidx.room.Room
import com.elekiwi.amazingnotes.add_note.domain.use_case.SearchImages
import com.elekiwi.amazingnotes.add_note.domain.use_case.UpsertNote
import com.elekiwi.amazingnotes.core.data.local.NoteDb
import com.elekiwi.amazingnotes.core.data.remote.api.ImagesApi
import com.elekiwi.amazingnotes.core.data.repository.ImagesRepositoryImpl
import com.elekiwi.amazingnotes.core.data.repository.NoteRepositoryImpl
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
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): NoteDb {
        return Room.databaseBuilder(
            application,
            NoteDb::class.java,
            "note_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDb: NoteDb): NoteRepository {
        return NoteRepositoryImpl(noteDb)
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
        return UpsertNote(  noteRepository)
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

    @Provides
    @Singleton
        fun provideImagesRepository(imagesApi: ImagesApi): ImagesRepository {
        return ImagesRepositoryImpl(imagesApi)
    }

    @Provides
    @Singleton
    fun provideSearchImagesUseCase(imagesRepository: ImagesRepository): SearchImages {
        return SearchImages(imagesRepository)
    }

}