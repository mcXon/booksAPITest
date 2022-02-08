package com.example.networktest_w4.model.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Serializable its defined Java.lang
 *  - Not customizable
 *  - Uses reflection to decompose / recreate the object
 *  - lot of temp objects
 *
 *  Parcelable defined in Android framework
 *  - customizable
 *  - doesn't use reflection
 *  - Marshall / Unmarshall
 */

@Parcelize
data class BookResponse (
    val items: List<BookItem>
): Parcelable

@Parcelize
data class BookItem(
    val volumeInfo: VolumeItem
) : Parcelable

@Parcelize
data class VolumeItem(
    val title : String,
    val authors: List<String>,
    val imageLinks : ImageItem
) : Parcelable

@Parcelize
data class ImageItem(
    val thumbnail : String
) : Parcelable