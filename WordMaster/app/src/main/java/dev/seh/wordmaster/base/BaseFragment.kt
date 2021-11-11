package dev.seh.wordmaster.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T:ViewDataBinding>(@LayoutRes private val layoutRes:Int): Fragment() {
    private var bindObj:T? = null
    protected val mBinding : T by lazy{
        bindObj!!
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindObj = DataBindingUtil.inflate(inflater,layoutRes,container,false)
        mBinding.lifecycleOwner=  this
        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindObj = null
    }

}