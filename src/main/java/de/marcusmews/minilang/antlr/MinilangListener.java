// Generated from c:/git/minilang/src/grammar/Minilang.g4 by ANTLR 4.13.1
package de.marcusmews.minilang.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MinilangParser}.
 */
public interface MinilangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MinilangParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MinilangParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinilangParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MinilangParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinilangParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(MinilangParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinilangParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(MinilangParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinilangParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(MinilangParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinilangParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(MinilangParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinilangParser#outputStmt}.
	 * @param ctx the parse tree
	 */
	void enterOutputStmt(MinilangParser.OutputStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinilangParser#outputStmt}.
	 * @param ctx the parse tree
	 */
	void exitOutputStmt(MinilangParser.OutputStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinilangParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void enterPrintStmt(MinilangParser.PrintStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinilangParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void exitPrintStmt(MinilangParser.PrintStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinilangParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MinilangParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinilangParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MinilangParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinilangParser#sequence}.
	 * @param ctx the parse tree
	 */
	void enterSequence(MinilangParser.SequenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinilangParser#sequence}.
	 * @param ctx the parse tree
	 */
	void exitSequence(MinilangParser.SequenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinilangParser#mapExpr}.
	 * @param ctx the parse tree
	 */
	void enterMapExpr(MinilangParser.MapExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinilangParser#mapExpr}.
	 * @param ctx the parse tree
	 */
	void exitMapExpr(MinilangParser.MapExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinilangParser#reduceExpr}.
	 * @param ctx the parse tree
	 */
	void enterReduceExpr(MinilangParser.ReduceExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinilangParser#reduceExpr}.
	 * @param ctx the parse tree
	 */
	void exitReduceExpr(MinilangParser.ReduceExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinilangParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(MinilangParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinilangParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(MinilangParser.IdentifierContext ctx);
}