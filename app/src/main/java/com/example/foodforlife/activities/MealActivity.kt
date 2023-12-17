package com.example.foodforlife.activities

import android.content.Intent
import android.net.InetAddresses
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodforlife.R
import com.example.foodforlife.databinding.ActivityMealBinding
import com.example.foodforlife.db.MealDataBase
import com.example.foodforlife.fragments.HomeFragment
import com.example.foodforlife.pojo.Meal
import com.example.foodforlife.viewModel.HomeViewModel
import com.example.foodforlife.viewModel.MealViewModel
import com.example.foodforlife.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding:ActivityMealBinding
    private lateinit var ytLink:String
    private lateinit var mealMvvm:MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMealBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)


        val mealDataBase = MealDataBase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDataBase)
        mealMvvm = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

        getMealInfoFromIntent()
        setInfoInViews()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observeMealDetailsLiveData()

        onYoutubeImageClick()
        onFavouriteClick()

    }

    private fun onFavouriteClick() {
        binding.btnAddFvrt.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this,"Meal Saved",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imageYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ytLink))
            startActivity(intent)
        }
    }

    private var mealToSave:Meal?= null
    private fun observeMealDetailsLiveData() {
        mealMvvm.observeMealDetailLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal = t
                mealToSave = meal

                binding.tvCategory.text = "Category: ${meal!!.strCategory}"
                binding.tvArea.text = "Area: ${meal.strArea}"
                binding.instructions.text = meal.strInstructions

                ytLink = meal.strYoutube!!
            }

        })
    }

    private fun setInfoInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddFvrt.visibility = View.INVISIBLE
        binding.instructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imageYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddFvrt.visibility = View.VISIBLE
        binding.instructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imageYoutube.visibility = View.VISIBLE
    }

}