@file:Suppress("unused")

package com.jaadutona.yuvaaptest.ui.screen.enterotp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jaadutona.yuvaaptest.R
import com.jaadutona.yuvaaptest.ui.components.AppColors
import com.jaadutona.yuvaaptest.ui.components.TransparentTopBackBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    modifier: Modifier = Modifier,
    title: String = "Verify OTP",
    subtitle: String = "Enter the 4-digit code sent to ",
    otpLength: Int = 4,
    initialSeconds: Int = 30,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onVerify: (String) -> Unit,
    onResend: () -> Unit,
    navController: NavHostController,
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var code by remember { mutableStateOf("") }
    var secondsLeft by remember { mutableIntStateOf(initialSeconds) }
    var timerJob by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()

    // Start / restart timer when secondsLeft resets
    LaunchedEffect(initialSeconds) {
        secondsLeft = initialSeconds
        timerJob?.cancel()
        timerJob = scope.launch {
            while (secondsLeft > 0) {
                delay(1000)
                secondsLeft--
            }
        }
        keyboardController?.show()
    }
    Scaffold(
        topBar = {
            TransparentTopBackBar(
                title = title,
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
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(70.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))

                // Hidden text field captures input
                val hiddenRequester = remember { FocusRequester() }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { hiddenRequester.requestFocus() },
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = code,
                        onValueChange = { new ->
                            val filtered = new.filter { it.isDigit() }.take(otpLength)
                            code = filtered
                        },
                        modifier = Modifier
                            .size(1.dp) // keep out of layout
                            .focusRequester(hiddenRequester),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        textStyle = TextStyle(fontSize = 1.sp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            if (code.length == otpLength && !isLoading) {
                                onVerify(code)
                                focusManager.clearFocus()
                            }
                        }),
                        maxLines = 1
                    )

                    OtpBoxes(
                        code = code,
                        length = otpLength,
                        boxSize = 44.dp,
                        cornerRadius = 12.dp,
                        onClick = { hiddenRequester.requestFocus() }
                    )
                }

                AnimatedVisibility(
                    visible = !errorMessage.isNullOrBlank(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = errorMessage.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = { if (code.length == otpLength) onVerify(code) },
                    enabled = code.length == otpLength && !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.width(10.dp))
                    }
                    Text("Verify")
                }

                Spacer(Modifier.height(16.dp))
                if (secondsLeft > 0) {
                    Text(
                        text = "Resend in 00:${secondsLeft.toString().padStart(2, '0')}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    TextButton(onClick = {
                        onResend()
                        secondsLeft = initialSeconds
                        timerJob?.cancel()
                        timerJob = scope.launch {
                            while (secondsLeft > 0) {
                                delay(1000)
                                secondsLeft--
                            }
                        }
                        // Optional: clear existing code on resend
                        code = ""
                    }) {
                        Text("Resend Code")
                    }
                }
            }

        }
    }

}

@Composable
private fun OtpBoxes(
    code: String,
    length: Int,
    boxSize: Dp,
    cornerRadius: Dp,
    onClick: () -> Unit,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(length) { index ->
            val char = code.getOrNull(index)?.toString() ?: ""
            val isFocused = code.length == index // crude focus indicator
            val border =
                if (isFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
            val container =
                if (isFocused) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface

            Box(
                modifier = Modifier
                    .size(boxSize)
                    .background(container, RoundedCornerShape(cornerRadius))
                    .border(BorderStroke(1.dp, border), RoundedCornerShape(cornerRadius))
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = char,
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun OtpScreenHost(
    onVerified: (String) -> Unit = {},
    onResend: () -> Unit = {},
    navController: NavHostController
) {
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    OtpScreen(
        isLoading = loading,
        errorMessage = error,
        onVerify = { code ->
            loading = true
            error = null
            scope.launch {
                delay(1200) // simulate API
                loading = false
                if (code == "1234") onVerified(code) else error =
                    "Invalid code. Please try again."
            }
        },
        onResend = {

        },
        navController = navController
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewOtp() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface {
            OtpScreenHost(
                onResend = {},
                navController = rememberNavController()
            )
        }
    }
}


