package com.coooldoggy.shopably.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coooldoggy.shopably.data.FavoriteEntity
import com.coooldoggy.shopably.data.Goods
import com.coooldoggy.shopably.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {
    private val TAG = FavoriteViewModel::class.java.simpleName

    private val _favItemList = MutableLiveData<ArrayList<Goods>>()
    val favItemList: LiveData<ArrayList<Goods>>
        get() = _favItemList

    init{
        getAllFavorite()
    }

    fun getAllFavorite(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
               val result = homeRepository.getAllFavorite()
                result.forEach {
                    _favItemList.value?.add(
                        Goods( id = it.id, name = it.name, image = it.image,
                            actualPrice = it.actualPrice, price = it.price,
                            isNew = it.isNew, sellCount = it.sellCount
                    ))
                }
            }
        }
    }

    fun insertFavorite(goods: Goods){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                homeRepository.insertFavorite(FavoriteEntity(
                    id = goods.id, name = goods.name, image = goods.image,
                    actualPrice = goods.actualPrice, price = goods.price,
                    isNew = goods.isNew, sellCount = goods.sellCount
                ))
            }
        }
    }

    fun deleteFavorite(goods: Goods){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                homeRepository.deleteFavorite(goods.id)
            }
        }
    }

    fun isFavorite(goods: Goods) = viewModelScope.async {
        withContext(Dispatchers.IO){
            val result = homeRepository.isRowExist(goods.id)
            result
        }
    }
}