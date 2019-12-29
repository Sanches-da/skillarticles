package ru.skillbranch.skillarticles.ui.custom.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import kotlin.math.max
import kotlin.math.min

class NestedScrollBehaviorBottom<T : ConstraintLayout> constructor(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<T>(context, attrs){
    private var height: Int = 0

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: T,
        layoutDirection: Int
    ): Boolean {
        height = child.height
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: T,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ) = axes == ViewCompat.SCROLL_AXIS_VERTICAL

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: T,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        //child.translationY = max(0f, min(child.height.toFloat(), child.translationY + dy))
        child.translationY =if (dy > 0) child.height.toFloat() else 0f
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: T,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
//        child.apply {
//            //clearAnimation()
//            //animate()
//            //    .translationY(if (dyConsumed > 0) height.toFloat() else 0f)
//                //.duration = 200
//            //visibility = if (dyConsumed > 0) View.GONE else View.VISIBLE
//            translationY =if (dyConsumed > 0) height.toFloat() else 0f
//        }
    }
}