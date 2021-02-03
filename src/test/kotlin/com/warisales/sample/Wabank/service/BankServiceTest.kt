package com.warisales.sample.Wabank.service

import com.warisales.sample.Wabank.DataSource.BankDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class BankServiceTest{

    val bankDataSource: BankDataSource = mockk(relaxed = true)
    val bankService = BankService(bankDataSource)
    
    @Test
    fun should_call_its_data_source_to_receive_banks(){
        //given
        //every { bankDataSource.retrieveBanks() } returns emptyList()
        
        //when
        bankService.getBanks()
        
        //then
        verify(exactly = 1) { bankDataSource.retrieveBanks() }
    }
}