package de.marcusmews.minilang.validation

import de.marcusmews.minilang.ast.ProgramInfo
import de.marcusmews.minilang.ast.SourceElement

/**
 * Contains validations regarding the program structure that are not already checked by the ANTLR parser
 */
class StructureValidator(issueHandler: IssueHandler, programInfo: ProgramInfo): BaseValidator(issueHandler, programInfo) {

    override fun onNullChild(parent: SourceElement, parentProperty: String) {
        reportError(parent, "Property $parentProperty is mandatory")
    }

}