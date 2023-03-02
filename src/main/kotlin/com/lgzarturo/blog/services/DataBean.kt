package com.lgzarturo.blog.services

import com.lgzarturo.blog.controllers.ApiRouteController
import com.lgzarturo.blog.services.impl.JpaOrderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class DataBean {
    private val logger: Logger = LoggerFactory.getLogger(DataBean::class.java)

    /**
     * Obtener el valor de una propiedad del application.yml
     */
    @Value("\${app.order.mocking}")
    private val mockingData: Boolean = false

    /**
     * Ejemplo para registrar un Bean en el contexto de la aplicación
     */
    @Bean
    fun myBean(): MyBean {
        return MyBean()
    }

    /**
     * Ejemplo para cargar de forma específica un Bean determinado.
     */
    @Bean
    fun orderService(): OrderService {
        if (mockingData) {
            return object : OrderService {
                override fun saveOrder(products: List<ApiRouteController.Product>) {
                    logger.info("Guardando en base de datos dummy")
                }
            }
        }
        return JpaOrderService()
    }
}
