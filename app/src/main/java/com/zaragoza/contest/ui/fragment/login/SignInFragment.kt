package com.zaragoza.contest.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zaragoza.contest.R
import com.zaragoza.contest.databinding.FragmentSignInBinding
import com.zaragoza.contest.ui.MainActivity
import com.zaragoza.contest.ui.viewmodel.CheckUserState
import com.zaragoza.contest.ui.viewmodel.UserViewModel
import com.zaragoza.contest.utils.ResourceState
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        _binding?.btnActionSignIn?.setOnClickListener {
            checkUser()
        }

        binding.btnRegisterSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_registerFragment)
        }


    }

    private fun initViewModel() {
        userViewModel.checkUserLiveData.observe(viewLifecycleOwner) { state ->
            handleCheckUserState(state)
        }
    }

    private fun handleCheckUserState(state: CheckUserState) {
        when (state) {
            is ResourceState.Loading -> {
                //
            }

            is ResourceState.Success -> {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }

            is ResourceState.Error -> {
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
            }

            is ResourceState.None -> {
                //
            }
        }
    }

    private fun checkUser() {
        val userPassword = binding.tilInputPasswordRegister.text.toString()
        val userEmail = binding.tilInputMailRegister.text.toString()

        userViewModel.checkUser(userEmail = userEmail, userPassword = userPassword)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}