package com.example.foodforlife.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodforlife.db.MealDataBase
import com.example.foodforlife.pojo.Meal
import com.example.foodforlife.pojo.MealList
import com.example.foodforlife.retrofit.retrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
   val mealDataBase: MealDataBase
): ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id:String){
        retrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity",t.message.toString())
            }

        })
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().upsertMeal(meal)
        }
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().delete(meal)
        }
    }

    fun observeMealDetailLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }

}