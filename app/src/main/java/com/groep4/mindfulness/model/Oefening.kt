package com.groep4.mindfulness.model

import android.os.Parcel
import android.os.Parcelable

class Oefening : Parcelable {
    var oefenigenId: Int
    var sessieId: Int
    var naam: String
    var beschrijving: String

    // File
    var fileUrl: String
    var fileMimeType: String


    constructor(id: Int, naam: String, beschrijving: String, sId: Int, url: String, mimeType: String){
        this.oefenigenId = id
        this.naam = naam
        this.beschrijving = beschrijving
        this.sessieId = sId
        this.fileUrl = url
        this.fileMimeType = mimeType
    }

    private constructor(parcel: Parcel) {
        this.oefenigenId = parcel.readInt()
        this.naam = parcel.readString()
        this.beschrijving = parcel.readString()
        this.sessieId = parcel.readInt()
        this.fileUrl = parcel.readString()
        this.fileMimeType = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(oefenigenId)
        dest?.writeString(naam)
        dest?.writeString(beschrijving)
        dest?.writeInt(sessieId)
        dest?.writeString(fileUrl)
        dest?.writeString(fileMimeType)
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