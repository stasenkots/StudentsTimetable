package com.stasenkots.logic.entity.student

object Students {
    internal var map = mutableMapOf<String, Student>()
    fun get() = map
    fun put(students: List<Student>) {
        map = students.associateBy { it.id }.toMutableMap()
    }
}