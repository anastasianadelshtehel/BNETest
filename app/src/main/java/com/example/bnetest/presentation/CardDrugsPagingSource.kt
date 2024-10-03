package com.example.bnetest.presentation

import androidx.constraintlayout.utils.widget.MockView
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bnetest.data.Drugs
import com.example.bnetest.domain.BNetListRepository
import javax.inject.Inject

class CardDrugsPagingSource @Inject constructor(
    private val repository: BNetListRepository,
    private val search: String? = null
) : PagingSource<Int, Drugs>() {

    override fun getRefreshKey(state: PagingState<Int, Drugs>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Drugs> {
        val page = params.key ?: 0
        val limit = params.loadSize

        return try {
            val offset = page * limit
            val response = repository.getListMedication(search, offset, limit)

            LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}