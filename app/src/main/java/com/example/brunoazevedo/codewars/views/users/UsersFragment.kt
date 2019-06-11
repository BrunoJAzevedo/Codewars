package com.example.brunoazevedo.codewars.views.users

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.Toast
import com.example.brunoazevedo.codewars.MainActivity
import com.example.brunoazevedo.codewars.R/**/
import com.example.brunoazevedo.codewars.model.User
import com.example.brunoazevedo.codewars.utils.challengeString
import com.example.brunoazevedo.codewars.utils.userString
import com.example.brunoazevedo.codewars.viewmodel.UserViewModel
import com.example.brunoazevedo.codewars.views.challenges.ChallengesFragment
import kotlinx.android.synthetic.main.users_fragment.*

class UsersFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var usersListAdapter : UsersAdapter

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

        userViewModel.init()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = object:UsersAdapter.OnItemClickListener {
            override fun onItemClick(user: User?) {
                val challengesFragment : Fragment = ChallengesFragment.newInstance(challengeString)
                val args = Bundle()
                args.putString(userString, user?.username)
                challengesFragment.arguments = args

                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_container, challengesFragment,
                    challengeString)?.addToBackStack(null)?.commit()
            }
        }

        this.usersListAdapter = UsersAdapter(arrayListOf(), listener)


        users_recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersListAdapter
        }

        observeUsers()
    }

    private fun observeUsers() {
        userViewModel.users.observe(this, Observer { users ->
            users?.let {
                usersListAdapter.updateUsers(it) }
        })

        userViewModel.loadError.observe(this, Observer { isError ->
            isError?.let {
                if (it) {
                    Toast.makeText(context, context?.resources?.getText(R.string.error_text),
                        Toast.LENGTH_LONG).show()
                }
            }
        })

        userViewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_order_by_search_time -> orderBySearchTime()
            R.id.menu_order_by_rank -> orderByRank()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun orderBySearchTime() : Boolean {
        userViewModel.orderBySearchTime()

        return true
    }

    private fun orderByRank() : Boolean {
        userViewModel.orderByRank()

        return true
    }
}