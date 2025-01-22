package de.marcusmews.minilang.tests.utils

import de.marcusmews.minilang.Core
import de.marcusmews.minilang.interpreter.Interpreter
import de.marcusmews.minilang.interpreter.RuntimeError
import de.marcusmews.minilang.interpreter.valueToString
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

fun run(sourceCode: String) : RunResult {
    val core = Core(sourceCode).parse().validate()
    assertTrue(core.getIssues().isEmpty(), "Cannot run code due to ${core.getIssues().size} issues")
    val interpreter = Interpreter(core.getProgramInfo()!!.program).executeProgram()
    return RunResult(interpreter.getOutput(), interpreter.getError(), interpreter.getVariables())
}

class RunResult(private val output: String? = null, private val error: RuntimeError? = null, private val variables: Map<String, Any?> = emptyMap()) {
    fun assertNoOutput() : RunResult {
        assertEquals("", output ?: "")
        return this
    }
    fun assertOutput(expectedOutput: String) : RunResult {
        assertEquals(expectedOutput.trim(), output?.trim())
        return this
    }
    fun assertNoError() : RunResult {
        assertEquals(null, error)
        return this
    }
    fun assertError(message: String) : RunResult {
        assertNotNull(error, "Error expected but none found")
        if (message.endsWith("...")) {
            assertTrue(error.toString().startsWith(message.substring(0, message.length - 3)), "Expected error to start with '$message' but was $error")
        } else {
            assertEquals(message, error.toString())
        }
        return this
    }
    fun assertNoVariables() : RunResult {
        assertTrue(variables.isEmpty())
        return this
    }
    fun assertVariables(expectedVariables: Map<String, String>) : RunResult {
        val actualValues = variables.mapValues { value -> valueToString(value.value) }
        assertEquals(expectedVariables, actualValues)
        return this
    }
}