package de.marcusmews.minilang.ast


/** Traverses the given program and calls callback methods of given visitors */
fun traverse(programInfo: ProgramInfo, visitors: List<ASTVisitor>) {
    dispatchVisit(visitors, programInfo.program, null, null)
    traverseSourceElements(programInfo.program) { node, parent, partType -> dispatchVisit(visitors, node, parent, partType) }
}

/** Traverses all children that are SourceElements */
private fun traverseSourceElements(node: SourceElement?, onVisit: (node: SourceElement?, parent: SourceElement?, partType: SourceElementPart?) -> Unit) {
    if (node == null) {
        return
    }

    val parts = getSourceElementParts(node)
    for ((partType, elem) in parts) {
        when (elem) {
            is List<*> -> {
                for (sibling in elem) {
                    if (sibling is SourceElement) {
                        onVisit(sibling, node, partType)
                        traverseSourceElements(sibling, onVisit)
                    } else {/* ignore */}
                }
            }
            is SourceElement -> {
                onVisit(elem, node, partType)
                traverseSourceElements(elem, onVisit)
            }
            null        -> {
                onVisit(null, node, partType)
            }
            is Operator -> {/* ignore */}
            is Double   -> {/* ignore */}
            is Int      -> {/* ignore */}
            is String   -> {/* ignore */}
            else        -> {/* ignore */}
        }
    }
}

private fun dispatchVisit(visitors: List<ASTVisitor>, node: SourceElement?, parent: SourceElement?, partType: SourceElementPart?) {
    for (visitor in visitors) {
        if (node == null) {
            if (parent != null && partType != null) {
                visitor.onNullChild(parent, partType)
            } else {
                throw IllegalArgumentException("Unsupported case: Node and parent are null")
            }
        } else {
            // respects class hierarchy
            when (node) {
                is Program                  -> visitor.onProgram(node, parent, partType)
                is VariableDeclaration      -> visitor.onVariableDeclaration(node, parent, partType)
                is OutputStatement          -> visitor.onOutputStatement(node, parent, partType)
                is PrintStatement           -> visitor.onPrintStatement(node, parent, partType)
                is BinaryOperation          -> visitor.onBinaryOperation(node, parent, partType)
                is ParenthesizedExpression  -> visitor.onParenthesizedExpression(node, parent, partType)
                is IdentifierExpression     -> visitor.onIdentifierExpression(node, parent, partType)
                is NumberLiteral            -> visitor.onNumberLiteral(node, parent, partType)
                is SequenceExpression       -> visitor.onSequenceExpression(node, parent, partType)
                is MapExpression            -> visitor.onMapExpression(node, parent, partType)
                is ReduceExpression         -> visitor.onReduceExpression(node, parent, partType)
                is StringLiteral            -> visitor.onStringLiteral(node, parent, partType)
                is Identifier               -> visitor.onIdentifier(node, parent, partType)
            }
            when (node) {
                is Statement    -> visitor.onStatement(node, parent, partType)
                is Expression   -> visitor.onExpression(node, parent, partType)
                else            -> {/* add further super types here if necessary */}
            }
            visitor.onSourceElement(node, parent, partType)
        }
    }
}
