package org.difly.contactappkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.difly.contactappkotlin.dao.ContactDao
import org.difly.contactappkotlin.entity.Contact

@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
public abstract class ContactRoomDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactRoomDatabase? = null

        fun getDatabase(context: Context): ContactRoomDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactRoomDatabase::class.java,
                    "contact_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}