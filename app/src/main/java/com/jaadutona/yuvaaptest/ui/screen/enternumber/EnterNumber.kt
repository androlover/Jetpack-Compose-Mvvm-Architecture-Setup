package com.jaadutona.yuvaaptest.ui.screen.enternumber

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jaadutona.yuvaaptest.R
import com.jaadutona.yuvaaptest.ui.components.AppColors
import com.jaadutona.yuvaaptest.ui.components.TransparentTopBackBar

@Composable
fun EnterYourNumber(
    modifier: Modifier = Modifier,
    onNextClick: (String) -> Unit,
    navController: NavController
) {
    val focusManager = LocalFocusManager.current
    var phoneNumber by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TransparentTopBackBar(
                title = "",
                showBack = true,
                navController = navController
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(AppColors.Gradient))
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 24.dp
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Title
                Text(
                    text = stringResource(R.string.enter_your_number),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Phone Number Field
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        if (it.length <= 10 && it.all { ch -> ch.isDigit() }) {
                            phoneNumber = it
                        }
                    },
                    label = { Text("Phone Number") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Next Button
                Button(
                    onClick = { onNextClick(phoneNumber) },
                    enabled = phoneNumber.length == 10,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors()
                    ) {
                    Text("Next")
                }
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewEnterYourNumber() {
    EnterYourNumber(modifier = Modifier.fillMaxSize(), {}, navController = rememberNavController())
}
