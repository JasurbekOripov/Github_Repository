package uz.juo.githubrepository.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("repos_url")
    val reposUrl: String?,
    @SerializedName("blog")
    val blog: String?
)