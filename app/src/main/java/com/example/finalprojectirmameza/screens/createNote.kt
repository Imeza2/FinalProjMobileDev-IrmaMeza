package com.example.finalprojectirmameza.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import com.example.finalprojectirmameza.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectirmameza.DiaryViewModel
import com.example.finalprojectirmameza.Note



@Composable
fun CreateNote(
    modifier: Modifier = Modifier,
    navController: NavController,
    diaryViewModel: DiaryViewModel,
    noteId: Long? = null
    ) {

    val userInput = remember { mutableStateOf("")}
    var existingNote by remember {mutableStateOf<Note?>(null)}

    LaunchedEffect(noteId) {
        if (noteId != null) {
            val note = diaryViewModel.getNoteById(noteId)
            existingNote = note
            userInput.value = note?.content ?: ""
        }
    }
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.note),
            contentDescription = "note sheet",
            contentScale = ContentScale.FillWidth,
            modifier = modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp)
                .height(525.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            TextField(
                value = userInput.value,
                onValueChange = {userInput.value = it},
                modifier = Modifier
                    .fillMaxSize(),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                ),

                textStyle = TextStyle(
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Serif
                ),
                maxLines = Int.MAX_VALUE
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Cancel")
            }

            Spacer(modifier = Modifier.width(20.dp))

            Button(
                onClick = {
                    if (userInput.value.isNotBlank()) {
                        if (noteId == null) {
                            diaryViewModel.addEntry(userInput.value)
                        }
                        else {
                            existingNote?.let {note ->
                                val updatedNote = note.copy(content = userInput.value)
                                diaryViewModel.updateNote(updatedNote)
                            }
                        }
                        navController.popBackStack()
                    }
                }
            ) {
                Text("Save")
            }
            existingNote?.let {
                Button(
                    onClick = {
                        diaryViewModel.deleteNote(it)
                        navController.popBackStack()
                    },
                    modifier = Modifier.padding(start = 15.dp)
                ) {
                    Text("Delete")
                }
            }
        }

    }
}

@Preview
@Composable
fun NoteCreationView() {
    val navController = rememberNavController()
    CreateNote(navController = navController, diaryViewModel = viewModel())
}