package com.example.brunoazevedo.codewars

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.brunoazevedo.codewars.utils.userString
import com.example.brunoazevedo.codewars.views.users.UsersFragment

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, UsersFragment.newInstance(
            userString), userString)?.addToBackStack(null)?.commit()
    }
}
