package com.example.lab7.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lab7.Ticket
import com.example.lab7.database.TicketTypeConverter


@Database(entities = [Ticket::class], version = 1)
@TypeConverters(TicketTypeConverter::class)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}