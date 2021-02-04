package com.warisales.sample.Wabank.DataSource

import com.warisales.sample.Wabank.model.Bank

interface BankDataSource {

    fun retrieveBanks():Collection<Bank>
    fun getBank(accountNumber: String): Bank
    fun addBank(bank: Bank): Bank
}