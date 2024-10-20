package com.elekiwi.amazingnotes.add_note.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elekiwi.amazingnotes.add_note.domain.use_case.UpsertNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
     private  val upsertNote: UpsertNote
): ViewModel(){

    private val _addNoteState = MutableStateFlow(AddNoteState())
    val addNoteState = _addNoteState.asStateFlow()

    private val _noteSavedChannel = Channel<Boolean>()
    val noteSavedFlow = _noteSavedChannel.receiveAsFlow()

    fun onAction(action: AddNoteActions) {
        when (action) {
            is AddNoteActions.UpdateTitle -> {
                _addNoteState.update {
                    it.copy(title = action.newTitle)
                }
            }
            is AddNoteActions.UpdateDescription -> {
                _addNoteState.update {
                    it.copy(description = action.newDescription)
                }
             }
            is AddNoteActions.PickImage -> {
                TODO()
            }
            AddNoteActions.UpdateImagesDialogVisibility -> {
                TODO()
            }
            is AddNoteActions.UpdateSearchImageQuery -> {
                TODO()
            }
            AddNoteActions.SaveNote -> {
                viewModelScope.launch {
                     val isSaved = upsertNote(
                          title = _addNoteState.value.title,
                         description = _addNoteState.value.description,
                         imageUrl = _addNoteState.value.imageUrl
                     )
                    _noteSavedChannel.send(isSaved)  
                }
            }


        }
    }

    suspend fun upsertNote(
        title: String,
        description: String,
        imageUrl: String
    ): Boolean {
        return upsertNote.invoke(title, description, imageUrl)}
}