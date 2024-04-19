package com.example.pd1

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
//šeit notiek šausmu stāsts, šo visu varēja daudzas daudzas reizes labāk
class GameState (val firstPlayerName: String?, val secondPlayerName: String?,
                 var pvpMode: Int, var playerTitle: TextView, val restartButton: Button,
                 val buttons: MutableList<Button>) {
//nosaka kurš ir esošais spēlētājs un vai spēle beidzās
    private var currentPlayer: Boolean = true
    private var gameEnded: Boolean = false
    //šeit saglabā visus spēles stāvokļus. Pēc loģikas tālāk jāskatās metode -> editBoard
    private var gameBoard: MutableList<Int> = mutableListOf(-1,-1,-1,-1,-1,-1,-1,-1,-1)

    //vienkārši meklē vai ir 3 vienādi simboli rindās, kollonās un diagonālēs
    private fun checkWinner(): Int {
        if (gameBoard[0] != -1) {
            if (gameBoard[0]==gameBoard[1] && gameBoard[0]==gameBoard[2]) return gameBoard[0]
            if (gameBoard[0]==gameBoard[3] && gameBoard[0]==gameBoard[6]) return gameBoard[0]
            if (gameBoard[0]==gameBoard[4] && gameBoard[0]==gameBoard[8]) return gameBoard[0]
        }

        if (gameBoard[4] != -1) {
            if (gameBoard[4]==gameBoard[3] && gameBoard[4]==gameBoard[5]) return gameBoard[4]
            if (gameBoard[4]==gameBoard[1] && gameBoard[4]==gameBoard[7]) return gameBoard[4]
            if (gameBoard[4]==gameBoard[6] && gameBoard[4]==gameBoard[2]) return gameBoard[4]
        }

        if (gameBoard[8] != -1) {
            if (gameBoard[8]==gameBoard[5] && gameBoard[8]==gameBoard[2]) return gameBoard[8]
            if (gameBoard[8]==gameBoard[7] && gameBoard[8]==gameBoard[6]) return gameBoard[8]
        }
        return -1

    }

    fun computerMove() {
        //šeit nosakās kura rinda vai kolonna vai diagonāle ir potenciāli laba
        //options listā saglabāsies rinda vai kol. vai diag. kurā nebūs neviena
        //pretinieka simbola
        //pretinieka simbolu nosaka pēc pvp, ja tas ir 0, nozīmē, ka pretinieks ir 1
        val options: MutableList<Int> = mutableListOf(-1,-1,-1)
        var decision = -1
        var against = 0
        if (pvpMode==0) {
            against = 1
        }
        //viss šis tizlais ifu bloks meklē adbilstošu rind, kol., diag,.
        if (gameBoard[0]!=against && gameBoard[1]!=against && gameBoard[2]!=against) {
            options[0]=0
            options[1]=1
            options[2]=2
        }
        else if (gameBoard[0]!=against && gameBoard[3]!=against && gameBoard[6]!=against) {
            options[0]=0
            options[1]=3
            options[2]=6
        }
        else if (gameBoard[0]!=against && gameBoard[4]!=against && gameBoard[8]!=against) {
            options[0]=0
            options[1]=4
            options[2]=8
        }
        else if (gameBoard[4]!=against && gameBoard[3]!=against && gameBoard[5]!=against) {
            options[0]=4
            options[1]=3
            options[2]=5
        }
        else if (gameBoard[4]!=against && gameBoard[1]!=against && gameBoard[7]!=against) {
            options[0]=4
            options[1]=1
            options[2]=7
        }
        else if (gameBoard[4]!=against && gameBoard[6]!=against && gameBoard[2]!=against) {
            options[0]=4
            options[1]=6
            options[2]=2
        }
        else if (gameBoard[8]!=against && gameBoard[5]!=against && gameBoard[2]!=against) {
            options[0]=8
            options[1]=5
            options[2]=2
        }
        else if (gameBoard[8]!=against && gameBoard[7]!=against && gameBoard[6]!=against) {
            options[0]=8
            options[1]=7
            options[2]=6
        }
        //ja ir rinda, kol, diag, tad options viennozīmīgi nebūs -1, atrod specifiski tukšu lauku izvēlētā
        //rind., kol., diag
        if (options[0]!=-1) {
            for (i in 0..2) {
                if (gameBoard[options[i]]==-1) {
                    decision = options[i]
                    break
                }
            }
        }
        //ja neatrod, tad vienkārši paņem pirmo derīgo pozīciju
        else {
            decision = gameBoard.indexOf(-1)
        }
        //beigās izdara gājienu attiecīgajā vietā, kas var būt vai nu random, vai izskaitļota
        if (currentPlayer) {
            buttons[decision].text = "O"
            currentPlayer = false
            playerTitle.text = secondPlayerName
            gameBoard[decision] = 0
        }
        else {
            buttons[decision].text = "X"
            currentPlayer = true
            playerTitle.text = firstPlayerName
            gameBoard[decision] = 1
        }

    }
//nu lūk, šeit izpilda gājienu cilvēks
    //ieliek vērtību spēles stāvokļos un liek pogai buru virsū, attiecīgi to, kurš spēlētājs uzspieda
    fun editBoard(activeButton: Button, index: Int) {
        if (gameEnded) return
        if (currentPlayer) {
            activeButton.text = "O"
            currentPlayer = false
            playerTitle.text = secondPlayerName
            gameBoard[index] = 0
        }
        else {
            activeButton.text = "X"
            currentPlayer = true
            playerTitle.text = firstPlayerName
            gameBoard[index] = 1
        }
    //disablo pogu
        activeButton.isEnabled = false
        activeButton.isFocusable = false
        activeButton.isClickable = false
    //pārbauda vai nav uzvarētāja
        var winner = checkWinner()
        var wPlayer = ""
        if (winner!=-1) {
            gameEnded = true
            restartButton.visibility = View.VISIBLE
            if (winner==0) {
                wPlayer = "$firstPlayerName won"
                playerTitle.text = wPlayer
            }
            else {
                wPlayer = "$secondPlayerName won"
                playerTitle.text = wPlayer
            }
            return
        }//te pārbauda vai vēl ir brīvi lauki, ja nē, spēle arī beidzas
        else if (gameBoard.indexOf(-1)==-1) {
            gameEnded = true
            restartButton.visibility = View.VISIBLE
            wPlayer = "Draw"
            playerTitle.text = wPlayer
            return
        }
    //šeit, ja ir pvc, tad pēc cilvēka gājiena ir aizkava un gājienu dara dators
        if (pvpMode!=-1) {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                computerMove()
                //pēc datora gājiena atkal pārbauda vai ir uzvarētājs
                winner = checkWinner()
                if (winner!=-1) {
                    gameEnded = true
                    restartButton.visibility = View.VISIBLE
                    if (winner==0) {
                        wPlayer = "$firstPlayerName won"
                        playerTitle.text = wPlayer
                    }
                    else {
                        wPlayer = "$secondPlayerName won"
                        playerTitle.text = wPlayer
                    }
                }
                else if (gameBoard.indexOf(-1)==-1) {
                    gameEnded = true
                    restartButton.visibility = View.VISIBLE
                    wPlayer = "Draw"
                    playerTitle.text = wPlayer
                }
            }, 2000)
        }
    }
}