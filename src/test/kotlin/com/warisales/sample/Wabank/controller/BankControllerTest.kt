package com.warisales.sample.Wabank.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.warisales.sample.Wabank.model.Bank
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper){

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
    
    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PostNewBank{
        @Test
        fun should_add_the_new_bank(){
            //given
            val bank = Bank("123457", 0.00, 2)
            
            // when
            val performPost = mockMvc.post(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber"){value("123457")}
                }

            mockMvc.get("$baseUrl/${bank.accountNumber}")
                .andDo { print() }
                .andExpect {
                    content { json(objectMapper.writeValueAsString(bank)) }
                }
        }
        
        @Test
        fun should_return_BAD_REQUEST_if_bank_with_ac_number_already_exist(){
            //given
            val invalidBank = Bank("12345", 55.00, 2)
            
            //when
            val performPost = mockMvc.post(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            
            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }
    
    @Nested
    @DisplayName("PATCH /api/bank")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PatchExistingBank{
        @Test
        fun should_update_existing_bank(){
            //given
            val bank = Bank("12345", 100.00, 2)
            
            //when
            val performPatch = mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }
            
            //then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(bank))
                    }
                }

            mockMvc.get("$baseUrl/${bank.accountNumber}")
                .andDo { print() }
                .andExpect {
                    content { json(objectMapper.writeValueAsString(bank)) }
                }
        }

        @Test
        fun should_return_BAS_REQUEST_if_bank_not_found(){
            //given
            val invalidBank = Bank("invalidId", 1.0, 0)

            //when
            val patchRequest = mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //then
            patchRequest
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }
}