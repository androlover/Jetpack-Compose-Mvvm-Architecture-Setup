package com.jaadutona.yuvaaptest.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaadutona.yuvaaptest.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About", fontWeight = FontWeight.Bold) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // App Name
            Text(
                text = "My Awesome App",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Short Description
            Text(
                text = "This app helps you stay productive and organized with ease. Manage your tasks, track your goals, and boost efficiency!",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Version Info
            Text(
                text = "Version 1.0.0",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Buttons
            Button(
                onClick = { /* open website */ },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Visit Website")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* open privacy policy */ },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Privacy Policy")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* send email */ },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Contact Us")
            }
        }
    }
}
