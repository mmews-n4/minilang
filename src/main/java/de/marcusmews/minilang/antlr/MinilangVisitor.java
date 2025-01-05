// Generated from c:/git/minilang/src/grammar/Minilang.g4 by ANTLR 4.13.1
package de.marcusmews.minilang.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MinilangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MinilangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MinilangParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MinilangParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinilangParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(MinilangParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinilangParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(MinilangParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinilangParser#outputStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutputStmt(MinilangParser.OutputStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinilangParser#printStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStmt(MinilangParser.PrintStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinilangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(MinilangParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinilangParser#sequence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSequence(MinilangParser.SequenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinilangParser#mapExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapExpr(MinilangParser.MapExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MinilangParser#reduceExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReduceExpr(MinilangParser.ReduceExprContext ctx);
}