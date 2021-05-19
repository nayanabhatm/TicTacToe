package com.nbhatm.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var PLAYER = true
    var TURN_COUNT  = 0
    var boardStatus = Array(3){IntArray(3)}
    lateinit var board: Array<Array<Button>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = arrayOf(
                arrayOf(btn1,btn2,btn3),
                arrayOf(btn4,btn5,btn6),
                arrayOf(btn7,btn8,btn9),
        )

        initializeBoardStatus()

        for(i in board){
            for(button in i){
                button.setOnClickListener(this)
            }
        }

        btnReset.setOnClickListener{
            TURN_COUNT = 0
            PLAYER = true
            initializeBoardStatus()
        }
    }

    private fun initializeBoardStatus() {
       for(i in 0..2){
           for(j in 0..2){
               boardStatus[i][j]=-1
           }
       }

        for(i in 0..2){
            for(j in 0..2){
                board[i][j].isEnabled = true
                board[i][j].text = ""
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn1 ->{
                updateValue(row=0,col=0,player = PLAYER)
            }
            R.id.btn2 ->{
                updateValue(row=0,col=1,player = PLAYER)
            }
            R.id.btn3 ->{
                updateValue(row=0,col=2,player = PLAYER)
            }
            R.id.btn4 ->{
                updateValue(row=1,col=0,player = PLAYER)
            }
            R.id.btn5 ->{
                updateValue(row=1,col=1,player = PLAYER)
            }
            R.id.btn6 ->{
                updateValue(row=1,col=2,player = PLAYER)
            }
            R.id.btn7 ->{
                updateValue(row=2,col=0,player = PLAYER)
            }
            R.id.btn8 ->{
                updateValue(row=2,col=1,player = PLAYER)
            }
            R.id.btn9 ->{
                updateValue(row=2,col=2,player = PLAYER)
            }

        }

        PLAYER = !PLAYER
        TURN_COUNT++

        if(PLAYER){
            updateDisplay("Player X Turn")
        }
        else{
            updateDisplay("Player O Turn")
        }

        if(TURN_COUNT==9){
            updateDisplay("GAME DRAW")
            AlertDialog.Builder(this).setTitle("GAME DRAW").setPositiveButton("RESET"){_,_ ->
                TURN_COUNT = 0
                PLAYER = true
                initializeBoardStatus()
            }.show()
        }

        checkWinner()
    }

    private fun updateDisplay(display: String) {
        textView.text=display
        if(display.contains("Winner")){
            AlertDialog.Builder(this).setTitle(display).setPositiveButton("RESET"){_,_ ->
                TURN_COUNT = 0
                PLAYER = true
                initializeBoardStatus()
            }.show()
            disableButton()
        }
    }

    private fun disableButton(){
        for(i in board){
            for(button in i){
                button.isEnabled =false
            }
        }
    }

    private fun checkWinner(){
        // Horizontal checks
        for(i in 0..2){
            if(boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][1] == boardStatus[i][2]){
                if(boardStatus[i][0]==1){
                    updateDisplay("Player X is the Winner")
                    break
                }
                else if(boardStatus[i][0]==0)
                {
                    updateDisplay("Player O is the Winner")
                    break
                }
            }
        }

        // Vertical Checks
        for(i in 0..2){
            if(boardStatus[0][i] == boardStatus[1][i] && boardStatus[1][i] == boardStatus[2][i]){
                if(boardStatus[0][i]==1){
                    updateDisplay("Player X is the Winner")
                    break
                }
                else if(boardStatus[0][i]==0)
                {
                    updateDisplay("Player O is the Winner")
                    break
                }
            }
        }

        // Diagonal Checks
        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][2]){
            if(boardStatus[0][0]==1){
                updateDisplay("Player X is the Winner")
            }
            else if(boardStatus[0][0]==0)
            {
                updateDisplay("Player O is the Winner")
            }
        }

        if(boardStatus[0][2] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][0]){
            if(boardStatus[0][2]==1){
                updateDisplay("Player X is the Winner")
            }
            else if(boardStatus[0][2]==0)
            {
                updateDisplay("Player O is the Winner")
            }
        }


    }

    private fun updateValue(row: Int, col: Int, player: Boolean) {
        var playerVal:String = if(player) "X" else "O"
        var value:Int = if(player) 1 else 0

        board[row][col].apply {
            text = playerVal
            isEnabled = false
        }

        boardStatus[row][col] = value
    }

}