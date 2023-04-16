package com.example.minesweeper

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private var squares: List<Square>, private val listener: AdapterInterface) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private val viewHolder: MutableList<ViewHolder> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView : TextView = view.findViewById<TextView>(R.id.item_square_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_square, parent, false)
        viewHolder.add(ViewHolder(view))
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return squares.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.setBackgroundColor(Color.GRAY)
//        if (squares[position].getBomb()) {
//            holder.textView.setBackgroundColor(Color.GREEN)
//        }
        holder.textView.setOnClickListener {
            listener.squareClick(squares[position])
            if (!squares[position].getClick()) {
                if (squares[position].getFlag() && listener.getFlag()) {
                    holder.textView.text = "🚩"
                } else if (!squares[position].getFlag() && listener.getFlag()) {
                    holder.textView.text = ""
                } else if (!squares[position].getFlag()) {
                    detection(position)
                }
                listener.checkBoard(squares)
            }
        }
    }

    fun reset(squares: List<Square>) {
        this.squares = squares
        for (i in 0..80) {
            viewHolder[i].textView.setBackgroundColor(Color.GRAY)
            viewHolder[i].textView.text = ""
        }
    }

    private fun showNumber(square: Square, holder: ViewHolder) {
        var number = square.getValues()
        holder.textView.text = "$number"
        if (number == 1) holder.textView.setTextColor(Color.BLUE)
        if (number == 2) holder.textView.setTextColor(Color.GREEN)
        if (number == 3) holder.textView.setTextColor(Color.RED)
        if (number == 4) holder.textView.setTextColor(Color.rgb(148,0,211)) //purple
        if (number == 5) holder.textView.setTextColor(Color.rgb(128,0,0)) //maroon
        if (number == 6) holder.textView.setTextColor(Color.rgb(64,224,208)) //turquoise
        if (number == 7) holder.textView.setTextColor(Color.BLACK)
        if (number == 8) holder.textView.setTextColor(Color.DKGRAY)

    }

    private fun detection(position: Int) {
        if (!squares[position].getClick() && !squares[position].getBomb()) {
            squares[position].setClick(true)
            viewHolder[position].textView.setBackgroundColor(Color.LTGRAY)
            if (squares[position].getValues() != 0) {
                showNumber(squares[position], viewHolder[position])
            } else {
                val col = position % 9
                val row = position / 9

                if (col > 0) {
                    if (row > 0) detection(col - 1 + (row - 1) * 9)
                    detection(col - 1 + row * 9)
                    if (row < 8) detection(col - 1 + (row + 1) * 9)
                }
                if (col < 8) {
                    if (row > 0) detection(col + 1 + (row - 1) * 9)
                    detection(col + 1 + row * 9)
                    if (row < 8) detection(col + 1 + (row + 1) * 9)
                }
                if (row > 0) detection(col + (row - 1) * 9)
                if (row < 8) detection(col + (row + 1) * 9)
            }
        } else if (squares[position].getBomb()) {
            viewHolder[position].textView.setBackgroundColor(Color.RED)
            for (i in 0..80) {
                if (squares[i].getBomb()) {
                    viewHolder[i].textView.text = "\uD83D\uDCA3"
                }
                squares[i].setClick(true)
            }
        }
    }
}