package com.example.wordmaster.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author : seungHo
 * @since : 2021-09-22
 * class : UserJoinTest.java
 * github : devaspirant0510
 * email : seungho020510@gmail.com
 * description :
 */
@Entity
public class UserJoinTest {
    @PrimaryKey
    private String userId;

    @ColumnInfo
    private String roomKey;


}
