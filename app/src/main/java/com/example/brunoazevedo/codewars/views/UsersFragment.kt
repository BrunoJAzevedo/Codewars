package com.example.brunoazevedo.codewars.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brunoazevedo.codewars.R/**/
import com.example.brunoazevedo.codewars.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.users_fragment.*

class UsersFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    companion object {
        private const val KEY: String = ""

        @JvmStatic
        fun newInstance(url: String) =
            UsersFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, url)
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.users_fragment, container, false)

        userViewModel = activity?.run {
            ViewModelProviders.of(this).get(UserViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener { this.fetchUser() }
        observeUsers()
    }

    private fun observeUsers() {
        userViewModel.users.observe(this, Observer { users ->
            users?.let {
                tv.text = users[0].username }
        })
    }

    fun fetchUser() {
        userViewModel.fetchUser("BrunoJAzevedo94")
    }
}