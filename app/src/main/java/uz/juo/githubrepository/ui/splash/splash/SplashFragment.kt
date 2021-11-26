package uz.juo.githubrepository.ui.splash.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.juo.githubrepository.R
import uz.juo.githubrepository.databinding.FragmentSplashBinding
import uz.juo.githubrepository.ui.main.MainActivity
import uz.juo.githubrepository.ui.splash.SplashActivity
import uz.juo.githubrepository.utils.SharedPreference

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SplashFragment : Fragment() {

    var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val root = binding.root
        var handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
//            if (isRegistrated()) {
                SplashActivity().finish()
                var intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
//            } else {
//                findNavController().popBackStack()
//                findNavController().navigate(R.id.authFragment)
//            }
        }, 2000)

        return root
    }

    private fun isRegistrated(): Boolean {
        var pref = SharedPreference.getInstance(requireContext())
        return pref.hasReg
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SplashFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}