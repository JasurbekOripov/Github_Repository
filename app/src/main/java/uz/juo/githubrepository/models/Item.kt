package uz.juo.githubrepository.models

data class Item(

    val id: Int,
    val name: String,
    val language: String,
    val node_id: String,
    val owner: Owner,
    val `private`: Boolean,
    val updated_at: String,
    val url: String,
    val html_url: String,
    val visibility: String,
   val full_name:String

)