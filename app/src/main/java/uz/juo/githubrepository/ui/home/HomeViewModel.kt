package uz.juo.githubrepository.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.juo.githubrepository.models.Item
import uz.juo.githubrepository.retrofit.ApiHelper
import uz.juo.githubrepository.retrofit.ApiService
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    var apiService: ApiService
) : ViewModel() {
    fun searchRepos(str: String, context: Context) =
        Pager(PagingConfig(pageSize = 20)) {
            ApiHelper(
                str,
                apiService,
                context = context
            )
        }.flow.cachedIn(
            viewModelScope
        ).asLiveData()
}