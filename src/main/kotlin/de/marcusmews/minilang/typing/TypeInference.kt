package de.marcusmews.minilang.typing

import de.marcusmews.minilang.ast.*



fun inferredType(expression: Expression?, programInfo: ProgramInfo) : Type {
    return when (expression) {
        null                        -> Type.Unknown
        is NumberLiteral            -> inferredType(expression)
        is SequenceExpression       -> Type.Sequence
        is MapExpression            -> Type.Sequence
        is BinaryOperation          -> inferredType(expression, programInfo)
        is ParenthesizedExpression  -> inferredType(expression.expression, programInfo)
        is ReduceExpression         -> inferredType(expression.initial, programInfo)
        is IdentifierExpression     -> inferredType(expression, programInfo)
    }
}

private fun inferredType(expression: NumberLiteral) : Type {
    return if (expression.value % 1.0 == 0.0) {
        Type.Int
    } else {
        Type.Real
    }
}

private fun inferredType(expression: IdentifierExpression, programInfo: ProgramInfo) : Type {
    val declarationInitializer = getDeclarationInitializer(programInfo, expression)
    return inferredType(declarationInitializer, programInfo)
}

private fun inferredType(expression: BinaryOperation, programInfo: ProgramInfo) : Type {
    val leftType = inferredType(expression.left, programInfo)
    val rightType = inferredType(expression.right, programInfo)
    return when (expression.operator) {
        null                        -> Type.Unknown
        Operator.PLUS               -> inferredTypeBinaryOperationAddition(leftType, rightType)
        Operator.MINUS              -> inferredTypeBinaryOperationSubtraction(leftType, rightType)
        Operator.MULTIPLY           -> inferredTypeBinaryOperationMultiplication(leftType, rightType)
        Operator.DIVIDE             -> leftType
        Operator.POWER              -> leftType
    }
}

private fun inferredTypeBinaryOperationAddition(leftType: Type, rightType: Type) : Type {
    return when {
        leftType == Type.Sequence || rightType == Type.Sequence -> Type.Sequence
        leftType == Type.Real || rightType == Type.Real -> Type.Real
        else -> Type.Int
    }
}

private fun inferredTypeBinaryOperationSubtraction(leftType: Type, rightType: Type) : Type {
    return when {
        leftType == Type.Sequence -> Type.Sequence
        leftType == Type.Real || rightType == Type.Real -> Type.Real
        else -> Type.Int
    }
}

private fun inferredTypeBinaryOperationMultiplication(leftType: Type, rightType: Type) : Type {
    return when {
        leftType == Type.Sequence || rightType == Type.Sequence -> Type.Sequence
        leftType == Type.Real || rightType == Type.Real -> Type.Real
        else -> Type.Int
    }
}
