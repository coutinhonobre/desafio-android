package com.picpay.desafio.android.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.repository.PicPayRepository
import com.picpay.desafio.android.util.LoadingState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: PicPayRepository) : ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _data = MutableLiveData<List<User>>()
    val data: LiveData<List<User>>
        get() = _data

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        repository.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful){
                    _data.postValue(response.body())
                    _loadingState.postValue(LoadingState.LOADED)
                }else
                    _loadingState.postValue(LoadingState.error(response.errorBody().toString()))
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _loadingState.postValue(LoadingState.error(t.message))
            }

        })
    }

}