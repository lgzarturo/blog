package com.lgzarturo.blog.services.impl

import com.lgzarturo.blog.controllers.ApiRouteController
import com.lgzarturo.blog.services.OrderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JpaOrderService : OrderService {

    private val logger: Logger = LoggerFactory.getLogger(JpaOrderService::class.java)

    override fun saveOrder(products: List<ApiRouteController.Product>) {
        logger.info("Guardando la orden en la base de datos")
        var total = 0.0
        products.forEach { product ->
            logger.debug("El nombre del producto es: ${product.name} y el precio: ${product.price}")
            total += product.price
        }
        logger.info("El total de la orden es $total")
    }
}
