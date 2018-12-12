fun main(args: Array<String>) {
    val instructions: List<Instruction> = input().map(::parseInstruction)

    val startingPoints = ('A'..'Z').filter {
        instructions.map(Instruction::step).contains(it) &&
                !instructions.map(Instruction::nextStep).contains(it)
    }

    val startingInstructions = instructions
        .filter { startingPoints.contains(it.step) }
        .map { Instruction('*', it.step) }
        .sortedBy { it.nextStep }

    val orderedInstructions = orderedInstructions(startingInstructions, instructions)

    val path = walkThisWay(orderedInstructions)

    println(path)
}

tailrec fun orderedInstructions(
    visitedInstructions: List<Instruction>,
    remainingInstructions: List<Instruction>
): List<Instruction> {
    return if (remainingInstructions.isEmpty()) {
        visitedInstructions
    } else {
        val possibleNextInstructions = remainingInstructions.filter { instruction ->
            val comesNext = isNext(visitedInstructions, instruction)
            val blocked = (remainingInstructions).any {
                val samePath = it.nextStep == instruction.nextStep
                val notNext = !isNext(visitedInstructions, it)
                samePath && notNext
            }
            comesNext && !blocked
        }
        val nextInstruction: Instruction = possibleNextInstructions.minBy { it.nextStep }!!
        orderedInstructions(visitedInstructions + nextInstruction, remainingInstructions - nextInstruction)
    }
}

private fun isNext(
    visitedInstructions: List<Instruction>,
    instruction: Instruction
) = visitedInstructions.map(Instruction::nextStep).contains(instruction.step)

private fun walkThisWay(orderedInstructions: List<Instruction>): String {
    var path = ""
    orderedInstructions.map(Instruction::nextStep).forEach {
        if (!path.contains(it)) {
            path += it
        }
    }
    return path
}

data class Instruction(val step: Char, val nextStep: Char)

fun parseInstruction(line: String): Instruction {
    val matches = Regex("Step (\\w) must be finished before step (\\w) can begin\\.").matchEntire(line)!!
    return Instruction(
        matches.groupValues[1].first(),
        matches.groupValues[2].first()
    )
}
