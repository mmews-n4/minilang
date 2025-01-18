package de.marcusmews.minilang.validation

import de.marcusmews.minilang.ast.*
import de.marcusmews.minilang.typing.*

/**
 * Contains validations regarding type relations
 */
class TypeValidator(issueHandler: IssueHandler, programInfo: ProgramInfo): BaseValidator(issueHandler, programInfo) {

    override fun onSourceElement(node: SourceElement, parent: SourceElement?, partType: SourceElementPart?) {
        checkExpectedTypesStatic(node)
    }

    override fun onBinaryOperation(node: BinaryOperation, parent: SourceElement?, partType: SourceElementPart?) {
        // check subtype of right hand side implied by left hand side
        val lhsTypeInferred = inferredType(node.left, programInfo)
        val rhsTypeExpected = when (node.operator) {
            Operator.MINUS -> expectedTypeOfBinaryOperationSubtraction(SourceElementPart.Right, lhsTypeInferred)
            Operator.MULTIPLY -> expectedTypeOfBinaryOperationMultiplication(SourceElementPart.Right, lhsTypeInferred)
            else -> Type.Any
        }
        if (rhsTypeExpected != Type.Any && rhsTypeExpected != Type.Unknown) {
            val rhsTypeInferred = inferredType(node.right, programInfo)
            if (!rhsTypeInferred.isSubtypeOf(rhsTypeExpected)) {
                if (rhsTypeInferred == Type.Unknown) {
                    // ignore since this is covered by other validations
                } else {
                    reportErrorTypesIncompatible(node, rhsTypeExpected, rhsTypeInferred)
                }
            }
        }
    }

    private fun checkExpectedTypesStatic(node: SourceElement) {
        val parts = getSourceElementParts(node)
        for ((elemPartType, elem) in parts) {
            if (elem is Expression) {
                val partTypeExpected = expectedType(node, elemPartType)
                if (partTypeExpected != Type.Any && partTypeExpected != Type.Unknown) {
                    val partTypeInferred = inferredType(elem, programInfo)
                    if (!partTypeInferred.isSubtypeOf(partTypeExpected)) {
                        if (partTypeInferred == Type.Unknown) {
                            // ignore since this is covered by other validations
                        } else {
                            reportErrorTypesIncompatible(elem, partTypeExpected, partTypeInferred)
                        }
                    }
                }
            }
        }
    }

    private fun reportErrorTypesIncompatible(elem: SourceElement, expectedType: Type, inferredType: Type) {
        reportError(elem, "Types incompatible: Expected type is $expectedType while given type was $inferredType.")
    }
}