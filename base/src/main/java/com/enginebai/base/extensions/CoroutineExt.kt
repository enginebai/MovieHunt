package com.enginebai.base.extensions

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import java.util.concurrent.atomic.AtomicReference

/**
 * Retry running block with exponential backoff mechanism.
 * @param times how many times to retry.
 * @param initialDelayMillis The initial delay time in millis second.
 * @param delayFactor the factor to multiple [initialDelayMillis] to be next retry delay
 */
suspend fun <T> retry(
    times: Int = 5,
    initialDelayMillis: Long = 1000,
    delayFactor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelayMillis
    repeat(times) {
        try {
            return block()
        } catch (e: Exception) {
            Timber.w(e)
        }
        delay(currentDelay)
        currentDelay = (currentDelay * delayFactor).toLong()
    }
    return block() // last attempt
}

/**
 * A helper class that controls the task execution as new task requests to run:
 *  1. Cancel previous task and run the new task.
 *  2. Execute all task sequentially.
 *  3. Continue previous task and don't run the new task.
 */
// Source: https://gist.github.com/objcode/7ab4e7b1df8acd88696cb0ccecad16f7
class CoroutineRunningController<T> {

    // A lock that may only be taken by one coroutine at a time.
    private val mutex by lazy { Mutex() }

    // The currently running task, this uses atomic reference for thread safety.
    private val activeTask by lazy { AtomicReference<Deferred<T>?>(null) }

    /**
     * Cancel all previous tasks before calling block, then run block.
     */
    suspend fun cancelPreviousThenRun(block: suspend () -> T): T {
        // Cancel previous task if there is.
        activeTask.get()?.cancelAndJoin()

        return coroutineScope {
            // Create a new coroutine for new task and don't start it until it's decided
            //  that the new task should execute.
            val newTask = async(start = CoroutineStart.LAZY) { block() }

            // Reset the currently running task to null as new task completes.
            newTask.invokeOnCompletion {
                activeTask.compareAndSet(newTask, null)
            }

            val result: T
            // Loop until all previous tasks are canceled and we can run new task.
            while (true) {
                // Some other tasks started before the new task got set to running.
                // If there is still other tasks running, just cancel.
                if (!activeTask.compareAndSet(null, newTask)) {
                    activeTask.get()?.cancelAndJoin()
                    yield()
                } else {
                    result = newTask.await()
                    break
                }
            }
            result
        }
    }

    /**
     * Ensure to execute the tasks one by one, it will always ensure that all previously tasks
     * completes prior to start the current block task. Any future calls to this method while the
     * current block task is running will not execute until the current block task completes.
     */
    suspend fun queueTask(block: suspend () -> T): T {
        mutex.withLock {
            return block()
        }
    }

    /**
     * Don't run the new task while a previous task is running, instead wait for the previous task
     *  and return its result.
     */
    suspend fun joinPreviousOrRun(block: suspend () -> T): T {
        // If a previous task is running, then wait and return its result.
        activeTask.get()?.let { return it.await() }

        return coroutineScope {
            val newTask = async(start = CoroutineStart.LAZY) { block() }
            newTask.invokeOnCompletion {
                activeTask.compareAndSet(newTask, null)
            }

            val result: T
            while (true) {
                // Loop to check if there is running tasks, then join.
                if (!activeTask.compareAndSet(null, newTask)) {
                    val currentTask = activeTask.get()
                    if (currentTask != null) {
                        newTask.cancel()
                        result = currentTask.await()
                        break
                    } else {
                        yield()
                    }
                } else {
                    result = newTask.await()
                    break
                }
            }
            result
        }
    }
}