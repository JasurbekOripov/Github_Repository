package uz.juo.githubrepository.ui.splash.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.juo.githubrepository.models.UserLoginReq
import uz.juo.githubrepository.models.UserLoginRes
import uz.juo.githubrepository.retrofit.ApiService
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    var apiService: ApiService
) : ViewModel() {

    fun login(userLoginReq: UserLoginReq): MutableLiveData<UserLoginRes> {
        var res = apiService.login(userLoginReq)
        res
        var data = MutableLiveData<UserLoginRes>()
        res.enqueue(object : Callback<UserLoginRes> {
            override fun onResponse(call: Call<UserLoginRes>, response: Response<UserLoginRes>) {
                if (response.isSuccessful && response.body() != null) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<UserLoginRes>, t: Throwable) {
                Log.d("TAG", "onFailure: ${ t.localizedMessage}")
            }
        })

        return data
    }

    fun getData(token: String): ResponseBody? {
        var res = apiService.getData(token)
        return if (res.isSuccessful) {
            res.body()
        } else {
            null
        }
    }
}