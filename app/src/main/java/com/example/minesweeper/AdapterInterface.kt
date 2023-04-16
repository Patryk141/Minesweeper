package com.example.minesweeper

interface AdapterInterface {
    fun squareClick(square: Square)
    fun checkBoard(_squares: List<Square>)
    fun getFlag():Boolean
}