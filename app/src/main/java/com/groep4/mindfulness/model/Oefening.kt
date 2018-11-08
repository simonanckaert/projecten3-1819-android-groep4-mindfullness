package com.groep4.mindfulness.model

import android.os.Parcel
import android.os.Parcelable

class Oefening : Parcelable {
    var oefenigenId: Int
    var naam: String
    var beschrijving: String
    var sessieId: Int

    constructor(id: Int, naam: String, beschrijving: String, sId: Int){
        this.oefenigenId = id
        this.naam = naam
        this.beschrijving = beschrijving
        this.sessieId = sId
    }

    private constructor(parcel: Parcel) {
        this.oefenigenId = parcel.readInt()
        this.naam = parcel.readString()
        this.beschrijving = parcel.readString()
        this.sessieId = parcel.readInt()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(oefenigenId)
        dest?.writeString(naam)
        dest?.writeString(beschrijving)
        dest?.writeInt(sessieId)
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