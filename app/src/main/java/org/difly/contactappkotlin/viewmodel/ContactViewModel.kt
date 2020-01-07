package org.difly.contactappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.difly.contactappkotlin.db.ContactRoomDatabase
import org.difly.contactappkotlin.entity.Contact
import org.difly.contactappkotlin.repository.ContactRepository

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ContactRepository
    val allContacts: LiveData<List<Contact>>

    init {
        val contactDao = ContactRoomDatabase.getDatabase(application).contactDao()
        repository = ContactRepository(contactDao)
        allContacts = repository.allContacts
    }

    fun inserContact(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }
}