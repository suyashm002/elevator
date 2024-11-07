package com.suyash.elevator.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.suyash.elevator.model.Direction
import com.suyash.elevator.model.ElevatorState
import androidx.compose.runtime.*


class ElevatorController {
    private var _elevatorState = mutableStateOf(ElevatorState(0, Direction.IDLE))
    val elevatorState: State<ElevatorState> = _elevatorState

    private val requestQueue = mutableListOf<Int>()

    fun requestElevator(floor: Int) {
        requestQueue.add(floor)
        updateState()
    }

    fun selectFloor(floor: Int) {
        requestQueue.add(floor)
        updateState()
    }

    private fun updateState() {
        if (requestQueue.isNotEmpty()) {
            val nextFloor = requestQueue.first()
            if (nextFloor > _elevatorState.value.currentFloor) {
                _elevatorState.value = _elevatorState.value.copy(
                    currentFloor = nextFloor,
                    direction = Direction.UP
                )
            } else {
                _elevatorState.value = _elevatorState.value.copy(
                    currentFloor = nextFloor,
                    direction = Direction.DOWN
                )
            }
            requestQueue.removeFirst()
        } else {
            _elevatorState.value = _elevatorState.value.copy(
                direction = Direction.IDLE
            )
        }
    }
}
