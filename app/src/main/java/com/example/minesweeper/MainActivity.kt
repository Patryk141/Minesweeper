package com.example.minesweeper

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), AdapterInterface {
    private val squares: MutableList<Square> = ArrayList()
    private var flag: Boolean = false
    private var adapter = CustomAdapter(generateBoard(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.buttonFlag).setBackgroundColor(Color.GREEN)
        findViewById<TextView>(R.id.textView2).text = "Minesweeper"
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this,  9)
        recyclerView.adapter = adapter
    }

    private fun generateBoard(): List<Square> {
        squares.clear()
        for (i in 0..80) {
            squares.add(Square(0))
            squares[i].setClick(false)
        }

        var counter = 0
        while (counter < 10) {
            val rand = (0..80).random()
            if (!squares[rand].getBomb()) {
                squares[rand].setBomb(true)
                val col = rand%9
                val row = rand/9
                println("$col  $row")
                if (col > 0) {
                    if (row > 0) squares[col - 1 + (row - 1)* 9].setValues(squares[col - 1 + (row - 1)* 9].getValues() + 1)
                    squares[col - 1 + row* 9].setValues(squares[col - 1 + row * 9].getValues() + 1)
                    if (row < 8) squares[col - 1 + (row + 1)* 9].setValues(squares[col - 1 + (row + 1)* 9].getValues() + 1)
                }
                if (col < 8) {
                    if (row > 0) squares[col + 1 + (row - 1)* 9].setValues(squares[col + 1 + (row - 1)* 9].getValues() + 1)
                    squares[col + 1 + row* 9].setValues(squares[col + 1 + row * 9].getValues() + 1)
                    if (row < 8) squares[col + 1 + (row + 1)* 9].setValues(squares[col + 1 + (row + 1)* 9].getValues() + 1)
                }
                if (row > 0) squares[col + (row - 1) * 9].setValues(squares[col + (row - 1) * 9].getValues() + 1)
                if (row < 8) squares[col + (row + 1) * 9].setValues(squares[col + (row + 1) * 9].getValues() + 1)

                counter++
            }
        }
        return squares
    }

    fun buttonFlag(view: View) {
        flag = !flag
        if (flag) findViewById<Button>(R.id.buttonFlag).setBackgroundColor(Color.RED)
        else findViewById<Button>(R.id.buttonFlag).setBackgroundColor(Color.GREEN)
    }

    fun buttonReset(view: View) {
        adapter.reset(generateBoard())
        findViewById<TextView>(R.id.textView2).text = "Minesweeper"
    }

    override fun getFlag(): Boolean {
        return flag
    }

    override fun squareClick(square: Square) {
        if (flag) {
            square.setFlag()
        }
    }

    override fun checkBoard(_squares: List<Square>) {
        var counter = 0
        var counterFlag =0
        for (i in 0..80) {
            if (_squares[i].getClick() && _squares[i].getBomb()) {
                findViewById<TextView>(R.id.textView2).text = "Game Over"
                break
            }
            if (_squares[i].getClick() && !_squares[i].getBomb()) {
                counter++
            }
            if (_squares[i].getFlag()) {
                if (_squares[i].getBomb()) {
                    counterFlag++
                } else {
                    counterFlag--
                }
            }
        }
        if (counter == 71 || counterFlag == 10) {
            findViewById<TextView>(R.id.textView2).text = "Win"
        }
    }
}