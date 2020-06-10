package ru.skillbranch.skillarticles.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.get
import androidx.navigation.NavDestination
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

fun View.setMarginOptionally(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0){
    val lp = this.layoutParams as ViewGroup.MarginLayoutParams
    lp.setMargins(left, top, right, bottom)
    this.layoutParams = lp
}

fun View.setPaddingOptionally(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0){
    this.setPadding(left, top, right, bottom)
}

fun BottomNavigationView.selectDestination(destination: NavDestination) {
    val menu = this.menu
    val item = menu.findItem(destination.id)
    item.isChecked = true
}

fun BottomNavigationView.selectItem(itemId: Int?) {
    val menu = this.menu
    if (itemId != null) {
        val item = menu.findItem(itemId)
        item.isChecked = true
    }
}