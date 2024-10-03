package com.example.bnetest.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.bnetest.data.BNetApi
import com.example.bnetest.data.Drugs
import com.example.bnetest.domain.BNetListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: BNetListRepository
) : ViewModel() {

    fun getDrugsList(searchQuery: String?) = Pager(PagingConfig(pageSize = 10)) {
        CardDrugsPagingSource(repository, searchQuery)
    }.flow.cachedIn(viewModelScope)



}