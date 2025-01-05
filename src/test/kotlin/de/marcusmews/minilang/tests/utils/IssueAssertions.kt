package de.marcusmews.minilang.tests.utils

import de.marcusmews.minilang.Core
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue


fun assertNoErrors(core: Core) {
    val issues = core.getIssues()
    assertEquals(0, issues.size, "Expected 0 issues but found: ${issues.joinToString()}")
}

@Suppress("unused")
fun assertError(core: Core, message: String) {
    val issues = core.getIssues()
    val issueFound = issues.stream().anyMatch { i -> Objects.equals(i.toString(), message) }
    assertTrue(issueFound, "Expected issue not found: $message. Instead found: ${issues.joinToString()}")
}

fun assertErrors(core: Core, vararg messages: String) {
    val issues = core.getIssues()
    assertEquals(messages.size, issues.size, "Issue count mismatch. Issues found: ${issues.joinToString()}")
    val issueMessages = issues.map { i -> i.toString() }.toSet()
    for (message in messages) {
        assertTrue(issueMessages.contains(message), "Expected issue not found: $message. Instead found: ${issueMessages.joinToString()}")
    }
}