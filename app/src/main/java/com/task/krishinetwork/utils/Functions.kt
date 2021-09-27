package com.task.krishinetwork.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.task.krishinetwork.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Patterns
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.krishinetwork.api.model.Data
import com.task.krishinetwork.api.model.OtherMandi
import okhttp3.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.reflect.Type


fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showError(editTextLayout: TextInputLayout, message: String) {
    editTextLayout.apply {
        error = message
        setErrorIconDrawable(R.drawable.ic_baseline_error_outline_24)
    }
}

class BitmapConvertor {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?) : ByteArray {
        val outputStream = ByteArrayOutputStream()
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        }
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray) : Bitmap? {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}

class OtherMandiConvertor {
    @TypeConverter
    fun fromOtherMandi(data: List<OtherMandi>) : String {

        return Gson().toJson(data)
    }

    @TypeConverter
    fun toOtherMandi(data: String) : List<OtherMandi> {
        val listType: Type = object : TypeToken<List<OtherMandi>>() {}.type
        return Gson().fromJson(data, listType)
    }

}

fun isInternetAvailable(context: Context): Boolean {
    var isConnected: Boolean = false // Initial Value
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}


