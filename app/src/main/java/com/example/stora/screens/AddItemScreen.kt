package com.example.stora.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stora.data.DummyData
import com.example.stora.data.InventoryItem
import com.example.stora.ui.theme.StoraBlueDark
import com.example.stora.ui.theme.StoraWhite
import com.example.stora.ui.theme.StoraYellow
import kotlinx.coroutines.delay

@Composable
fun AddItemScreen(navController: NavHostController) {
    var isVisible by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }


    val blueBgWeight by animateFloatAsState(
        targetValue = if (isVisible) 0.15f else 1f,
        animationSpec = tween(durationMillis = 800),
        label = "Blue BG Weight"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StoraBlueDark)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(blueBgWeight)
                .background(StoraBlueDark),
            contentAlignment = Alignment.Center
        ) {

            StoraTopBar(
                title = "Add Items",
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(StoraWhite)
                .padding(top = 24.dp)
        ) {

            androidx.compose.animation.AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(animationSpec = tween(800, delayMillis = 200)) { it } + fadeIn(animationSpec = tween(800, delayMillis = 200)),
                modifier = Modifier.fillMaxSize()
            ) {
                AddItemForm(navController = navController)
            }
        }
    }
}

@Composable
fun StoraTopBar(title: String, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = StoraYellow
            )
        }
        Text(
            text = title,
            color = StoraYellow,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun AddItemForm(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var noinv by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StoraFormField(
            value = name,
            onValueChange = { name = it; isError = false },
            label = "Nama Inventaris"
        )
        StoraFormField(
            value = noinv,
            onValueChange = { noinv = it; isError = false },
            label = "Nomor Inventaris"
        )
        StoraFormField(
            value = quantity,
            onValueChange = { quantity = it; isError = false },
            label = "Jumlah",
            keyboardType = KeyboardType.Number
        )
        StoraFormField(
            value = category,
            onValueChange = { category = it; isError = false },
            label = "Kategori"
        )
        StoraFormField(
            value = condition,
            onValueChange = { condition = it; isError = false },
            label = "Kondisi"
        )
        StoraFormField(
            value = location,
            onValueChange = { location = it; isError = false },
            label = "Lokasi"
        )
        StoraFormField(
            value = date,
            onValueChange = { date = it; isError = false },
            label = "Tanggal pencatatan"
        )
        StoraFormField(
            value = description,
            onValueChange = { description = it; isError = false },
            label = "Deskripsi",
            singleLine = false,
            modifier = Modifier.height(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isError) {
            Text(
                text = "Semua kolom harus diisi!",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                val qtyInt = quantity.toIntOrNull()
                if (name.isNotBlank() && noinv.isNotBlank() && qtyInt != null && category.isNotBlank() && condition.isNotBlank() && location.isNotBlank() && description.isNotBlank() && date.isNotBlank()) {
                    val newItem = InventoryItem(
                        name = name,
                        noinv = noinv,
                        quantity = qtyInt,
                        category = category,
                        condition = condition,
                        location = location,
                        description = description,
                        date = date
                    )
                    DummyData.inventoryItemList.add(0, newItem)
                    navController.previousBackStackEntry
                        ?.savedStateHandle?.set("newItemAdded", true)
                    navController.popBackStack()
                    isError = false
                } else {
                    isError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = StoraYellow,
                contentColor = StoraBlueDark
            )
        ) {
            Text("Save", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoraFormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val fieldColor = Color(0xFFE9E4DE)
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = fieldColor,
                unfocusedContainerColor = fieldColor,
                disabledContainerColor = fieldColor,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}