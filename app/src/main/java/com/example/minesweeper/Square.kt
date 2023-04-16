package com.example.minesweeper


class Square(value: Int) {
    private var value: Int
    private var flag: Boolean = false
    private var bomb: Boolean = false
    private var click: Boolean = false

    fun setValues(value: Int) {
        this.value = value
    }

    fun getValues(): Int {
        return value
    }

    fun setFlag() {
        flag = !flag
    }

    fun getFlag(): Boolean {
        return flag
    }

    fun setBomb(bomb : Boolean) {
        this.bomb = bomb
    }

    fun getBomb(): Boolean {
        return bomb
    }

    fun setClick(click : Boolean) {
        this.click = click
    }

    fun getClick(): Boolean {
        return click
    }

    init {
        this.value = value
    }
}

