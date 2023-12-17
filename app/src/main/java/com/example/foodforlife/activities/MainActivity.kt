package com.example.foodforlife.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodforlife.R
import com.example.foodforlife.databinding.ActivityMainBinding
import com.example.foodforlife.db.MealDataBase
import com.example.foodforlife.viewModel.HomeViewModel
import com.example.foodforlife.viewModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val viewModel:HomeViewModel by lazy {
        val mealDataBase = MealDataBase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDataBase)
        ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController(this, R.id.frgmnt)

        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}