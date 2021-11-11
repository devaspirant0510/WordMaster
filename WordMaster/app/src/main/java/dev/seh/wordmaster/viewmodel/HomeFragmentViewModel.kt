package dev.seh.wordmaster.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.seh.wordmaster.model.firebase.UserModel
import dev.seh.wordmaster.repository.Repository
import kotlinx.coroutines.launch

class HomeFragmentViewModel() : ViewModel() {
    companion object {
        private const val TAG = "HomeFragmentViewModel"

    }

    private val tempId: String = "1687548254"

    private val repository: Repository = Repository()

    val _userData = MutableLiveData<UserModel>()
    val userData: LiveData<UserModel>
        get() = _userData

    private val _userDataList = MutableLiveData<MutableList<UserModel>>()
    val userDataList: LiveData<MutableList<UserModel>>
        get() = _userDataList


    init {
        requestUserData()
        requestUserDataList()
    }

    private fun requestUserData() {
        Log.e(TAG, "requestUserData: ${_userData.value}")
        viewModelScope.launch {
            try {
                _userData.value = repository.requestUserData(tempId)
                Log.e(TAG, "requestUserData: ${userData.value?.userName}")
            } catch (e: Exception) {
                Log.e(TAG, "requestUserData: ${e.message.toString()}")

            }
        }

    }

    private fun requestUserDataList() {
        viewModelScope.launch {
            try {
                _userDataList.value = repository.requestUserDataList()
                Log.e(TAG, "requestUserDataList: ${userDataList.value}")

            } catch (e: Exception) {
                Log.e(TAG, "requestUserDataList: ${e.message.toString()}")
            }
        }
    }
}


/*
*/
