package com.example.foodforlife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodforlife.R
import com.example.foodforlife.activities.MainActivity
import com.example.foodforlife.adapters.CategoriesAdapter
import com.example.foodforlife.adapters.CategoryMealsAdapter
import com.example.foodforlife.databinding.FragmentCategoryBinding
import com.example.foodforlife.pojo.Category
import com.example.foodforlife.viewModel.HomeViewModel


class CategoryFragment : Fragment() {

    private lateinit var binding:FragmentCategoryBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var categoriesAdapter:CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCategories()
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories->
            categoriesAdapter.setCategoriesList(categories as ArrayList<Category>)
        })
    }

}