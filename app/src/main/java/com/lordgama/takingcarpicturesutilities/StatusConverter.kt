package com.lordgama.takingcarpicturesutilities

import android.arch.persistence.room.TypeConverter
import com.lordgama.carpicturesutilities.Status


class StatusConverter{
    @TypeConverter
    fun toStatus(statusString: String): Status {
        /*try {
            return Status.valueOf(statusString)
        }catch (e: IllegalArgumentException){
            return Status.PENDING_STATUS
        }*/
        val response = Status.from(statusString)
        if(response != null)
            return response
        else
            return Status.PENDING_STATUS

    }
    
    @TypeConverter
    fun fromStatus(status: Status): String {
        when(status){
            Status.PENDING_STATUS -> {
                return Status.PENDING_STATUS.status
            }
            Status.PENDING_CAPTURE -> {
                return Status.PENDING_CAPTURE.status
            }
            Status.PENDING_TEST -> {
                return Status.PENDING_TEST.status
            }
            Status.COMPLETE_STATUS -> {
                return Status.COMPLETE_STATUS.status
            }
        }
    }
}