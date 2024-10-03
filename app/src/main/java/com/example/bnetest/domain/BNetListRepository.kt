package com.example.bnetest.domain

import com.example.bnetest.data.Drugs
import com.example.bnetest.data.retrofit
import javax.inject.Inject

class BNetListRepository @Inject constructor() {

    suspend fun getCardMedication(id: Int): Drugs {
        return retrofit.getCardMedication(id)
    }


    suspend fun getListMedication(search: String?, offset: Int, limit: Int): List<Drugs> {
        return retrofit.getListMedication(search = search, offset = offset, limit = limit)
    }
}