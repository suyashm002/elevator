package com.suyash.elevator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import com.suyash.elevator.viewmodel.ElevatorController


@Composable
fun ElevatorControlApp() {
    val elevatorController = remember { ElevatorController() }
    val elevatorState = elevatorController.elevatorState.value
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Elevator Control") }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Current Floor: ${elevatorState.currentFloor}",
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = "Direction: ${elevatorState.direction}",
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            isLoading = true
                            elevatorController.requestElevator(elevatorState.currentFloor - 1)
                            coroutineScope.launch {
                                delay(2000)
                                isLoading = false
                            }
                        }
                    ) {
                        Text("Down")
                    }
                    Button(
                        onClick = {
                            isLoading = true
                            elevatorController.requestElevator(elevatorState.currentFloor + 1)
                            coroutineScope.launch {
                                delay(2000)
                                isLoading = false
                            }
                        }
                    ) {
                        Text("Up")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            isLoading = true
                            elevatorController.selectFloor(0)
                            coroutineScope.launch {
                                delay(2000)
                                isLoading = false
                            }
                        }
                    ) {
                        Text("Ground Floor")
                    }
                    Button(
                        onClick = {
                            isLoading = true
                            elevatorController.selectFloor(1)
                            coroutineScope.launch {
                                delay(2000)
                                isLoading = false
                            }
                        }
                    ) {
                        Text("1st Floor")
                    }
                }
            }

            // Centered Progress Bar Overlay
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background.copy(alpha = 0.5f)), // Optional dim background
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}