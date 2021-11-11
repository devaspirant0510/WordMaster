package dev.seh.wordmaster.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T : ViewDataBinding>
    (view: ViewGroup, @LayoutRes layoutRes: Int) : RecyclerView.ViewHolder(
    LayoutInflater.from(view.context).inflate(layoutRes, view, false)
) {
    protected val mBinding: T by lazy {
        DataBindingUtil.bind(itemView)!!
    }


}