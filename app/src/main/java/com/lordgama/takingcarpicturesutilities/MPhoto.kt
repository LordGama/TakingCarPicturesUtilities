package com.lordgama.takingcarpicturesutilities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.lordgama.carpicturesutilities.VehiclePhoto

@Entity(tableName = "photos",
        foreignKeys = arrayOf(
        ForeignKey(entity = MVehicle::class, parentColumns = arrayOf("id"), childColumns = arrayOf("vehicle"),onDelete = ForeignKey.CASCADE)
        ),
        indices = arrayOf(Index(value = arrayOf("vehicle","type"),unique = true)))
class MPhoto: VehiclePhoto() {
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0
}