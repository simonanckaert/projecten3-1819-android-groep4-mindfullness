package com.groep4.mindfulness.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

class Oefening : Parcelable {
    var oefenigenId: Int
    var sessieId: Int
    var naam: String
    var beschrijving: String
    var groepen: String


    // File
    var fileUrl: String
    var fileMimeType: String


    constructor(id: Int, naam: String, beschrijving: String, sId: Int, url: String, mimeType: String, groepen: String){
        this.oefenigenId = id
        this.naam = naam
        this.beschrijving = beschrijving
        this.sessieId = sId
        this.fileUrl = url
        this.fileMimeType = mimeType
        this.groepen = groepen
    }

    private constructor(parcel: Parcel) {
        this.oefenigenId = parcel.readInt()
        //Log.d("TAG", parcel.readString())
        this.sessieId = parcel.readInt()
        this.naam = parcel.readString().orEmpty()
        this.beschrijving = parcel.readString().orEmpty()

        this.fileUrl = parcel.readString().orEmpty()
        this.fileMimeType = parcel.readString().orEmpty()
        this.groepen = parcel.readString().orEmpty()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(oefenigenId)
        dest?.writeString(naam)
        dest?.writeString(beschrijving)
        dest?.writeInt(sessieId)
        dest?.writeString(fileUrl)
        dest?.writeString(fileMimeType)
        dest?.writeString(groepen)
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