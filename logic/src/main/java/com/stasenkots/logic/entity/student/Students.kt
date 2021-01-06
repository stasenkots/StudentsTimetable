package com.stasenkots.logic.entity.student

object Students {
    internal val map = mutableMapOf<String, Student>()
    fun get() = map
    fun put(students:List<Student>){
        for (student in students) {
            map[student.id]=student
        }
    }
}