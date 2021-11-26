package uz.juo.githubrepository.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.FadingCircle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import uz.juo.githubrepository.adapter.RepositoryAdapter
import uz.juo.githubrepository.databinding.FragmentHomeBinding
import uz.juo.githubrepository.models.Item
import android.content.Intent
import android.net.Uri
import com.google.android.material.snackbar.Snackbar
import uz.juo.githubrepository.utils.NetworkHelper


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var searchJob: Job? = null
    lateinit var reposAdapter: RepositoryAdapter
    val mainViewModel: HomeViewModel by viewModels()
    var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!
    private var param1: String? = null
    private var param2: String? = null
    lateinit var networkHepler: NetworkHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        networkHepler = NetworkHelper(requireContext())
        val progressBar = binding.progress as ProgressBar
        val doubleBounce: Sprite = FadingCircle()
        progressBar.indeterminateDrawable = doubleBounce
        binding.rv.visibility = View.INVISIBLE
        loadData("a")
        showLoading()
        reposAdapter = RepositoryAdapter(requireContext(), object : RepositoryAdapter.itemClick {
            override fun setOnClick(data: Item, position: Int) {
                val url = data.html_url
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        })
        binding.rv.adapter = reposAdapter

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length ?: 0 > 2) {
                    showLoading()
                    searchJob?.cancel()
                    searchJob = coroutineScope.launch {
                        s?.let {
                            delay(500L)
                            Log.d(TAG, "onQueryTextChange: $s")
                            loadData(s.toString() ?: "")
                        }
                    }
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        return binding.root
    }

    fun loadData(str: String) {
        if (networkHepler.isNetworkConnected()) {
            mainViewModel.searchRepos(str, requireContext()).observe(viewLifecycleOwner, {
                lifecycleScope.launch {
                    hideLoading()
                    reposAdapter.submitData(it)
                }
            })
        } else {
            Snackbar.make(requireView(), "No internet connection", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun hideLoading() {
        Log.d(TAG, "hideLoading: hide")
        binding.rv.visibility = View.VISIBLE
        binding.progress.visibility = View.INVISIBLE
    }

    fun showLoading() {
        Log.d(TAG, "showLoading: show")
        binding.rv.visibility = View.INVISIBLE
        binding.progress.visibility = View.VISIBLE
    }
}