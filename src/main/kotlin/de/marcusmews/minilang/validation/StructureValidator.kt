package de.marcusmews.minilang.validation

import de.marcusmews.minilang.ast.ProgramInfo
import de.marcusmews.minilang.ast.SourceElement
import de.marcusmews.minilang.ast.SourceElementPart

/**
 * Contains validations regarding the program structure that are not already checked by the ANTLR parser
 */
class StructureValidator(issueHandler: IssueHandler, programInfo: ProgramInfo): BaseValidator(issueHandler, programInfo) {

    override fun onNullChild(parent: SourceElement, partType: SourceElementPart) {
        reportError(parent, "Property $partType is mandatory")
    }

}