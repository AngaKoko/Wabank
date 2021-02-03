package com.warisales.sample.Wabank.DataSource.Mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest{

    private val mockBankDataSource = MockBankDataSource()

    @Test
    fun should_provide_a_collection_of_banks(){
        //when
        val banks = mockBankDataSource.retrieveBanks()
        //then
        assertThat(banks).isNotEmpty

    }

    @Test
    fun should(){
        //when
        val banks = mockBankDataSource.retrieveBanks()

        //then
        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        assertThat(banks).allMatch { it.transactionFee != 0 }

    }
}