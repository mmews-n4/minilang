package de.marcusmews.minilang.ast

abstract class ASTVisitor {

    open fun onNullChild(parent: SourceElement, partType: SourceElementPart) {}

    open fun onSourceElement(node: SourceElement, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onStatement(node: Statement, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onExpression(node: Expression, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onProgram(node: Program, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onVariableDeclaration(node: VariableDeclaration, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onOutputStatement(node: OutputStatement, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onPrintStatement(node: PrintStatement, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onBinaryOperation(node: BinaryOperation, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onParenthesizedExpression(node: ParenthesizedExpression, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onIdentifierExpression(node: IdentifierExpression, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onNumberLiteral(node: NumberLiteral, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onSequenceExpression(node: SequenceExpression, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onMapExpression(node: MapExpression, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onReduceExpression(node: ReduceExpression, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onStringLiteral(node: StringLiteral, parent: SourceElement?, partType: SourceElementPart?) {}

    open fun onIdentifier(node: Identifier, parent: SourceElement?, partType: SourceElementPart?) {}

}