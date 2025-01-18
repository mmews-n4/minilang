package de.marcusmews.minilang.analysis

import de.marcusmews.minilang.ast.*


fun isAssociative(expression: Expression?) : Boolean {
    return when (expression) {
        null                        -> return false
        is IdentifierExpression     -> return true
        is NumberLiteral            -> return true
        is SequenceExpression       -> return true
        is BinaryOperation          -> {
            if (expression.operator == Operator.PLUS || expression.operator == Operator.MULTIPLY) {
                val result1 = isAssociative(expression.left)
                val result2 = isAssociative(expression.right)
                return result1 && result2
            }
            return false
        }
        is ParenthesizedExpression  -> isAssociative(expression.expression)
        is MapExpression            -> isAssociative(expression.body)
        is ReduceExpression         -> isAssociative(expression.body)
    }
}