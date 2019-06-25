package pl.robert.kotlinweb.task.domain

import lombok.AccessLevel
import lombok.experimental.FieldDefaults

import java.util.Optional
import java.util.stream.Collectors
import java.util.concurrent.ConcurrentHashMap

import kotlin.collections.HashSet

@FieldDefaults(level = AccessLevel.PRIVATE)
class InMemoryTaskRepository(var map: ConcurrentHashMap<String, Task>) : TaskRepository {

    override fun save(task: Task): Task {
        map[task.id] = task
        return task
    }

    override fun findAll(): List<Task> = HashSet(map.values)
            .stream()
            .collect(Collectors.toList())

    override fun findById(id: String): Optional<Task> {
        return map.entries
                .stream()
                .filter { map -> map.key == id }
                .map { it.value }
                .findFirst()
    }

    override fun deleteById(id: String) {
        map.remove(id)
    }

    override fun deleteAll() {
        map.clear()
    }
}
