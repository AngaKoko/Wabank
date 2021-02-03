package com.warisales.sample.Wabank.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.web.bind.annotation.ExceptionHandler

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc
    private val baseUrl = "/api/banks"

    @Nested
    @DisplayName("getBanks()")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBanks{
        @Test
        fun should_return_all_banks(){
            // when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect { status { isOk() } }

        }
    }

    @Nested
    @DisplayName("getBank")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBank{
        @Test
        fun should_return_bank_from_account_number(){
            //given
            val accountNumber = 12345

            //when
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }
        
        @Test
        fun should_account_number_not_found(){
            //given
            val accountNumber = "no_such_account_number"
            
            // when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}