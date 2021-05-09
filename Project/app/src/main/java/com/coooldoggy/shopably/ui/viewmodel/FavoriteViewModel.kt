package com.coooldoggy.shopably.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coooldoggy.shopably.data.FavoriteEntity
import com.coooldoggy.shopably.data.Goods
import com.coooldoggy.shopably.data.repository.HomeRepository
import com.coooldoggy.shopably.ui.common.ViewModelEvent
import com.coooldoggy.shopably.ui.view.FavoriteAdapter
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

    val favAdapter = FavoriteAdapter()
    private val _favItemList = MutableLiveData<ArrayList<Goods>>()
    val favItemList: LiveData<ArrayList<Goods>>
        get() = _favItemList
    private val _deletedItem = MutableLiveData<ViewModelEvent<Goods>>()
    val deletedItem : LiveData<ViewModelEvent<Goods>>
        get() = _deletedItem

    init{
        getAllFavorite()
    }

    fun reverseDataStatus(goods: Goods){
        viewModelScope.launch {
            val isFavorite = isFavorite(goods).await()
            if (isFavorite){
                deleteFavorite(goods)
            }else{
                insertFavorite(goods)
            }
        }
    }

    fun getAllFavorite(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
               val result = homeRepository.getAllFavorite()
                val list = ArrayList<Goods>()
                result.forEach {
                    val goods = Goods( id = it.id, name = it.name, image = it.image,
                        actualPrice = it.actualPrice, price = it.price,
                        isNew = it.isNew, sellCount = it.sellCount)
                    list.add(goods)
                }
                _favItemList.postValue(list)
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
                getAllFavorite()
            }
        }
    }

    fun deleteFavorite(goods: Goods){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                homeRepository.deleteFavorite(goods.id)
                getAllFavorite()
            }
            _deletedItem.postValue(ViewModelEvent(goods))
        }
    }

    fun isFavorite(goods: Goods) = viewModelScope.async {
        withContext(Dispatchers.IO){
            val result = homeRepository.isRowExist(goods.id)
            result
        }
    }
}