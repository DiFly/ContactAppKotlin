package org.difly.contactappkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.difly.contactappkotlin.dao.ContactDao
import org.difly.contactappkotlin.entity.Contact

@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
public abstract class ContactRoomDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    private class ContactDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.contactDao())
                }
            }
        }

        suspend fun populateDatabase(contactDao: ContactDao) {
            contactDao.deleteAll()

            var contact = Contact(
                firstName = "First",
                lastName = "LastFirst",
                phoneNumber = "+380123456789",
                phoneType = "Home",
                id = null)
            contactDao.insert(contact)

            contact = Contact(
                firstName = "Second",
                lastName = "LastSecond",
                phoneNumber = "+380123456789",
                phoneType = "Home",
                id = null
            )
            contactDao.insert(contact)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ContactRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ContactRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactRoomDatabase::class.java,
                    "contact_database"
                ).addCallback(ContactDatabaseCallback(scope)).build()
                INSTANCE = instance

                instance
            }
        }
    }
}