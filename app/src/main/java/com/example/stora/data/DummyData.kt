package com.example.stora.data

import androidx.compose.runtime.mutableStateListOf
object DummyData {
    val inventoryItemList = mutableStateListOf(
        InventoryItem(
            name = "Vacum Cleaner",
            noinv = "HMSI/2025/xx/24",
            quantity = 2,
            category = "Elektronik",
            condition = "Baik",
            location = "Ruang Sekretariat HMSI",
            description = "Digunakan untuk membersihkan lantai, karpet, dan permukaan lain dari debu dan kotoran. Jangan digunakan selain kegunaan yang telah disampaikan sebelumnya.",
            date = "16/10/2025"
        ),
        InventoryItem(
            name = "Kulkas Display Minuman",
            noinv = "HMSI/2025/xx/12",
            quantity = 1,
            category = "Elektronik",
            condition = "Baik",
            location = "Ruang Sekretariat HMSI",
            description = "Digunakan untuk menyimpan dan menampilkan minuman dalam kondisi dingin agar siap disajikan. Hanya boleh diisi dengan minuman jualan divisi bisnis kreatif.",
            date = "04/08/2025"
        ),
        InventoryItem(
            name = "Lemari Berkas",
            noinv = "HMSI/2025/xx/8",
            quantity = 8,
            category = "Perabotan",
            condition = "Kurang Baik",
            location = "Ruang Sekretariat HMSI",
            description = "Lemari berkas digunakan untuk menyimpan dan mengamankan dokumen atau arsip penting HMSI. Jangan meletakkan brang lain di lemari ini.",
            date = "/20/11/2025"
        )
    )
}