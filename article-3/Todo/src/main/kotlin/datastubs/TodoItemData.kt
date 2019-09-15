package datastubs

import api.TodoItem
import api.TodoItemPayload
import api.TodoList
import java.time.LocalDate
import java.time.LocalDateTime

class TodoItemData {

    companion object Data {
        private val todoLists: List<TodoList> = TodoListData.todoLists

        val todoItems = mutableListOf<TodoItem>(
            TodoItem("/todo-items/1", 1, 1, UserData.users[0].self,
            1, todoLists[0].self, "Go to the gym",
            "Do upper body workout", LocalDate.now(), LocalDate.now(), false),

            TodoItem("/todo-items/2", 2, 1,  UserData.users[0].self,
                1, todoLists[0].self, "Grocery shopping",
            "Get carrots", LocalDate.now(), LocalDate.now(), false),

            TodoItem("/todo-items/3", 3, 1, UserData.users[0].self,
                2, todoLists[1].self, "Create illustrations",
                "Figure out the illustration and do it", LocalDate.now(), LocalDate.now(), false)
        )
    }


    fun find(id : Long): TodoItem? {
        return todoItems.find { it.id == id }
    }

    fun findAll(): List<TodoItem> {
        return todoItems
    }

    fun create(todoItemPayload: TodoItemPayload): TodoItem {
        val id = todoItems.last().id + 1
        val item =  TodoItem("/todo-items/$id", id, 1,  UserData.users[0].self,
            todoItemPayload.parentId!!, TodoListData.todoLists[todoItemPayload.parentId - 1].self,
            todoItemPayload.title!!, todoItemPayload.description!!,
            LocalDate.now(), todoItemPayload.deadline!!, false)

        todoItems.add(item)

        return item
    }

    fun remove(id: Long): Boolean {
        return todoItems.removeIf { it.id == id }
    }

    fun update(id: Long, update: TodoItemPayload): TodoItem? {
        todoItems.find { it.id == id }?.let {
            it.title = update.title ?: it.title
            it.description = update.description ?: it.description
            it.deadline = update.deadline ?: it.deadline
            it.completed = update.completed ?: it.completed
            it.parentId = update.parentId?.apply {
                it.parentLink = TodoListData.todoLists[it.parentId - 1].self
            } ?: it.parentId
        }

        return todoItems.find { it.id == id }
    }
}