package uz.juo.githubrepository.retrofit

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.juo.githubrepository.models.Item
import uz.juo.githubrepository.models.SearchReposData
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiHelper @Inject constructor(var str: String, var apiService: ApiService,var context: Context) :
    PagingSource<Int, Item>() {
    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition
    }

    var totalPage = -1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        try {
            var page = params.key ?: 1
            if (totalPage == -1 || totalPage >= page) {
                var users = apiService.searchRepos(q = str, page = page, size = 20)
                totalPage = users.body()?.total_count?.div(20)?.plus(1) ?: 0
                Log.d("TAG", "pagess:${page}")
                if (users.isSuccessful) {
                    if (users.body()?.items?.size ?: 0 < 20) {
                        totalPage = 0
                    }
                } else {
                    Toast.makeText(context, "Over limited", Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "loadError:${users}")
                }
                return try {
                    if (page > 1) {
                        LoadResult.Page(users.body()?.items ?: emptyList(), page - 1, page + 1)
                    } else {
                        LoadResult.Page(users.body()?.items ?: emptyList(), null, page + 1)
                    }
                } catch (e: Exception) {
                    LoadResult.Page(emptyList(), null, null)
                }
            } else {
                return LoadResult.Page(emptyList(), null, null)
            }
        } catch (e: Exception) {
            return LoadResult.Page(emptyList(), null, null)
        }
    }
}