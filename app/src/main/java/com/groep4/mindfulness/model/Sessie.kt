package com.groep4.mindfulness.model

import android.os.Parcel
import android.os.Parcelable

class Sessie : Parcelable {
    var sessieId: Int
    var naam: String
    var beschrijving: String
    var oefeningen: ArrayList<Oefening>? = null
    var vergrendeld: Boolean = false

    constructor(id: Int, naam: String, beschrijving: String, info: String, oefeningen: ArrayList<Oefening>?, vergrendeld: Boolean){
        this.sessieId = id
        this.naam = naam
        this.beschrijving = beschrijving
        this.oefeningen = oefeningen
        this.vergrendeld = vergrendeld
    }

    private constructor(parcel: Parcel) {
        this.sessieId = parcel.readInt()
        this.naam = parcel.readString()
        this.beschrijving = parcel.readString()
        this.oefeningen = parcel.createTypedArrayList(Oefening.CREATOR)
        this.vergrendeld = (parcel.readByte().toInt() != 0)
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(sessieId)
        dest?.writeString(naam)
        dest?.writeString(beschrijving)
        dest?.writeTypedList(oefeningen)
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