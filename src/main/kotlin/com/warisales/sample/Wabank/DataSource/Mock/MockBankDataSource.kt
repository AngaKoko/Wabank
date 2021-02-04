package com.warisales.sample.Wabank.DataSource.Mock

import com.warisales.sample.Wabank.DataSource.BankDataSource
import com.warisales.sample.Wabank.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {
    var banks = mutableListOf(
        Bank("12345", 55.32, 3),
        Bank("12346", 155.91, 1))

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun getBank(accountNumber: String): Bank =
        banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find bank with account number $accountNumber")

    override fun addBank(bank: Bank): Bank {
        if(banks.any{it.accountNumber == bank.accountNumber}){
            throw IllegalArgumentException("Bank with account number: ${bank.accountNumber} already exist")
        }
        banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {

        val oldBank = banks.firstOrNull{it.accountNumber == bank.accountNumber}
            ?: throw NoSuchElementException("Could not find bank with account number ${bank.accountNumber}")

        banks.remove(oldBank)
        banks.add(bank)

        return bank
    }
}