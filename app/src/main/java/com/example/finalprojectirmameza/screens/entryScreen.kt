package com.example.finalprojectirmameza.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalprojectirmameza.DataStoreManager
import com.example.finalprojectirmameza.R
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalContext


@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    dataStoreManager: DataStoreManager
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val savedPassword by dataStoreManager.getPassword.collectAsState(initial = null)
    var showPasswordDialog by remember { mutableStateOf(false) }
    var enteredPassword by remember {mutableStateOf("")}

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFE4E1)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to My Diary",
            color = Color(0xFFFFB6C1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic
            
        )
        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.mail),
            contentDescription = "Heart envelope",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(150.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    if (savedPassword.isNullOrEmpty()) {
                        navController.navigate("notesScreen")
                    }
                    else {
                        showPasswordDialog = true
                    }
                }

        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Click Me",
            color = Color(0xFFFFB6C1),
            fontSize = 25.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold
        )
    }

    if (showPasswordDialog) {
        AlertDialog(
            onDismissRequest = {showPasswordDialog = false},
            title = {Text("Enter Password")},
            text = {
                OutlinedTextField(
                    value = enteredPassword,
                    onValueChange = {enteredPassword = it},
                    label = {Text("Password")}
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (enteredPassword == savedPassword) {
                        showPasswordDialog = false
                        enteredPassword = ""
                        navController.navigate("notesScreen")
                    }
                    else {
                        Toast.makeText(context, "Incorrect Password", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Enter")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showPasswordDialog = false
                    enteredPassword = ""
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}
