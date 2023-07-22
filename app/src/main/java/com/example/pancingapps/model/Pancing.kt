package com.example.pancingapps.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Pancing_table")
data class Pancing(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val address: String,
    val jumlah: String,
    val berat: String,
    val harga: String,
    val latitude : Double?,
    val longitude : Double?
) : Parcelable