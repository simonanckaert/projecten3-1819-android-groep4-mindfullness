package com.groep4.mindfulness.model

import android.os.Parcel
import android.os.Parcelable

data class Oefening(var naam: String, var beschrijving: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(naam)
        parcel.writeString(beschrijving)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Oefening> {
        override fun createFromParcel(parcel: Parcel): Oefening {
            return Oefening(parcel)
        }

        override fun newArray(size: Int): Array<Oefening?> {
            return arrayOfNulls(size)
        }
    }
}

data class Message(val titel: String, val onderwerp: String, val afzender: String, val  tekst : String)