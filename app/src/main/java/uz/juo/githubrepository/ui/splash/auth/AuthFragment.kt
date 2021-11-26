package uz.juo.githubrepository.ui.splash.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.juo.githubrepository.databinding.FragmentAuthBinding
import uz.juo.githubrepository.models.UserLoginReq
import uz.juo.githubrepository.ui.main.MainActivity
import uz.juo.githubrepository.ui.splash.SplashActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class AuthFragment : Fragment() {
    val viewModel: AuthViewModel by viewModels()
    var _binding: FragmentAuthBinding? = null
    val binding get() = _binding!!
    private var param1: String? = null
    private var param2: String? = null

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
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        val root = binding.root
        login()
        return root
    }

    fun login() {
        var res = viewModel.login(UserLoginReq("JasurbekOripov", "J@ck2777"))
        res.observe(viewLifecycleOwner, {
            var token = it.token
            var data = viewModel.getData(token)
            Log.d("TAG", "login12: $data")

        })
        SplashActivity().finish()
        var intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AuthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}