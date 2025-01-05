package de.marcusmews.minilang.tests.utils

import de.marcusmews.minilang.ast.*
import kotlin.random.Random

/**
 * This utility generates arbitrary ASTs of valid Minilang programs.
 */
class ASTGenerator {

    fun generateASTs(seed: Long, count: Int): List<Program> {
        val random = Random(seed)
        return List(count) { generateRandomAST(random) }
    }

    private fun generateRandomAST(random: Random): Program {
        val statements = List(random.nextInt(1, 5)) { generateRandomStatement(random) }
        return Program(statements)
    }

    private fun generateRandomStatement(random: Random): Statement {
        return when (random.nextInt(3)) {
            0 -> generateRandomVariableDeclaration(random)
            1 -> generateRandomOutputStatement(random)
            2 -> generateRandomPrintStatement(random)
            else -> throw IllegalStateException("Unexpected random value")
        }
    }

    private fun generateRandomVariableDeclaration(random: Random): VariableDeclaration {
        val identifier = "var${random.nextInt(100)}"
        val expression = generateRandomExpression(random)
        return VariableDeclaration(identifier, expression)
    }

    private fun generateRandomOutputStatement(random: Random): OutputStatement {
        val expression = generateRandomExpression(random)
        return OutputStatement(expression)
    }

    private fun generateRandomPrintStatement(random: Random): PrintStatement {
        val string = "RandomString${random.nextInt(100)}"
        return PrintStatement(string)
    }

    private fun generateRandomExpression(random: Random): Expression {
        return when (random.nextInt(7)) {
            0 -> NumberLiteral(generateNumber(random, -100, 100))
            1 -> IdentifierExpression("id${random.nextInt(100)}")
            2 -> BinaryOperation(
                generateRandomExpression(random),
                generateRandomOperator(random),
                generateRandomExpression(random)
            )
            3 -> ParenthesizedExpression(generateRandomExpression(random))
            4 -> SequenceExpression(
                NumberLiteral(random.nextInt(0, 10).toDouble()),
                NumberLiteral(random.nextInt(11, 20).toDouble())
            )
            5 -> MapExpression(
                SequenceExpression(
                    NumberLiteral(random.nextInt(0, 10).toDouble()),
                    NumberLiteral(random.nextInt(11, 20).toDouble())
                ),
                "param${random.nextInt(10)}",
                generateRandomExpression(random)
            )
            6 -> ReduceExpression(
                SequenceExpression(
                    NumberLiteral(random.nextInt(0, 10).toDouble()),
                    NumberLiteral(random.nextInt(11, 20).toDouble())
                ),
                NumberLiteral(generateNumber(random, 0, 10)),
                "acc${random.nextInt(10)}",
                "val${random.nextInt(10)}",
                generateRandomExpression(random)
            )
            else -> throw IllegalStateException("Unexpected random value")
        }
    }

    private fun generateNumber(random: Random, origin: Int, bound: Int): Double {
        return Math.round(random.nextDouble(origin * 100.0, bound * 100.0)) / 100.0
    }

    private fun generateRandomOperator(random: Random): Operator {
        return when (random.nextInt(5)) {
            0 -> Operator.PLUS
            1 -> Operator.MINUS
            2 -> Operator.MULTIPLY
            3 -> Operator.DIVIDE
            4 -> Operator.POWER
            else -> throw IllegalStateException("Unexpected random value")
        }
    }
}
