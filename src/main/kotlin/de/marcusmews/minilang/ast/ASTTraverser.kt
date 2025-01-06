package de.marcusmews.minilang.ast


/** Traverses the given program and calls callback methods of given visitors */
fun traverse(programInfo: ProgramInfo, visitors: List<ASTVisitor>) {
    dispatchVisit(visitors, programInfo.program, null, null)
    traverse(programInfo.program) { node, parent, parentProperty -> dispatchVisit(visitors, node, parent, parentProperty) }
}

private fun traverse(node: SourceElement?, onVisit: (node: SourceElement?, parent: SourceElement?, parentProperty: String?) -> Unit) {
    if (node == null) {
        return
    }
    val nodeClass = node::class
    val properties = nodeClass.members.filterIsInstance<kotlin.reflect.KProperty1<Any, *>>()

    for (property in properties) {
        val child: Any?
        try {
            child = property.get(node)
        } catch (e: Exception) {
            println("Error accessing property '${property.name}' of node ${nodeClass.simpleName}: ${e.message}")
            continue
        }
        when (child) {
            is List<*> -> {
                for (sibling in child) {
                    if (sibling is SourceElement) {
                        onVisit(sibling, node, property.name)
                        traverse(sibling, onVisit)
                    } else {
                        throw IllegalArgumentException("Unsupported element found in AST")
                    }
                }
            }
            is SourceElement -> {
                onVisit(child, node, property.name)
                traverse(child, onVisit)
            }
            null        -> {
                onVisit(null, node, property.name)
            }
            is Operator -> {/* ignore */}
            is Double   -> {/* ignore */}
            is String   -> {/* ignore */}
            else        -> {
                throw IllegalArgumentException("Unsupported element found in AST")
            }
        }
    }
}

private fun dispatchVisit(visitors: List<ASTVisitor>, node: SourceElement?, parent: SourceElement?, parentProperty: String?) {
    for (visitor in visitors) {
        if (node == null) {
            if (parent != null && parentProperty != null) {
                visitor.onNullChild(parent, parentProperty)
            } else {
                throw IllegalArgumentException("Unsupported case: Node and parent are null")
            }
        } else {
            // respects class hierarchy
            when (node) {
                is Program -> visitor.onProgram(node, parent, parentProperty)
                is VariableDeclaration -> visitor.onVariableDeclaration(node, parent, parentProperty)
                is OutputStatement -> visitor.onOutputStatement(node, parent, parentProperty)
                is PrintStatement -> visitor.onPrintStatement(node, parent, parentProperty)
                is BinaryOperation -> visitor.onBinaryOperation(node, parent, parentProperty)
                is ParenthesizedExpression -> visitor.onParenthesizedExpression(node, parent, parentProperty)
                is IdentifierExpression -> visitor.onIdentifierExpression(node, parent, parentProperty)
                is NumberLiteral -> visitor.onNumberLiteral(node, parent, parentProperty)
                is SequenceExpression -> visitor.onSequenceExpression(node, parent, parentProperty)
                is MapExpression -> visitor.onMapExpression(node, parent, parentProperty)
                is ReduceExpression -> visitor.onReduceExpression(node, parent, parentProperty)
                else -> throw IllegalArgumentException("Unsupported node type: ${node.let { it::class.simpleName } ?: "Unknown node"}")
            }
            when (node) {
                is Statement -> visitor.onStatement(node, parent, parentProperty)
                is Expression -> visitor.onExpression(node, parent, parentProperty)
            }
            visitor.onSourceElement(node, parent, parentProperty)
        }
    }
}
