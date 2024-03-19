package kh.edu.rupp.quiz1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kh.edu.rupp.quiz1.api.ApiService
import kh.edu.rupp.quiz1.model.ProfileModel
import kh.edu.rupp.quiz1.model.UiState
import kh.edu.rupp.quiz1.model.UiStateStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainViewModel: ViewModel() {

    private val _profileModelUiState = MutableLiveData<UiState<ProfileModel>>()
    val profileModelUiState: LiveData<UiState<ProfileModel>> get() = _profileModelUiState

    fun loadProfileModel(){
        _profileModelUiState.value = UiState(UiStateStatus.loading)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://smlp-pub.s3.ap-southeast-1.amazonaws.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiService = retrofit.create<ApiService>()

        apiService.loadProfile().enqueue(object : Callback<ProfileModel> {
            override fun onResponse(call: Call<ProfileModel>, response: Response<ProfileModel>) {
                _profileModelUiState.value = UiState(UiStateStatus.success, data=response.body())
            }

            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
                Log.e("kh.edu.rupp.quiz1.viewmodel.MainViewModel", "Load profile model error: ${t.message}")
                _profileModelUiState.value = UiState(UiStateStatus.error)
            }
        })
    }
}
