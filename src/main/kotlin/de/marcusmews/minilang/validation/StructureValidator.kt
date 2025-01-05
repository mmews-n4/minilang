package de.marcusmews.minilang.validation

import de.marcusmews.minilang.ast.ProgramInfo
import de.marcusmews.minilang.ast.SourceElement

class StructureValidator(issueHandler: IssueHandler, programInfo: ProgramInfo): BaseValidator(issueHandler, programInfo) {

    override fun onNullChild(parent: SourceElement, parentProperty: String) {
        reportError(parent, "Property $parentProperty is mandatory")
    }

}