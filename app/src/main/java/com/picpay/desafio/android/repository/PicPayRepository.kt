package com.picpay.desafio.android.repository

import com.picpay.desafio.android.api.PicPayService

class PicPayRepository(private val api: PicPayService) {
    fun getUsers() = api.getUsers()
}