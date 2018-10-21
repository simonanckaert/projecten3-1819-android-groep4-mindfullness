package com.groep4.mindfulness.model

import android.os.Parcel
import android.os.Parcelable


data class Sessie(var naam: String, var beschrijving: String, var info: String , var oefeningen: ArrayList<Oefening>, var vergrendeld: Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readArrayList(Oefening::class.java.classLoader) as ArrayList<Oefening>,
            parcel.readByte() != 0.toByte())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(naam)
        dest?.writeString(beschrijving)
        dest?.writeString(info)
        dest?.writeArray(arrayOf(oefeningen))
        dest?.writeByte((if (vergrendeld) 1 else 0).toByte())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sessie> {
        override fun createFromParcel(parcel: Parcel): Sessie {
            return Sessie(parcel)
        }

        override fun newArray(size: Int): Array<Sessie?> {
            return arrayOfNulls(size)
        }
    }
}



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