package com.example.brunoazevedo.codewars.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.*
import com.example.brunoazevedo.codewars.MainActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.options_menu, menu)
        val item = menu?.findItem(R.id.search)
        val sv = SearchView((activity as MainActivity).supportActionBar?.themedContext)
        MenuItemCompat.setShowAsAction(
            item,
            MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItemCompat.SHOW_AS_ACTION_IF_ROOM
        )
        MenuItemCompat.setActionView(item, sv)
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                userViewModel.fetchUser(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                println("tap")
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}