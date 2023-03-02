package com.lgzarturo.blog.services

import com.lgzarturo.blog.controllers.ApiRouteController

interface OrderService {
    fun saveOrder(products: List<ApiRouteController.Product>)
}
