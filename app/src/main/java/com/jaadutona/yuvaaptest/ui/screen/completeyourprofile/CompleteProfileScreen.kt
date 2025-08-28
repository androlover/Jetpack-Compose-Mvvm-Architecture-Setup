package com.jaadutona.yuvaaptest.ui.screen.completeyourprofile


import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jaadutona.yuvaaptest.R
import java.util.Calendar

@Composable
fun CompleteProfileScreen(
    onNextClick: () -> Unit,
    modifier: Modifier,
    navController: NavHostController
) {
    var name by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Heading always on Top
        Text(
            text = "Complete Your Profile",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 50.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Form in Center
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable(onClick = onNextClick)
                )

                Icon(
                    painter = painterResource(id = R.drawable.edit_24px),
                    contentDescription = "Edit Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.Black, CircleShape)
                        .padding(4.dp)
                        .clickable(onClick = onNextClick)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = dob,
                onValueChange = {},
                readOnly = true,
                label = { Text("Date of Birth") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val datePicker = DatePickerDialog(
                            context,
                            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                dob = "$dayOfMonth/${month + 1}/$year"
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                        datePicker.show()
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Male", "Female", "Other").forEach { option ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = (gender == option),
                            onClick = { gender = option }
                        )
                        Text(option)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onNextClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save")
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCompleteProfileScreen() {
    CompleteProfileScreen({}, modifier = Modifier, navController = rememberNavController())
}
