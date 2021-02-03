package com.warisales.sample.Wabank.DataSource.Mock

import com.warisales.sample.Wabank.DataSource.BankDataSource
import com.warisales.sample.Wabank.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {
    var banks = listOf(
        Bank("12345", true, 3),
        Bank("12346", false, 1))

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun getBank(accountNumber: String): Bank =
        banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find bank with account number $accountNumber")
}