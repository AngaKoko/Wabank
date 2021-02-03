package com.warisales.sample.Wabank.controller

import com.warisales.sample.Wabank.model.Bank
import com.warisales.sample.Wabank.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/banks")
class BankController(private val service:BankService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNonFoundException(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @GetMapping
    fun retrieveBanks():Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String):Bank = service.getBank(accountNumber)
}