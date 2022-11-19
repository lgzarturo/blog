package com.lgzarturo.blog.controllers

import com.lgzarturo.blog.exceptions.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class HandleExceptionController {

    @ExceptionHandler(EmailAlreadyRegisteredException::class)
    fun handleEmailAlreadyRegistered(
        ex: EmailAlreadyRegisteredException
    ): ResponseEntity<ErrorException> = ResponseEntity(ErrorException(ex), HttpStatus.CONFLICT)

    @ExceptionHandler(RegistrationUserException::class)
    fun handleRegistrationUser(
        ex: RegistrationUserException
    ) : ResponseEntity<ErrorException> = ResponseEntity.badRequest().body(ErrorException(ex))

    @ExceptionHandler(UserDoesNotExistException::class)
    fun handleUserDoesNotExist(
        ex: UserDoesNotExistException
    ) : ResponseEntity<ErrorException> = ResponseEntity(ErrorException(ex), HttpStatus.NOT_FOUND)

    @ExceptionHandler(ConfirmationPasswordNotMatchException::class)
    fun handleConfirmationPasswordNotMatch(
        ex: ConfirmationPasswordNotMatchException
    ) : ResponseEntity<ErrorException> = ResponseEntity.badRequest().body(ErrorException(ex))

}
