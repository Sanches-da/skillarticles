package ru.skillbranch.skillarticles.data.delegates

import ru.skillbranch.skillarticles.data.local.PrefManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrefDelegate<T>(private val defaultValue: T) : ReadWriteProperty<PrefManager, T?>{
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: PrefManager, property: KProperty<*>): T? =
        when (defaultValue){
            is Boolean -> thisRef.preferences.getBoolean(property.name, defaultValue) as? T
            is String -> thisRef.preferences.getString(property.name, defaultValue) as? T
            is Float -> thisRef.preferences.getFloat(property.name, defaultValue) as? T
            is Int -> thisRef.preferences.getInt(property.name, defaultValue) as? T
            is Long -> thisRef.preferences.getLong(property.name, defaultValue) as? T
            else -> throw Throwable("Unexpected type of shared preference")
        }


    override fun setValue(thisRef: PrefManager, property: KProperty<*>, value: T?) {
        val edPref = thisRef.preferences.edit()
        when (value){
            is Boolean -> edPref.putBoolean(property.name, value)
            is String -> edPref.putString(property.name, value)
            is Float -> edPref.putFloat(property.name, value)
            is Int -> edPref.putInt(property.name, value)
            is Long -> edPref.putLong(property.name, value)
            else -> throw Throwable("Unexpected type of shared preference")
        }
        edPref.apply()
    }

}