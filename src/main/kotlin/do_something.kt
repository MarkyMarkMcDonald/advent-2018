import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun main(args: Array<String>) {
    val lines = input()

    val actions = lines.map(::parseAction).sortedBy { it.occurredAt }

    val sleepySleeps = parseGuardSleepIntervals(actions)

    val guards = sleepySleeps.map(GuardSleepInterval::guard).distinct()

    val snorer = guards.maxBy { guard -> findSleepiestMinute(guard, sleepySleeps).second }!!

    val snoreTime = findSleepiestMinute(snorer, sleepySleeps)

    println(snorer)
    println(snoreTime.first)
    println(snorer.toInt() * snoreTime.first)
}

typealias Minute = Int
typealias Count = Int

fun findSleepiestMinute(sleepyHead: String, sleepySleeps: List<GuardSleepInterval>): Pair<Minute, Count> {
    val minutes = minuteFrequencies(sleepySleeps, sleepyHead)

    val (minute, count) = minutes.maxBy { it.value }!!
    return Pair(minute, count)
}

private fun minuteFrequencies(
    sleepySleeps: List<GuardSleepInterval>,
    sleepyHead: String
): MutableMap<Minute, Count> {
    val minutes = mutableMapOf<Minute, Count>()
    minutes.putAll(((0..60).map { Pair(it, 0) }))

    val intervals = sleepySleeps.filter { it.guard == sleepyHead }

    intervals.forEach {
        (it.startedAt.minute..(it.stoppedAt.minute - 1)).forEach { minute ->
            minutes[minute] = minutes[minute]?.plus(1)!!
        }
    }
    return minutes
}

fun findSleepyHead(sleepySleeps: List<GuardSleepInterval>): String {
    val byGuard = sleepySleeps.groupBy(GuardSleepInterval::guard)
    val (sleepyHead, _) = byGuard.maxBy { (_, sleepySleeps) -> sleepySleeps.sumBy(GuardSleepInterval::minutesSlept) }!!
    return sleepyHead
}

fun parseGuardSleepIntervals(actions: List<Action>): List<GuardSleepInterval> {
    val intervals = mutableListOf<GuardSleepInterval>()

    val shiftStart = actions.first()
    val shiftActions = actions.subList(1, actions.size).takeWhile { it.guard == null }

    val interval = shiftActions.chunked(2) {
        val guard = shiftStart.guard!!
        val startedAt = it[0].occurredAt
        val stoppedAt = it[1].occurredAt
        GuardSleepInterval(guard, startedAt, stoppedAt)
    }

    intervals.addAll(interval)

    val remainingActions = actions.subList(1 + shiftActions.size, actions.size)
    if (remainingActions.isNotEmpty()) {
        intervals.addAll(parseGuardSleepIntervals(remainingActions))
    }

    return intervals
}

data class GuardSleepInterval(val guard: String, val startedAt: LocalDateTime, val stoppedAt: LocalDateTime) {
    fun minutesSlept() : Int {
        return ChronoUnit.MINUTES.between(startedAt, stoppedAt).toInt()
    }
}

enum class ActionType { BEGIN_SHIFT, FALLS_ASLEEP, WAKES_UP }

data class Action(val guard: String?, val occurredAt: LocalDateTime, val action: ActionType)

fun parseAction(line: String): Action {
    if (line.contains("begins shift")) {
        val match = Regex("\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)] Guard #(\\d+) begins shift").matchEntire(line)!!

        val year = match.groups[1]!!.value.toInt()
        val month = match.groups[2]!!.value.toInt()
        val day = match.groups[3]!!.value.toInt()
        val hour = match.groups[4]!!.value.toInt()
        val minute = match.groups[5]!!.value.toInt()

        val occurredAt = LocalDateTime.of(year, month, day, hour, minute)

        val guardNumber = match.groups[6]!!.value

        return Action(guardNumber, occurredAt, ActionType.BEGIN_SHIFT)
    } else {
        val match = Regex("\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)] (.*)").matchEntire(line)!!

        val year = match.groups[1]!!.value.toInt()
        val month = match.groups[2]!!.value.toInt()
        val day = match.groups[3]!!.value.toInt()
        val hour = match.groups[4]!!.value.toInt()
        val minute = match.groups[5]!!.value.toInt()

        val occurredAt = LocalDateTime.of(year, month, day, hour, minute)

        val actionType = match.groups[6]!!.value.toActionType()

        return Action(null, occurredAt, actionType)
    }
}

private fun String.toActionType(): ActionType {
    return ActionType.valueOf(this.toUpperCase().replace(" ", "_"))
}

