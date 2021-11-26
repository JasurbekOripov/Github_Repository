package uz.juo.githubrepository.retrofit

import androidx.lifecycle.LiveData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import uz.juo.githubrepository.models.UserLoginReq
import uz.juo.githubrepository.models.SearchReposData
import uz.juo.githubrepository.models.User
import uz.juo.githubrepository.models.UserLoginRes

interface ApiService {

    //login
    @POST("login")
    fun login(@Body login: UserLoginReq): Call<UserLoginRes>

//    @GET("users/{login}")
//    fun getUser(@Path("login") login: String): LiveData<Response<User>>

    //get token
    @GET("secretinfo")
    fun getData(@Header("authorization")token:String): Response<ResponseBody>

    //search repository
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<SearchReposData>

}