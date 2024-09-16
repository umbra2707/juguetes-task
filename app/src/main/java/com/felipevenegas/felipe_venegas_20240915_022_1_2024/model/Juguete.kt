package com.felipevenegas.felipe_venegas_20240915_022_1_2024.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

@Serializable
data class Juguete (val id: Int,
                    val nombre: String,
                    @DrawableRes
                    val imagen: Int,
                    val precio: Int)
