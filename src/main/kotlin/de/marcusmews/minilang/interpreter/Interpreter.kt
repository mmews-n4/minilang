package de.marcusmews.minilang.interpreter

import de.marcusmews.minilang.analysis.isAssociative
import de.marcusmews.minilang.ast.*
import de.marcusmews.minilang.validation.Issue
import kotlinx.coroutines.*
import kotlin.math.pow


data class RuntimeError(val sourceElement: SourceElement, override val message: String): Throwable() {
    override fun toString(): String {
        val position = "(${sourceElement.location.start.line}, ${sourceElement.location.start.character})"
        return "Runtime Error $position: $message"
    }
    fun asIssue() : Issue {
        return Issue(IssueKind.RuntimeError, sourceElement.location, message)
    }
}


fun valueToString(value: Any?) : String {
    return when (value) {
        is String  -> value
        is Double  -> if (value % 1 == 0.0) value.toInt() else value
        is List<*> -> value.map { elem -> valueToString(elem) }
        else       -> ""
    }.toString()
}

class Interpreter(val program: Program, private val job: Job? = null) {
    private val variableStack: MutableMap<String, Any?> = mutableMapOf()
    private var errorResult: RuntimeError? = null
    private var outputResult: String = ""

    /** Execute a program */
    fun executeProgram() : Interpreter {
        try {
            program.statements.forEach { executeStatement(it, variableStack) }
        } catch (e: RuntimeError) {
            errorResult = e
        } catch (e: Throwable) {
            println(e.message)
        }
        return this
    }

    fun getError() : RuntimeError? {
        return errorResult
    }

    fun getOutput() : String {
        return outputResult
    }

    fun getVariables() : Map<String, Any?> {
        return variableStack
    }

    private fun ensureJobActive(sourceElement: SourceElement) {
        Thread.yield()

        if (job != null && !job.isActive) {
            throw RuntimeError(sourceElement, "Cancelled at " + sourceElement.javaClass.simpleName)
        }
        job?.ensureActive() // double check
    }

    private fun println(value: Any?) {
        outputResult += valueToString(value) + "\n"
    }

    /** Execute a statement */
    private fun executeStatement(statement: Statement, variableStack: MutableMap<String, Any?>) {
        when (statement) {
            is VariableDeclaration -> {
                val value = evaluateExpression(statement.expression, variableStack)
                if (statement.identifier != null) {
                    variableStack[statement.identifier.name] = value
                }
            }
            is OutputStatement -> {
                val value = evaluateExpression(statement.expression, variableStack)
                println(value)
            }
            is PrintStatement -> {
                println(statement.string?.value)
            }
        }
    }

    /** Evaluate an expression */
    private fun evaluateExpression(expression: Expression?, variableStack: Map<String, Any?>): Any? {
        return when (expression) {
            is BinaryOperation          -> evaluateBinaryOperation(expression, variableStack)
            is ParenthesizedExpression  -> evaluateExpression(expression.expression, variableStack)
            is IdentifierExpression     -> evaluateIdentifier(expression, variableStack)
            is NumberLiteral            -> expression.value
            is SequenceExpression       -> evaluateSequence(expression, variableStack)
            is MapExpression            -> evaluateMap(expression, variableStack)
            is ReduceExpression         -> evaluateReduce(expression, variableStack)
            null -> null
        }
    }

    private fun evaluateBinaryOperation(expression: BinaryOperation, variableStack: Map<String, Any?>): Any {
        val left = evaluateExpression(expression.left, variableStack)
        val right = evaluateExpression(expression.right, variableStack)

        return when (expression.operator) {
            Operator.PLUS -> handleAddition(expression, left, right)
            Operator.MINUS -> handleSubtraction(expression, left, right)
            Operator.MULTIPLY -> handleMultiplication(expression, left, right)
            Operator.DIVIDE -> handleDivision(expression, left, right)
            Operator.POWER -> handlePower(expression, left, right)
            else -> runtimeError(expression, "Unsupported operator: ${expression.operator}") // prevented by parser
        }
    }

    private fun handleAddition(expression: BinaryOperation, left: Any?, right: Any?): Any {
        return when {
            left is Double && right is Double   -> left + right
            left is List<*> && right is List<*> -> left + right
            left is List<*> && right is Double  -> left + listOf(right)
            left is Double && right is List<*>  -> listOf(left) + right
            else -> runtimeError(expression, "Invalid addition operands: $left, $right") // prevented by validation
        }
    }

    private fun handleSubtraction(expression: BinaryOperation, left: Any?, right: Any?): Any {
        return when {
            left is Double && right is Double   -> left - right
            left is List<*> && right is List<*> -> left.toMutableList().apply {  ensureJobActive(expression);  removeAll(right) }
            left is List<*> && right is Double  -> left.toMutableList().apply {  ensureJobActive(expression);  remove(right) }
            else -> runtimeError(expression, "Invalid subtraction operands: $left, $right") // prevented by validation
        }
    }

