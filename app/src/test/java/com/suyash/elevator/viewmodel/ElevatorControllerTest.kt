package com.suyash.elevator.viewmodel

import com.suyash.elevator.model.Direction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ElevatorControllerTest {
    private lateinit var elevatorController: ElevatorController

    @Before
    fun setUp() {
        elevatorController = ElevatorController()
    }

    @Test
    fun `initial state should be on ground floor and idle`() {
        assertEquals(0, elevatorController.elevatorState.value.currentFloor)
        assertEquals(Direction.IDLE, elevatorController.elevatorState.value.direction)
    }

    @Test
    fun `requestElevator should update the state to the requested floor`() = runBlocking {
        elevatorController.requestElevator(3)

        // Validate that the elevator moved to the requested floor
        assertEquals(3, elevatorController.elevatorState.value.currentFloor)
        assertEquals(Direction.UP, elevatorController.elevatorState.value.direction)
    }

    @Test
    fun `selectFloor should update the state to the selected floor`() = runBlocking {
        elevatorController.selectFloor(1)

        // Validate that the elevator moved to the selected floor
        assertEquals(1, elevatorController.elevatorState.value.currentFloor)
        assertEquals(Direction.UP, elevatorController.elevatorState.value.direction)
    }

    @Test
    fun `requestElevator with lower floor should move down`() = runBlocking {
        elevatorController.requestElevator(5)
        elevatorController.requestElevator(2)

        // Simulate the elevator moving down
        assertEquals(2, elevatorController.elevatorState.value.currentFloor)
        assertEquals(Direction.DOWN, elevatorController.elevatorState.value.direction)
    }

    @Test
    fun `state should be idle when no more requests are in the queue`() = runBlocking {
        elevatorController.selectFloor(1)
        elevatorController.selectFloor(0)

        // Final state should be idle
        assertEquals(Direction.IDLE, elevatorController.elevatorState.value.direction)
    }
}