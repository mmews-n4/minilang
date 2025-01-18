package de.marcusmews.minilang

import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import de.marcusmews.minilang.ast.Range
import de.marcusmews.minilang.interpreter.Interpreter
import de.marcusmews.minilang.ui.appUI
import de.marcusmews.minilang.validation.Issue
import kotlinx.coroutines.*
import java.io.File
import java.util.*
import kotlin.collections.LinkedHashMap


fun main(args: Array<String>) = application {
    val filePath = args.getOrNull(0)
    if (filePath != null) {
        if (!File(filePath).isFile) {
            println("Given file does not exist")
            return@application
        }
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Minilang",
        state = rememberWindowState(width = 1050.dp, height = 700.dp)
    ) {
        appUI(filePath)
    }
}


class MainState(val filePath: String?) {
    private var updateJob: Job? = null
    private var cancelJob: Job? = null
    private var fileContent: String? = null
    val isEditorDirty = mutableStateOf(false)
    val isInterpreterRunning = mutableStateOf(false)
    val sourceCode = mutableStateOf(TextFieldValue(""))
    val output = mutableStateOf("")
    val issueRanges = mutableStateListOf<Range>()
    val issuesOutput = mutableStateListOf<String>()
    val variables = mutableStateOf(LinkedHashMap<String, String>())

    fun reloadFile() {
        val sourceCode = if (filePath == null) {
                // show initial example
                """
                var shortSequence = {1, 3}
                var sequenceOfSeq = map(shortSequence, i -> {0, i})
                
                var n = 500
                var sequence = map({0, n}, i -> -1^i / (2 * i + 1))
                var pi = 4 * reduce(sequence, 0, x y -> x + y)
                print "pi = "
                out pi
                
                """.trimIndent()
            } else {
                try {
                    val content = File(filePath).readText()
                    fileContent = content
                    content
                } catch (e: Exception) {
                    clearIssues()
                    addIssue("Error while loading file: " + e.message)

                    "" // clear due to error
                }
            }

        setSourceCode(TextFieldValue(sourceCode))
    }

    fun saveFile() {
        try {
            val path = filePath ?: "minilang.mlx"
            File(path).writeText(sourceCode.value.text)
            fileContent = sourceCode.value.text
            ensureIsDirty()
        } catch (e: Exception) {
            // Handle errors silently for now
        }
    }

    private fun ensureIsDirty() {
        isEditorDirty.value = !Objects.equals(fileContent, sourceCode.value.text)
    }

    fun setSourceCode(newSourceCodeTFV: TextFieldValue) {
        if (sourceCode.value.text == newSourceCodeTFV.text) {
            sourceCode.value = newSourceCodeTFV // sets cursor
            // no changes
            return
        }

        // set source code and compiler values
        sourceCode.value = newSourceCodeTFV

        ensureIsDirty()
        clearIssues()
        variables.value.clear()
        output.value = ""

        val core = Core(newSourceCodeTFV.text)
        try {
            core.parse()
        } catch (e: Exception) {
            addIssue("Fatal Error during parsing.")
            return
        }
        try {
            core.validate()
        } catch (e: Exception) {
            addIssue("Fatal Error during validation.")
            return
        }
        setIssues(core.getIssues(), true)

        launchInterpreter(core)
    }

    fun cancelUpdate() {
        cancelJob?.cancel()
        cancelJob = null
    }

    private fun launchInterpreter(core: Core) = runBlocking {
        cancelJob?.cancel()
        updateJob?.join()

        cancelJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + cancelJob!!)

        isInterpreterRunning.value = true
        updateJob = scope.launch {
            try {
                runInterpreter(core)
            } catch (e: CancellationException) {
                addIssue("Cancelled: ${e.message}")
            } catch (e: Exception) {
                addIssue("Update Error: ${e.message}")
            } finally {
                isInterpreterRunning.value = false
            }
        }
    }

    private fun runInterpreter(core: Core) {
        if (core.getAst() != null && core.getIssues().isEmpty()) {
            val interpreter = Interpreter(core.getAst()!!, cancelJob)
            try {
                interpreter.executeProgram()
            } catch (e: Exception) {
                addIssue("Fatal Error during execution.")
                return
            }

            if (interpreter.getError() != null) {
                setIssues(listOf(interpreter.getError()!!.asIssue()))
            } else {
                variables.value.putAll(interpreter.getVariables().mapValues {
                    e -> if (e.value is List<*> && (e.value as List<*>).size > 41) {
                        // ui performance
                        StringBuilder().apply {
                            val iter = (e.value as List<*>).iterator()
                            append("[")
                            for (i in 0..41) {
                                if (i > 0) append(", ")
                                append(iter.next())
                            }
                            append(" ... ]")
                        }.toString()
                    } else {
                        e.value.toString()
                    }
                })
                output.value = interpreter.getOutput()
            }
        }
    }

    private fun clearIssues() {
        issueRanges.clear()
        issuesOutput.clear()
    }
    private fun setIssues(newIssues: List<Issue>, clear: Boolean = false) {
        if (clear) clearIssues()

        for (issue in newIssues) {
            issue.range?.let { issueRanges.add(it) }
            issuesOutput.add(issue.toString(1, 0))
        }
    }
    private fun addIssue(message: String) {
        issuesOutput.add(message)
    }
}
