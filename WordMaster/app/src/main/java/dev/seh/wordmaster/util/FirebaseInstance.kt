package dev.seh.wordmaster.util

import com.google.firebase.database.FirebaseDatabase

object FirebaseInstance {
     const val USER_ACCOUNT = "UserAccount"
     const val USER_TEST = "UserTest"
     const val WORD_STORE = "WordStore"
    private val database = FirebaseDatabase.getInstance();
    private val reference  = database.reference
    val UserReference = reference.child(USER_ACCOUNT)



}