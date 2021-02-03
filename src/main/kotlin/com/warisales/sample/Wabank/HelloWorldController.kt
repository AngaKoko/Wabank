package com.warisales.sample.Wabank

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/hello")
class HelloWorldController {

    @GetMapping
    fun helloWorld():String = "Hello my name is Anga, may I know who is speaking?"
}