package com.example.wordmaster.Define;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Util {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference myRefUser = database.getReference(Const.FIREBASE_REFERENCE_USER_ACCOUNT);
    public static DatabaseReference myRefWord = database.getReference(Const.FIREBASE_REFERENCE_WORD_STORE);
    public static DatabaseReference myRefTest = database.getReference(Const.FIREBASE_REFERENCE_WORD_TEST);
}
