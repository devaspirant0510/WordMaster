package dev.seh.wordmaster.ui.recyclerview.adpater

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.seh.wordmaster.model.firebase.UserModel
import dev.seh.wordmaster.ui.recyclerview.viewholder.UserRankingViewHolder

class UserRankingAdapter():RecyclerView.Adapter<UserRankingViewHolder>(){
    private val list = mutableListOf<UserModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRankingViewHolder {
        return UserRankingViewHolder(parent)
    }

    override fun onBindViewHolder(holder: UserRankingViewHolder, position: Int) {
        Log.e("ds", "onBindViewHolder: ${list[position]} ")
        val data = list[position]
        holder.bind( UserModel(userName = data.userName,userProfileUri = data.userProfileUri,userComment = data.userComment))
    }

    override fun getItemCount(): Int =list.size

    fun addData(data:UserModel){
        list.add(data)
    }
    fun loadData(list:MutableList<UserModel>?){
        list?.let{
            this.list.addAll(it)
            this.notifyDataSetChanged()
        }
        Log.e("afds", "loadData: ${list?.get(0)}")


    }
}