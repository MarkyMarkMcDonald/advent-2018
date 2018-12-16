val STEP_OVERHEAD = 60
val NUMBER_OF_WORKERS = 5

fun main(args: Array<String>) {
    val instructions: List<Instruction> = input().map(::parseInstruction)

    val startingPoints = ('A'..'Z').filter {
        instructions.map(Instruction::step).contains(it) &&
                !instructions.map(Instruction::nextStep).contains(it)
    }

    val startingInstructions = instructions
        .filter { startingPoints.contains(it.step) }
        .map { Instruction('*', it.step) }.distinct()
        .sortedBy { it.nextStep }

    val (_, time) = orderedInstructions(listOf(), mapOf(), instructions + startingInstructions, 0, WorkerPool(NUMBER_OF_WORKERS))

    println(time)
}

val COSTS: Map<Char, Int> = ('A'..'Z').mapIndexed { index, char -> Pair(char, index + 1 + STEP_OVERHEAD) }.toMap()

class WorkerPool(max: Int) {
    private var count = max

    fun availability() : Int {
        return count
    }

    fun take(number: Int){
        count -= number
        if (count < 0) throw IllegalArgumentException("you took too many")
    }

    fun giveBack(number: Int){
        count += number
    }
}

typealias SecondsRemaining = Int
typealias Seconds = Int
typealias InstructionsAndCost = Pair<List<Instruction>, Seconds>

tailrec fun orderedInstructions(
    visitedInstructions: List<Instruction>,
    inProduction: Map<SecondsRemaining, Instruction>,
    remainingInstructions: List<Instruction>,
    timeSoFar: Seconds,
    workerPool: WorkerPool
): InstructionsAndCost {
    return if (remainingInstructions.isEmpty()) {
        Pair(visitedInstructions + inProduction.map { it.value }, timeSoFar + (inProduction.keys.max() ?: 0))
    } else {
        val possibleNextInstructions = workToBePickedUp(remainingInstructions, visitedInstructions)
        val noWorkersAvailable = workerPool.availability() == 0
        val noWorkToBeDone = possibleNextInstructions.isEmpty()

        if (noWorkersAvailable || noWorkToBeDone) {
            // simulate time passing and move WIP to visited
            val minimumTimeLeft = inProduction.keys.min()!!
            val finishedActions: Map<SecondsRemaining, Instruction> = inProduction.filter { it.key == minimumTimeLeft }
            val newTimeSoFar = timeSoFar + minimumTimeLeft
            val newInProduction: Map<SecondsRemaining, Instruction> = inProduction.minus(finishedActions.map { it.key }).mapKeys { it.key - minimumTimeLeft }
            val newVisitedInstructions = visitedInstructions + finishedActions.values
            workerPool.giveBack(finishedActions.size)
            orderedInstructions(
                newVisitedInstructions, newInProduction, remainingInstructions, newTimeSoFar, workerPool
            )

        } else {
            // Move as much unblocked work as we have workers for from remaining to inProduction
            val availableWorkers = workerPool.availability()
            val accomplishableWork = possibleNextInstructions.take(availableWorkers)
            workerPool.take(accomplishableWork.size)

            val newInProduction: Map<SecondsRemaining, Instruction> = inProduction.plus(accomplishableWork.map { Pair(COSTS[it.nextStep]!!, it) })

            orderedInstructions(
                visitedInstructions,
                newInProduction,
                remainingInstructions.filter {
                    val targetNextSteps = accomplishableWork.map { a -> a.nextStep }
                    !targetNextSteps.contains(it.nextStep)
                },
                timeSoFar,
                workerPool
            )
        }
    }
}

private fun workToBePickedUp(
    remainingInstructions: List<Instruction>,
    visitedInstructions: List<Instruction>
): List<Instruction> {
    val specialFirstCases = remainingInstructions.filter { it.step == '*' }
    if (specialFirstCases.isNotEmpty()) {
        return specialFirstCases.sortedBy { it.nextStep }
    }

    return (remainingInstructions).filter { instruction ->
        val comesNext = isNext(visitedInstructions, instruction)
        val blocked = (remainingInstructions).any {
            val samePath = it.nextStep == instruction.nextStep
            val notNext = !isNext(visitedInstructions, it)
            samePath && notNext
        }
        comesNext && !blocked
    }.sortedBy { it.nextStep }
}

private fun isNext(
    visitedInstructions: List<Instruction>,
    instruction: Instruction
) = visitedInstructions.map(Instruction::nextStep).contains(instruction.step)

data class Instruction(val step: Char, val nextStep: Char)

fun parseInstruction(line: String): Instruction {
    val matches = Regex("Step (\\w) must be finished before step (\\w) can begin\\.").matchEntire(line)!!
    return Instruction(
        matches.groupValues[1].first(),
        matches.groupValues[2].first()
    )
}
