package com.example.stora.screens

import android.net.Uri
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.stora.data.LoansData
import com.example.stora.navigation.Routes
import com.example.stora.ui.theme.StoraBlueDark
import com.example.stora.ui.theme.StoraWhite
import com.example.stora.ui.theme.StoraYellow
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailLoanHistoryScreen(
    navController: NavHostController,
    loanId: Int
) {
    var isVisible by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    val loan = remember(loanId) {
        LoansData.loansHistory.find { it.id == loanId }
    }
    
    val textGray = Color(0xFF585858)
    
    // Calculate return status
    val returnStatus = remember(loan) {
        if (loan?.borrowDate != null && loan.returnDate != null) {
            try {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val borrowDateParsed = sdf.parse(loan.borrowDate)
                val returnDateParsed = sdf.parse(loan.returnDate)
                val currentDate = Date()
                
                if (borrowDateParsed != null && returnDateParsed != null) {
                    // Check if returned on time
                    if (currentDate.after(returnDateParsed)) {
                        "Telat" to Color(0xFFE53935) // Red
                    } else {
                        "Tepat Waktu" to Color(0xFF4CAF50) // Green
                    }
                } else {
                    "Tepat Waktu" to Color(0xFF4CAF50)
                }
            } catch (e: Exception) {
                "Tepat Waktu" to Color(0xFF4CAF50)
            }
        } else {
            "Tepat Waktu" to Color(0xFF4CAF50)
        }
    }
    
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }
    
    if (loan == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loan history not found")
        }
        return
    }

    Scaffold(
        containerColor = StoraWhite,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Loan History",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = StoraYellow
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = StoraWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = StoraBlueDark
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(StoraBlueDark)
                .padding(top = 24.dp)
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(animationSpec = tween(800, delayMillis = 200)) { it } + fadeIn(animationSpec = tween(800, delayMillis = 200)),
                modifier = Modifier.fillMaxSize()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = StoraWhite,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 24.dp)
                            .padding(top = 32.dp, bottom = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Item Name
                        Text(
                            text = loan.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = StoraBlueDark
                        )
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        // Borrower Info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Nama",
                                    fontSize = 12.sp,
                                    color = textGray,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = loan.borrower ?: "-",
                                    fontSize = 16.sp,
                                    color = StoraBlueDark,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Dates
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Dipinjam",
                                    fontSize = 12.sp,
                                    color = textGray,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = loan.borrowDate ?: "-",
                                    fontSize = 14.sp,
                                    color = StoraBlueDark,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Dikembalikan",
                                    fontSize = 12.sp,
                                    color = textGray,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = loan.returnDate ?: "-",
                                    fontSize = 14.sp,
                                    color = StoraBlueDark,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Return Status
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Column {
                                Text(
                                    text = "Status Pengembalian",
                                    fontSize = 12.sp,
                                    color = textGray,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = returnStatus.first,
                                    fontSize = 16.sp,
                                    color = returnStatus.second,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        // Photos Section
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Foto Saat Dipinjam
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Foto Saat di Pinjam",
                                    fontSize = 12.sp,
                                    color = textGray,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFFF5F5F5))
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xFFE0E0E0),
                                            shape = RoundedCornerShape(12.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (loan.imageUri != null) {
                                        Image(
                                            painter = rememberAsyncImagePainter(Uri.parse(loan.imageUri)),
                                            contentDescription = "Loan Photo",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Filled.Image,
                                            contentDescription = "No Image",
                                            tint = Color(0xFFBDBDBD),
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                }
                            }
                            
                            // Foto Saat Dikembalikan
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Foto Saat di Kembalikan",
                                    fontSize = 12.sp,
                                    color = textGray,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFFF5F5F5))
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xFFE0E0E0),
                                            shape = RoundedCornerShape(12.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (loan.returnImageUri != null) {
                                        Image(
                                            painter = rememberAsyncImagePainter(Uri.parse(loan.returnImageUri)),
                                            contentDescription = "Return Photo",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Filled.Image,
                                            contentDescription = "No Image",
                                            tint = Color(0xFFBDBDBD),
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        // Delete Button
                        Button(
                            onClick = { showDeleteDialog = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE53935) // Red
                            )
                        ) {
                            Text(
                                text = "Delete",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = StoraWhite
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
    
    // Modern Delete Confirmation Dialog
    if (showDeleteDialog) {
        Dialog(onDismissRequest = { showDeleteDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = StoraWhite
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Icon
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .background(Color(0xFFFFEBEE)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = Color(0xFFE53935),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Title
                    Text(
                        text = "Hapus History?",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = StoraBlueDark
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Description
                    Text(
                        text = "History peminjaman ini akan dihapus secara permanen dan tidak dapat dikembalikan.",
                        fontSize = 14.sp,
                        color = textGray,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 20.sp
                    )
                    
                    Spacer(modifier = Modifier.height(28.dp))
                    
                    // Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Cancel Button
                        OutlinedButton(
                            onClick = { showDeleteDialog = false },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = textGray
                            )
                        ) {
                            Text(
                                text = "Batal",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        
                        // Delete Button
                        Button(
                            onClick = {
                                LoansData.deleteLoanHistory(loan.id)
                                showDeleteDialog = false
                                
                                // Set result for previous screen
                                navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("history_deleted", true)
                                
                                // Navigate back
                                navController.popBackStack()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE53935)
                            )
                        ) {
                            Text(
                                text = "Hapus",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = StoraWhite
                            )
                        }
                    }
                }
            }
        }
    }
}
