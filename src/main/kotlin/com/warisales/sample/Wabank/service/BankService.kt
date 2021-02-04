package com.warisales.sample.Wabank.service

import com.warisales.sample.Wabank.DataSource.BankDataSource
import com.warisales.sample.Wabank.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val bankDataSource: BankDataSource) {

    fun getBanks():Collection<Bank> = bankDataSource.retrieveBanks()
    fun getBank(accountNumber: String): Bank = bankDataSource.getBank(accountNumber)
    fun addBank(bank: Bank): Bank = bankDataSource.addBank(bank)
}