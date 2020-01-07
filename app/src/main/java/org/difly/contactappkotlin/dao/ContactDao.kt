package org.difly.contactappkotlin.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.difly.contactappkotlin.entity.Contact

@Dao
interface ContactDao {
    @Query("SELECT * from contact_table ORDER BY firstname ASC")
    fun getAlphabetizedWords(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()
}