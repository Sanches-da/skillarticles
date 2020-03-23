package ru.skillbranch.skillarticles.ui.custom

import android.os.Parcel
import android.os.Parcelable
import android.view.View

class SavedState: View.BaseSavedState, Parcelable {
    var ssIsManual = false
    var ssIsDark = false
    var ssIds = arrayListOf<Int>()

    constructor(superState: Parcelable?) : super(superState)
    constructor(source: Parcel) : super(source) {
        var tmpArray = intArrayOf()
        source.readIntArray(tmpArray)
        tmpArray.forEach { ssIds.add(it) }
        ssIsDark = (source.readInt() == 1)
        ssIsManual = (source.readInt() == 1)
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(if(ssIsManual) 1 else 0)
        out.writeInt(if(ssIsDark) 1 else 0)
        out.writeIntArray(ssIds.toIntArray())
    }

    override fun describeContents(): Int = 0
}