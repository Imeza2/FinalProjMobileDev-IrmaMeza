package com.example.finalprojectirmameza.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import com.example.finalprojectirmameza.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalprojectirmameza.DataStoreManager
import kotlinx.coroutines.launch


@Composable
fun SettingsScreen(modifier: Modifier = Modifier,
                   navController: NavController,
                   dataStoreManager: DataStoreManager
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val savedPassword by dataStoreManager.getPassword.collectAsState(initial = "")
    val locked = remember(savedPassword) {mutableStateOf(savedPassword?.isNotEmpty())}
    var showPasswordDialog by remember {mutableStateOf(false)}
    var newPassword by remember {mutableStateOf("")}

    if (showPasswordDialog) {
        AlertDialog(
            onDismissRequest = {showPasswordDialog = false},
            title = { Text(if (savedPassword.isNullOrEmpty()) "Set Password" else "Change Password")},
                text = {
                    Column{
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = {newPassword = it},
                            label = {Text("New Password")}
                        )
                    }
                },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch{
                            dataStoreManager.savePassword(newPassword)
                            Toast.makeText(context, "Password saved", Toast.LENGTH_SHORT).show()
                            locked.value = true
                            newPassword = ""
                            showPasswordDialog = false
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPasswordDialog = false}) {
                    Text("Cancel")
                }
            }
        )
    }


     Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFE4E1) )
    ) {
        Text(
            text = "Settings",
            color = Color(0xFFFFB6C1),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp, bottom = 30.dp)

        )
        Image(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = "Pink arrow",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {navController.navigate("notesScreen")}
        )

        Text(
            text = "Password",
            color = Color(0xFFFFB6C1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = modifier
                .padding(top = 20.dp, bottom = 10.dp)
                .offset(x = 5.dp)
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(50.dp)

        ) {
            //changes from locked to unlocked
            Image(
                painter = if (locked.value == true) painterResource(id = R.drawable.lock)
                else painterResource(id = R.drawable.unlocked),
                contentDescription = if (locked.value == true) "lock" else "unlocked",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable{
                        if(locked.value == true) {
                            coroutineScope.launch {
                                dataStoreManager.clearPassword()
                                locked.value = false
                                Toast.makeText(context, "App unlocked", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            showPasswordDialog = true
                        }
                    }
            )

        }

        Text(
            text = "Instructions",
            color = Color(0xFFFFB6C1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .offset(x = 5.dp)
        )

        Row(modifier = Modifier.padding(start = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(25.dp),
                modifier = Modifier) {
                Image(
                    painter = painterResource(id = R.drawable.mail),
                    contentDescription = "Heart envelope",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Image(
                    painter = painterResource(id = R.drawable.entry),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Image(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Pink arrow",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            Column(modifier = Modifier.padding(top = 15 .dp),
                verticalArrangement = Arrangement.spacedBy(60.dp)
            ) {
                Text(
                    text = "Open the app",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Create a new entry",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Access the settings",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Return to the previous page",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold

                )
            }
        }
        Text(
            text = "Purpose",
            color = Color(0xFFFFB6C1),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
        )
        Text(
            text = "This app provides a space for you to write your thoughts and gives you the ability to keep them private with a password if you choose.",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

