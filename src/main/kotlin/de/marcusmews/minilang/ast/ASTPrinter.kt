package de.marcusmews.minilang.ast


class ASTPrinter {

    /** Creates a string from the given program. */
    fun generate(program: Program): String {
        return program.statements.joinToString("\n") { generateStatement(it) }
    }

    private fun generateStatement(statement: Statement): String {
        return when (statement) {
            is VariableDeclaration -> "var ${statement.identifier?.name} = ${generateExpression(statement.expression)}"
            is OutputStatement -> "out ${generateExpression(statement.expression)}"
            is PrintStatement -> "print \"${statement.string?.value}\""
        }
    }

    private fun generateExpression(expression: Expression?): String {
        if (expression == null) {
            return ""
        }
        return when (expression) {
            is BinaryOperation          -> "${generateExpression(expression.left)} ${operatorToString(expression.operator)} ${generateExpression(expression.right)}"
            is ParenthesizedExpression  -> "(${generateExpression(expression.expression)})"
            is IdentifierExpression     -> expression.name
            is NumberLiteral            -> expression.value.toString()
            is SequenceExpression       -> "{${generateExpression(expression.start)}, ${generateExpression(expression.end)}}"
            is MapExpression            -> "map(${generateExpression(expression.sequence)}, ${expression.parameter?.name} -> ${generateExpression(expression.body)})"
            is ReduceExpression         -> "reduce(${generateExpression(expression.sequence)}, ${generateExpression(expression.initial)}, ${expression.param1?.name} ${expression.param2?.name} -> ${generateExpression(expression.body)})"
        }
    }

    private fun operatorToString(operator: Operator?): String {
        if (operator == null) {
            return ""
        }
        return when (operator) {
            Operator.PLUS -> "+"
            Operator.MINUS -> "-"
            Operator.MULTIPLY -> "*"
            Operator.DIVIDE -> "/"
            Operator.POWER -> "^"
        }
    }
}
