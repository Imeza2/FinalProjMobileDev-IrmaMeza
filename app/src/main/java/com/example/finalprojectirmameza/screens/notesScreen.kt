package com.example.finalprojectirmameza.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalprojectirmameza.R
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.finalprojectirmameza.DiaryViewModel


@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    diaryViewModel: DiaryViewModel) {
    var userInput by remember { mutableStateOf("") }

    val diaryEntries = remember { diaryViewModel.diaryEntries }
    val filteredEntries = if (userInput.isBlank()) {
        diaryEntries
    } else {
        diaryEntries.filter {
            it.content.contains(userInput, ignoreCase = true)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFE4E1) )
            .padding(top = 60.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Pink arrow",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {navController.navigate("enterScreen")}
            )
            Image(
                painter = painterResource(id = R.drawable.entry),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable{ navController.navigate("createNote") }
            )
            Image(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)

                    .clip(RoundedCornerShape(10.dp))
                    .clickable{ navController.navigate("settings") }
            )

        }
        TextField(
            value = userInput,
            onValueChange = {userInput = it},
            placeholder = {
                Text(
                    text = "Search Diary",
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 24.sp)
                )},
            modifier = modifier
                .fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 24.sp,
                textAlign = TextAlign.Start
            ),)

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(filteredEntries) { entry ->
                val previewText = entry.content.split(" ").take(5).joinToString(" ")
                Text(
                    text = previewText,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            navController.navigate("createNote/${entry.id}")
                        }
                )
            }
        }
    }
}
