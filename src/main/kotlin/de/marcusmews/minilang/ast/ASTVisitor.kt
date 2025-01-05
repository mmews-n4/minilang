package de.marcusmews.minilang.ast

abstract class ASTVisitor {

    open fun onNullChild(parent: SourceElement, parentProperty: String) {}

    open fun onSourceElement(node: SourceElement, parent: SourceElement?, parentProperty: String?) {}

    open fun onStatement(node: Statement, parent: SourceElement?, parentProperty: String?) {}

    open fun onExpression(node: Expression, parent: SourceElement?, parentProperty: String?) {}

    open fun onProgram(node: Program, parent: SourceElement?, parentProperty: String?) {}

    open fun onVariableDeclaration(node: VariableDeclaration, parent: SourceElement?, parentProperty: String?) {}

    open fun onOutputStatement(node: OutputStatement, parent: SourceElement?, parentProperty: String?) {}

    open fun onPrintStatement(node: PrintStatement, parent: SourceElement?, parentProperty: String?) {}

    open fun onBinaryOperation(node: BinaryOperation, parent: SourceElement?, parentProperty: String?) {}

    open fun onParenthesizedExpression(node: ParenthesizedExpression, parent: SourceElement?, parentProperty: String?) {}

    open fun onIdentifierExpression(node: IdentifierExpression, parent: SourceElement?, parentProperty: String?) {}

    open fun onNumberLiteral(node: NumberLiteral, parent: SourceElement?, parentProperty: String?) {}

    open fun onSequenceExpression(node: SequenceExpression, parent: SourceElement?, parentProperty: String?) {}

    open fun onMapExpression(node: MapExpression, parent: SourceElement?, parentProperty: String?) {}

    open fun onReduceExpression(node: ReduceExpression, parent: SourceElement?, parentProperty: String?) {}

}