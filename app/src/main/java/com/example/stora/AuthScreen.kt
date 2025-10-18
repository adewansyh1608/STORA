package com.example.stora

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stora.ui.theme.StoraBlueButton
import com.example.stora.ui.theme.StoraBlueDark
import com.example.stora.ui.theme.StoraWhite
import com.example.stora.ui.theme.StoraYellowButton
import kotlinx.coroutines.delay

// Enum untuk mengelola status halaman
enum class AuthScreenState {
    WELCOME,
    LANDING,
    LOGIN,
    SIGN_UP
}

@Composable
fun AuthScreen() {
    // State untuk mengontrol halaman mana yang tampil
    var authState by rememberSaveable { mutableStateOf(AuthScreenState.WELCOME) }

    // Durasi animasi
    val animationDuration = 1000 // 1 detik

    // Efek untuk splash screen 3 detik
    LaunchedEffect(authState) {
        if (authState == AuthScreenState.WELCOME) {
            delay(3000) // Tampil selama 3 detik
            authState = AuthScreenState.LANDING // Pindah ke Landing
        }
    }

    // --- Animasi Utama ---

    // 1. Animasi berat (height) untuk latar belakang biru
    val blueBgWeight by animateFloatAsState(
        targetValue = when (authState) {
            AuthScreenState.WELCOME -> 1f    // 100% tinggi
            AuthScreenState.LANDING -> 0.65f // 65% tinggi
            else -> 0.3f                      // 30% tinggi
        },
        animationSpec = tween(durationMillis = animationDuration),
        label = "Blue BG Weight"
    )

    // 2. Animasi berat (height) untuk latar belakang putih
    val whiteBgWeight by animateFloatAsState(
        targetValue = when (authState) {
            AuthScreenState.WELCOME -> 0f
            AuthScreenState.LANDING -> 0.35f
            else -> 0.7f
        },
        animationSpec = tween(durationMillis = animationDuration),
        label = "White BG Weight"
    )

    // --- Layout Utama ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)   // ⟵ WAJIB supaya lengkung terlihat putih
    ) {

        // --- BAGIAN BIRU (ATAS) ---
        val blueCorner by animateDpAsState(
            targetValue = if (authState != AuthScreenState.WELCOME) 47.dp else 0.dp,
            animationSpec = tween(durationMillis = animationDuration),
            label = "Blue BG Corner"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(blueBgWeight) // Tinggi animatif
                // Terapkan shape langsung di background agar sudut membentuk warna biru juga
                .clip(                                   // ⟵ clip dulu
                    RoundedCornerShape(
                        bottomStart = blueCorner,
                        bottomEnd   = blueCorner
                    )
                )
                .background(
                    color = StoraBlueDark,
                    shape = RoundedCornerShape(
                        bottomStart = blueCorner,
                        bottomEnd   = blueCorner
                    )
                )
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = authState,
                label = "Header Content Animation",
                transitionSpec = {
                    fadeIn(animationSpec = tween(animationDuration)) togetherWith
                            fadeOut(animationSpec = tween(animationDuration))
                }
            ) { state ->
                when (state) {
                    AuthScreenState.WELCOME, AuthScreenState.LANDING -> {
                        WelcomeLandingHeader()
                    }
                    else -> {
                        LoginSignupHeader()
                    }
                }
            }
        }

        // --- BAGIAN PUTIH (BAWAH) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight() // Mengisi sisa tinggi
                .background(Color.White)
                .padding(horizontal = 32.dp, vertical = 24.dp)
        ) {
            // Konten dinamis berdasarkan status
            androidx.compose.animation.AnimatedVisibility(
                visible = authState == AuthScreenState.LANDING,
                modifier = Modifier.fillMaxSize(),
                enter = slideInVertically(animationSpec = tween(animationDuration)) { it } + fadeIn(),
                exit = slideOutVertically(animationSpec = tween(animationDuration)) { it } + fadeOut()
            ) {
                LandingButtons(
                    onLoginClicked = { authState = AuthScreenState.LOGIN },
                    onSignUpClicked = { authState = AuthScreenState.SIGN_UP }
                )
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = authState == AuthScreenState.LOGIN,
                modifier = Modifier.fillMaxSize(),
                enter = slideInVertically(animationSpec = tween(animationDuration)) { it } + fadeIn(),
                exit = slideOutVertically(animationSpec = tween(animationDuration)) { it } + fadeOut()
            ) {
                LoginForm(
                    onSignUpClicked = { authState = AuthScreenState.SIGN_UP }
                )
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = authState == AuthScreenState.SIGN_UP,
                modifier = Modifier.fillMaxSize(),
                enter = slideInVertically(animationSpec = tween(animationDuration)) { it } + fadeIn(),
                exit = slideOutVertically(animationSpec = tween(animationDuration)) { it } + fadeOut()
            ) {
                SignUpForm(
                    onLoginClicked = { authState = AuthScreenState.LOGIN }
                )
            }
        }
    }
}

