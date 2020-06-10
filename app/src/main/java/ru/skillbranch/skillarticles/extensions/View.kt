package ru.skillbranch.skillarticles.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.get
import androidx.navigation.NavDestination
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
    val menu = this[0] as BottomNavigationMenuView
    if (this.selectedItemId != destination.id && menu.children.find { it.id == destination.id } != null) {
        this.selectedItemId = destination.id
    }

}

fun BottomNavigationView.selectItem(itemId: Int?) {
    val menu = this[0] as BottomNavigationMenuView
    if (itemId != null) {
        this.selectedItemId = itemId
    }
}