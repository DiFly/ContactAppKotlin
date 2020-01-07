package org.difly.contactappkotlin.repository

import androidx.lifecycle.LiveData
import org.difly.contactappkotlin.dao.ContactDao
import org.difly.contactappkotlin.entity.Contact

class ContactRepository(private val contactDao: ContactDao) {
    val allContacts: LiveData<List<Contact>> = contactDao.getAlphabetizedWords()

    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }
}