    private fun handleMultiplication(expression: BinaryOperation, left: Any?, right: Any?): Any {
        return when {
            left is Double && right is Double   -> left * right
            left is List<*> && right is Double  -> left.map {  ensureJobActive(expression);  if (it is Double) it * right else it }
            right is List<*> && left is Double  -> right.map {  ensureJobActive(expression);  if (it is Double) it * left else it }
            else -> runtimeError(expression, "Invalid multiplication operands: $left, $right") // prevented by validation
        }
    }

    private fun handleDivision(expression: BinaryOperation, left: Any?, right: Any?): Any {
        return when {
            left is Double && right is Double   -> divide(left, right, expression.right ?: expression)
            left is List<*> && right is Double  -> left.map { if (it is Double) divide(it, right, expression.right ?: expression) else it }
            else -> runtimeError(expression, "Invalid division operands: $left, $right") // prevented by validation
        }
    }

    private fun divide(left: Double, right: Double, rightExpression: Expression) : Double {
        ensureJobActive(rightExpression)
        if (right == 0.0) {
            runtimeError(rightExpression, "Division by zero")
        }
        return left / right
    }

    private fun handlePower(expression: BinaryOperation, left: Any?, right: Any?): Any {
        return when {
            left is Double && right is Double   -> Math.pow(left, right)
            left is List<*> && right is Double  -> left.filterIsInstance<Double>().map { ensureJobActive(expression);  it.pow(right) }
            else -> runtimeError(expression, "Invalid power operands: $left, $right") // prevented by validation
        }
    }

    private fun evaluateIdentifier(expression: IdentifierExpression, variableStack: Map<String, Any?>): Any {
        return variableStack[expression.name] ?: runtimeError(expression, "Undefined variable: ${expression.name}")
    }

    private fun evaluateSequence(expression: SequenceExpression, variableStack: Map<String, Any?>): List<Double> {
        val startValue = evaluateExpression(expression.start, variableStack)
        val startDouble = startValue  as? Double ?:
            runtimeError(expression.start ?: expression, "Invalid start of sequence. Value was $startValue")
        val start = if (startDouble % 1 == 0.0) startDouble.toInt() else
            runtimeError(expression.start ?: expression, "Start of sequence must be Int but was $startDouble")
        val endValue = evaluateExpression(expression.end, variableStack)
        val endDouble = endValue as? Double ?:
            runtimeError(expression.end ?: expression, "Invalid end of sequence. Value was $endValue")
        val end = if (endDouble % 1 == 0.0) endDouble.toInt() else
            runtimeError(expression.end ?: expression, "End of sequence must be Int but was $endDouble")
        if (start > end)
            runtimeError(expression, "Start of sequence must be less/equal than end but was $start > $end")
        return (start..end).toList().map { value -> ensureJobActive(expression); value.toDouble() }
    }

    private fun evaluateMap(expression: MapExpression, variableStack: Map<String, Any?>): List<Any?> = runBlocking {
        val sequence = evaluateExpression(expression.sequence, variableStack) as? List<*> ?:
            runtimeError(expression.sequence ?: expression, "Invalid sequence for map") // prevented by validation
        val parameter = expression.parameter ?:
            runtimeError(expression, "Map parameter missing") // prevented by parser

        sequence.map { item ->
            async {
                ensureJobActive(expression)

                val newVariableStack = variableStack + (parameter.name to item)
                evaluateExpression(expression.body, newVariableStack)
            }
        }.awaitAll()
    }

    private fun evaluateReduce(expression: ReduceExpression, variableStack: Map<String, Any?>): Any {
        val sequence = evaluateExpression(expression.sequence, variableStack) as? List<Any?> ?:
            runtimeError(expression.sequence ?: expression, "Invalid sequence for reduce") // prevented by validation
        val initial = evaluateExpression(expression.initial, variableStack) ?:
            runtimeError(expression.initial ?: expression, "Invalid initial for reduce") // prevented by validation
        val param1 = expression.param1 ?: runtimeError(expression, "Reduce parameter 1 missing") // prevented by parser
        val param2 = expression.param2 ?: runtimeError(expression, "Reduce parameter 2 missing") // prevented by parser

        return if (isAssociative(expression.body)) {
            // parallel version
            val initialPlusSequence = listOf(initial) + sequence
            initialPlusSequence.parallelStream().reduce { acc, item ->
                evaluateReduceBody(expression, variableStack, param1.name, param2.name, acc, item)
            }.get()
        } else {
            // sequential version
            sequence.fold(initial) { acc, item ->
                evaluateReduceBody(expression, variableStack, param1.name, param2.name, acc, item)
            }
        }
    }

    private fun evaluateReduceBody(expression: ReduceExpression, variableStack: Map<String, Any?>, param1: String, param2: String, acc: Any?, item: Any?) : Any {
        ensureJobActive(expression)

        val newVariableStack = variableStack + (param1 to acc) + (param2 to item)
        //println("reduce body with stack $newVariableStack")
        return evaluateExpression(expression.body, newVariableStack) ?:
            runtimeError(expression, "Invalid reduce body result")
    }

    @Throws(Throwable::class)
    private fun runtimeError(sourceElement: SourceElement, message: String): Nothing {
        throw RuntimeError(sourceElement, message)
    }
}
