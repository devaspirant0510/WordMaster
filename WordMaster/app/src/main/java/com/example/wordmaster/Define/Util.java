package com.example.wordmaster.Define;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Util {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference myRefUser = database.getReference(Define.FIREBASE_REFERENCE_USER_ACCOUNT);
    public static DatabaseReference myRefWord = database.getReference();


}
