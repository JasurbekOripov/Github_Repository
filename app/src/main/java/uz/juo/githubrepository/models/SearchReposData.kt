package uz.juo.githubrepository.models

data class SearchReposData(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)