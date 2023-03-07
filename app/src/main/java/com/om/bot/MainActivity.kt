package com.om.bot

/**
 * MAPD721 MidTerm Test
 * @Authors
 * Student Name: Oscar Miralles Fernandez
 * Student ID: 301250756
 **/

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var editusername: EditText

    companion object {
        private const val LOG_TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadUserFromPreferences()

        fab = findViewById(R.id.fab)

        fab.setOnClickListener{
            editusername = findViewById(R.id.edtUserName)
            var userName: String = editusername.text.toString()

            //Message if user name is empty
            if (userName == "") {
                Toast.makeText(this, "You have to introduce a User Name", Toast.LENGTH_SHORT).show()
            } else {
                savedInstanceState(userName)
                val intent = Intent(this, ChatActivity::class.java)
                this.startActivity(intent)
            }
        }
    }

    /**
     * Load user stored by key from Shared preferences
     */
    private fun loadUserFromPreferences() {
        val pref = getSharedPreferences(Constants.USER_PREF,Context.MODE_PRIVATE)
        var userName: String? = pref.getString(Constants.KEY_USER_NAME, "User").toString()

        editusername = findViewById(R.id.edtUserName)
        editusername.setText(userName)
    }

    /**
     * Store user in Shared preferences
     */
    private fun savedInstanceState(userName: String) {
        val pref = getSharedPreferences(Constants.USER_PREF,Context.MODE_PRIVATE)
        var editor = pref.edit()

        editor.putString(Constants.KEY_USER_NAME,userName)
        editor.commit()
    }
}