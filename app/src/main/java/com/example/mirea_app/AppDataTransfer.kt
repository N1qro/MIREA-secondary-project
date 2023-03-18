package com.example.mirea_app

import android.content.Context
import android.content.SharedPreferences


class AppDataTransfer {
    companion object {
        lateinit var sharedPreferences: SharedPreferences

        fun initContext(applicationContext: Context) {
            sharedPreferences = applicationContext.getSharedPreferences("Data", Context.MODE_PRIVATE)
        }

        inline fun <reified T> saveData(key: String, value: T) {
            val editor = sharedPreferences.edit()

            when (T::class) {
                String::class -> editor.putString(key, value as String)
                Int::class -> editor.putInt(key, value as Int)
                Float::class -> editor.putFloat(key, value as Float)
                Boolean::class -> editor.putBoolean(key, value as Boolean)
                else -> error("Unsupported type: ${T::class}")
            }

            editor.apply()
        }

        inline fun <reified T> saveData(data: Map<String, T>) {
            val editor = sharedPreferences.edit()

            data.forEach { (key, value) ->
                when(T::class) {
                    String::class -> editor.putString(key, value as String)
                    Int::class -> editor.putInt(key, value as Int)
                    Float::class -> editor.putFloat(key, value as Float)
                    Boolean::class -> editor.putBoolean(key, value as Boolean)
                    else -> error("Unsupported type: ${T::class}")
                }
            }

            editor.apply()
        }

        inline fun <reified T> loadData(key: String): T? {
            return when (T::class) {
                String::class -> sharedPreferences.getString(key, null) as T?
                Int::class -> sharedPreferences.getInt(key, 0) as T?
                Float::class -> sharedPreferences.getFloat(key, 0f) as T?
                Boolean::class -> sharedPreferences.getBoolean(key, false) as T?
                else -> error("Unsupported type: ${T::class}")
            }
        }

        inline fun <reified T> loadData(vararg keys: String): Map<String, T> {
            val result = mutableMapOf<String, T>()

            keys.forEach { key ->
                val value = when (T::class) {
                    String::class -> sharedPreferences.getString(key, null) as? T
                    Int::class -> sharedPreferences.getInt(key, 0) as? T
                    Float::class -> sharedPreferences.getFloat(key, 0f) as? T
                    Boolean::class -> sharedPreferences.getBoolean(key, false) as? T
                    else -> error("Unsupported type: ${T::class}")
                }

                if (value != null) {
                    result[key] = value
                }
            }

            return result
        }

        fun checkThatDataExists(key: String): Boolean {
            return sharedPreferences.contains(key)
        }
    }
}