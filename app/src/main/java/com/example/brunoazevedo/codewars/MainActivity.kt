package com.example.brunoazevedo.codewars

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.brunoazevedo.codewars.model.User
import com.example.brunoazevedo.codewars.utils.userString
import com.example.brunoazevedo.codewars.viewmodel.UserViewModel
import com.example.brunoazevedo.codewars.views.challenges.ChallengesActivity
import com.example.brunoazevedo.codewars.views.users.UsersAdapter
import kotlinx.android.synthetic.main.users_fragment.*

class MainActivity : AppCompatActivity(){

    private lateinit var userViewModel: UserViewModel
    private lateinit var usersListAdapter : UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        userViewModel.init()

        val listener = object:UsersAdapter.OnItemClickListener {
            override fun onItemClick(user: User?) {
                launchChallengesActivity(user?.username)
            }
        }

        this.usersListAdapter = UsersAdapter(arrayListOf(), listener)


        users_recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersListAdapter
        }

        observeUsers()
    }

    override fun onDestroy() {
        super.onDestroy()
        closeOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        val item = menu?.findItem(R.id.search)
        val sv = SearchView(this.supportActionBar?.themedContext)
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

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_order_by_search_time -> orderBySearchTime()
            R.id.menu_order_by_rank -> orderByRank()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeUsers() {
        userViewModel.users.observe(this, Observer { users ->
            users?.let {
                usersListAdapter.updateUsers(it) }
        })

        userViewModel.loadError.observe(this, Observer { isError ->
            isError?.let {
                if (it) {
                    //Toast.makeText(, context?.resources?.getText(R.string.error_text),
                    //    Toast.LENGTH_LONG).show()
                }
            }
        })

        userViewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
    }

    private fun launchChallengesActivity(name : String?) {
        val intent = Intent(this, ChallengesActivity::class.java)
        intent.putExtra(userString, name)
        startActivity(intent)
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
