package br.thiago.calculator

import br.thiago.core.StartAppium
import br.thiago.pages.calculator.Calculator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CalculatorTest : StartAppium() {

    private lateinit var calculator: Calculator

    @Test
    fun sumNumberCalculator() {
        calculator = Calculator()
        val num1 = 3
        val num2 = 5

        val result = calculator.sum(num1, num2)
        Assertions.assertEquals(num1 + num2, result.toInt())
    }
}