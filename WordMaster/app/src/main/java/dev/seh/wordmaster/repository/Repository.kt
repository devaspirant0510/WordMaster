package dev.seh.wordmaster.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dev.seh.wordmaster.model.firebase.UserModel
import dev.seh.wordmaster.util.FirebaseInstance
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Repository {
    companion object{
        private const val TAG = "Repository"
    }
    suspend fun requestUserData(id:String):UserModel?{
        val data = suspendCoroutine<UserModel?> {
            FirebaseInstance.UserReference.child(id).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(UserModel::class.java)
                    it.resume(data)
                }
                override fun onCancelled(error: DatabaseError) {
                    it.resume(null)
                }
            })

        }
        return data
    }
    suspend fun requestUserDataList():MutableList<UserModel>?{
        val data = suspendCoroutine<MutableList<UserModel>?> {
            FirebaseInstance.UserReference.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data:MutableMap<String,UserModel> = snapshot.getValue() as MutableMap<String,UserModel>
                    val list :MutableList<UserModel> = mutableListOf()
                    for(sna in snapshot.children){
                        val elementData :UserModel?= sna.getValue(UserModel::class.java)
                        elementData?.let{
                            list.add(elementData)
                        }
                    }
                    it.resume(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    it.resume(null)
                }

            })
        }
        return data
    }
}