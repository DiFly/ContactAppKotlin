package org.difly.contactappkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.difly.contactappkotlin.adapter.ContactListAdapter
import org.difly.contactappkotlin.entity.Contact
import org.difly.contactappkotlin.viewmodel.ContactViewModel

class MainActivity : AppCompatActivity() {
    private val newContactActivityRequestCode = 1
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ContactListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.allContacts.observe(this, Observer { contacts ->
            contacts?.let { adapter.setContacts(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewContactActivity::class.java)
            startActivityForResult(intent, newContactActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newContactActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewContactActivity.EXTRA_REPLY)?.let {
                val contact = Contact(null, it, "", "", "")
                contactViewModel.insertContact(contact)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}
