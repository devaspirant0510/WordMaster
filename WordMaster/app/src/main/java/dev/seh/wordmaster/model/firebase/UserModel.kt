package dev.seh.wordmaster.model.firebase

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
class UserModel(
    var activityHistory: HashMap<String, HashMap<String, HashMap<String, Int>>>?=null,
    var joinDate:String?=null,
    var userAge:String?=null,
    var userEmail:String?=null,
    var userDownloadDict:HashMap<String,List<String>>?=null,
    var userComment:String?=null,
    var userGender:String?=null,
    var userJoinTestId:String?=null,
    var userName:String?=null,
    var userProfileUri:String?=null
)
