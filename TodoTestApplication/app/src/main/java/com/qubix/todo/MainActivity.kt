package com.qubix.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.qubix.todo.ui.theme.TodoTestApplicationTheme

val tasksList: MutableList<TaskHolder> = mutableListOf()

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTestApplicationTheme {
                Scaffold(topBar = { TopBar() }) {
                    Card(shape = RoundedCornerShape(8.dp), elevation = 16.dp, modifier = Modifier.padding(16.dp)) {
                        AddTask()
                        TasksList(tasks = tasksList)
                    }
                }
            }
        }
    }
}

@Composable
fun TasksList(tasks: List<TaskHolder>) {
    LazyColumn {
        items(tasks) { task ->
            Task(task)
        }
    }
}

@Composable
fun AddTask() {
    val taskMutable = remember { mutableStateOf("") }
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)) {
        val (addText, addTextField, plusButton) = createRefs()
        Text(text = "Add",
            fontSize = 12.sp,
            modifier = Modifier.constrainAs(addText) {
                top.linkTo(parent.top, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }
        )
        TextField(
            value = taskMutable.value,
            onValueChange = { taskMutable.value = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Transparent
            ),
            modifier = Modifier.constrainAs(addTextField) {
                top.linkTo(addText.bottom, margin = 0.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(plusButton.start, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        IconButton(onClick = { tasksList.add(TaskHolder(taskMutable.value, false)) }, Modifier.constrainAs(plusButton) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }) {
            Icon(Icons.Filled.Add, contentDescription = "Add new task")
        }
        
    }
}

@Composable
fun Task(task : TaskHolder) {
    val taskDescription = remember { mutableStateOf(task.taskText) }
    val taskIsChecked = remember { mutableStateOf(task.isChecked) }
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (isTaskDone, taskText, deleteButton) = createRefs()
        Checkbox(
            checked = taskIsChecked.value,
            onCheckedChange = { taskIsChecked.value = it },
            Modifier.constrainAs(isTaskDone) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start, margin = 8.dp)
        })
        BasicTextField(
            value = taskDescription.value,
            onValueChange = { taskDescription.value = it },
            modifier = Modifier.constrainAs(taskText) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(isTaskDone.end, margin = 16.dp)
                end.linkTo(deleteButton.start, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
        )

        IconButton(onClick = { /*TODO*/ }, Modifier.constrainAs(deleteButton) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete this task")
        }
    }
}

@Composable
fun TopBar() {
    Column {
        TopAppBar(title = { Text(text = "Todo List") })
    }
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    TodoTestApplicationTheme {
        Scaffold(topBar = { TopBar() }) {
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 16.dp,
                modifier = Modifier.padding(16.dp)
            ) {
                val tasks =
                    listOf(TaskHolder("TEST TASK1", true), TaskHolder("TEST TASK2", false))
                Column {
                    TasksList(tasks = tasks)
                    AddTask()
                }
            }
        }
    }
}