package com.example.week1.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week1.model.Task

@Composable
fun DetailDialog(
    task: Task,
    isNew: Boolean,
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit,
    onDelete: (Int) -> Unit
) {
    var title by remember(task.id) { mutableStateOf(task.title) }
    var description by remember(task.id) { mutableStateOf(task.description) }
    var dueDate by remember(task.id) { mutableStateOf(task.dueDate) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isNew) "Add task" else "Edit task") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Due date (dd-MM-yyyy)") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(task.copy(title = title, description = description, dueDate = dueDate))
            }) { Text("Save") }
        },
        dismissButton = {
            Column {
                Button(onClick = onDismiss) { Text("Cancel") }
                if (!isNew) {
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { onDelete(task.id) }) { Text("Delete") }
                }
            }
        }
    )
}
