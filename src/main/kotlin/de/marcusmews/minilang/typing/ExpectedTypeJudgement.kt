package de.marcusmews.minilang.typing

import de.marcusmews.minilang.ast.*



fun expectedType(expression: SourceElement?, part: SourceElementPart? = null) : Type {
    return when (expression) {
        null                        -> Type.Unknown
        // is PrintStatement        -> checked by grammar
        is OutputStatement          -> Type.Any
        is SequenceExpression       -> Type.Int // both: start and end
        is MapExpression            -> expectedTypeOfMapExpression(part)
        is ReduceExpression         -> expectedTypeOfReduceExpression(part)
        is BinaryOperation          -> expectedTypeOfBinaryOperation(expression, part)
        else                        -> Type.Any
    }
}

private fun expectedTypeOfMapExpression(part: SourceElementPart?) : Type {
    return when (part) {
        null                        -> Type.Unknown
        SourceElementPart.Sequence  -> Type.Sequence
        // SourceElementPart.Parameter -> checked by grammar
        else                        -> Type.Any
    }
}

private fun expectedTypeOfReduceExpression(part: SourceElementPart?) : Type {
    return when (part) {
        null                        -> Type.Unknown
        SourceElementPart.Sequence  -> Type.Sequence
        SourceElementPart.Initial   -> Type.Any
        // SourceElementPart.Param1 -> checked by grammar
        // SourceElementPart.Param2 -> checked by grammar
        else                        -> Type.Any
    }
}

private fun expectedTypeOfBinaryOperation(expression: BinaryOperation, part: SourceElementPart?) : Type {
    return when (expression.operator) {
        null                        -> Type.Unknown
        Operator.PLUS               -> Type.Any
        Operator.MINUS              -> expectedTypeOfBinaryOperationSubtraction(part)
        Operator.MULTIPLY           -> expectedTypeOfBinaryOperationMultiplication(part)
        Operator.DIVIDE             -> expectedTypeOfBinaryOperationDivision(part)
        Operator.POWER              -> expectedTypeOfBinaryOperationPower(part)
    }
}

fun expectedTypeOfBinaryOperationSubtraction(part: SourceElementPart?, lhsType: Type? = null): Type {
    return when (part) {
        null                        -> Type.Unknown
        SourceElementPart.Left      -> Type.Any
        SourceElementPart.Right     ->
            when (lhsType) {
                Type.Int            -> Type.Real
                Type.Real           -> Type.Real
                else                -> Type.Any
            }
        else                        -> Type.Any
    }
}

fun expectedTypeOfBinaryOperationMultiplication(part: SourceElementPart?, lhsType: Type? = null): Type {
    return when (part) {
        null                        -> Type.Unknown
        SourceElementPart.Left      -> Type.Any
        SourceElementPart.Right     ->
            when (lhsType) {
                Type.Sequence       -> Type.Real
                else                -> Type.Any
            }
        else                        -> Type.Any
    }
}

private fun expectedTypeOfBinaryOperationDivision(part: SourceElementPart?): Type {
    return when (part) {
        null                        -> Type.Unknown
        SourceElementPart.Left      -> Type.Any
        SourceElementPart.Right     -> Type.Real
        else                        -> Type.Any
    }
}

private fun expectedTypeOfBinaryOperationPower(part: SourceElementPart?): Type {
    return when (part) {
        null                        -> Type.Unknown
        SourceElementPart.Left      -> Type.Any
        SourceElementPart.Right     -> Type.Real
        else                        -> Type.Any
    }
}