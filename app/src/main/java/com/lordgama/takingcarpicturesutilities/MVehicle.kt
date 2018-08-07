package com.lordgama.takingcarpicturesutilities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.lordgama.carpicturesutilities.BaseVehicle

@Entity(tableName = "vehicles",
        indices = arrayOf(Index(value = arrayOf("pediment","vin"),unique = true))
        )
class MVehicle: BaseVehicle() {
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0
    @ColumnInfo(name = "custom_agent_name")
    override var customAgentName: String = ""
}