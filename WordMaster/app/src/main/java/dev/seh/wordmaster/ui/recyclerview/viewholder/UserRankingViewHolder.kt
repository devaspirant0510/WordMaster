package dev.seh.wordmaster.ui.recyclerview.viewholder

import android.view.ViewGroup
import com.bumptech.glide.Glide
import dev.seh.wordmaster.R
import dev.seh.wordmaster.base.BaseViewHolder
import dev.seh.wordmaster.databinding.LayoutItemUserRankingBinding
import dev.seh.wordmaster.model.firebase.UserModel

open class UserRankingViewHolder(val parent: ViewGroup) :

    BaseViewHolder<LayoutItemUserRankingBinding>(parent, R.layout.layout_item_user_ranking) {
    fun bind( data: UserModel) {
        mBinding.tvUserMessage.text = data.userComment
        mBinding.tvUserName.text = data.userName
        Glide.with(parent).load(data.userProfileUri).into(mBinding.ivProfile)

    }
}