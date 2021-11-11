package dev.seh.wordmaster.util

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.seh.wordmaster.ui.recyclerview.adpater.UserRankingAdapter
import dev.seh.wordmaster.model.firebase.UserModel




object BindingAdapters {
    @JvmStatic
    @BindingAdapter("loadUserRankingItems")
    fun loadUserRankingItems(recyclerView:RecyclerView,list:MutableList<UserModel>?){
        val rvUserRankingAdapter = recyclerView.adapter as UserRankingAdapter
        rvUserRankingAdapter.loadData(list)
    }

    @JvmStatic
    @BindingAdapter("loadImageUrl")
    fun loadImageUrl(imageView: ImageView,url:String?){
        Log.e("afsd", "loadImageUrl: $url")
        imageView.background = ShapeDrawable(OvalShape())
        imageView.clipToOutline = true
        Glide.with(imageView.context).load(url).into(imageView)
    }
}