// --- Komponen Header ---

@Composable
fun WelcomeLandingHeader() {
    // Sesuai Gambar 1 & 2
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.stora_logo),
            contentDescription = "Stora Logo",
            modifier = Modifier.size(130.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "STORA",
            color = StoraWhite,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Student Organization Asset Manager",
            color = StoraWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun LoginSignupHeader() {
    // Sesuai Gambar 3 & 4
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center // Pusatkan Row
    ) {
        Image(
            painter = painterResource(id = R.drawable.stora_logo),
            contentDescription = "Stora Logo",
            modifier = Modifier.size(60.dp), // Logo dikecilkan
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "STORA",
            color = StoraWhite,
            fontSize = 36.sp, // Teks dikecilkan
            fontWeight = FontWeight.Bold
        )
    }
}

// --- Komponen Tombol & Form ---

@Composable
fun LandingButtons(onLoginClicked: () -> Unit, onSignUpClicked: () -> Unit) {
    // Sesuai Gambar 2
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Button LOGIN
        Button(
            onClick = onLoginClicked,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = StoraBlueButton,
                contentColor = StoraWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "LOGIN", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Button SIGN UP (dengan style khusus)
        Button(
            onClick = onSignUpClicked,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = StoraYellowButton.copy(alpha = 0.43f), // Transparansi 43%
                contentColor = StoraBlueButton // Font color #37729C
            ),
            border = BorderStroke(2.dp, StoraYellowButton), // Stroke 2dp
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "SIGN UP", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LoginForm(onSignUpClicked: () -> Unit) {
    // Sesuai Gambar 3
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        StoraTextField(label = "Email", keyboardType = KeyboardType.Email)
        Spacer(modifier = Modifier.height(16.dp))
        StoraTextField(label = "Password", isPassword = true)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Forgot Password?",
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* TODO: Handle Forgot Password */ },
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* TODO: Handle Login */ },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = StoraBlueButton,
                contentColor = StoraWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "LOGIN", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Sign Up",
            color = StoraYellowButton, // Font color #EFBF6A
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.clickable { onSignUpClicked() }
        )
    }
}

@Composable
fun SignUpForm(onLoginClicked: () -> Unit) {
    // Sesuai Gambar 4
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        StoraTextField(label = "User Name")
        Spacer(modifier = Modifier.height(16.dp))
        StoraTextField(label = "Email", keyboardType = KeyboardType.Email)
        Spacer(modifier = Modifier.height(16.dp))
        StoraTextField(label = "Password", isPassword = true)
        Spacer(modifier = Modifier.height(16.dp))
        StoraTextField(label = "Confirm Password", isPassword = true)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* TODO: Handle Sign Up */ },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = StoraBlueButton,
                contentColor = StoraWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "SIGN UP", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Login",
            color = StoraYellowButton, // Font color #EFBF6A
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.clickable { onLoginClicked() }
        )
    }
}

// Komponen Input Field kustom
@Composable
fun StoraTextField(
    label: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var text by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(!isPassword) }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = StoraBlueButton,
            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
            cursorColor = StoraBlueButton
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (isPassword) {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        }
    )
}