package main

import (
    "fmt"
)

func main() {
    board_size := 30

    board := make([][]int, board_size)
    for i := range board {
        board[i] = make([]int, board_size)
    }

    for i := range board {
        for j := range board[i] {
            board[i][j] = 0
        }
    }

    // Starting state
    board[14][15]=1
    board[15][15]=1
    board[15][16]=1
    board[15][17]=1
    board[15][18]=1
    board[16][18]=1

    fmt.Println("Starting State: ")
    printBoard(board)
    fmt.Println()

    game(board, 1, 10, true)
}

func game(board [][]int, round int, round_limit int, verbose bool) {
    if (round > round_limit) { return }

    if (verbose) {
        fmt.Printf("Round %v \n", round)
        printBoard(board)
        fmt.Println()
    }

    board_next_round := copyBoard(board)

    for i := range board {
        for j := range board[i] {
            gameCell(board, board_next_round, i, j)
        }
    }

    game(board_next_round, round+1, round_limit, verbose)
}

func gameCell(board [][]int, board_next_round [][]int, row int, col int) {
    var neighbours = 0
    var alive = (board[row][col] == 1)

    if (row > 0 && col > 0 && board[row-1][col-1] == 1) { neighbours += 1 }
    if (row > 0 && board[row-1][col] == 1) { neighbours += 1 }
    if (row > 0 && col < len(board[row]) - 1 && board[row-1][col+1] == 1) { neighbours += 1 }
    if (col > 0 && board[row][col-1] == 1) { neighbours += 1 }
    if (col < len(board[row]) - 1 && board[row][col+1] == 1) { neighbours += 1 }
    if (row < len(board) - 1 && col > 0 && board[row+1][col-1] == 1) { neighbours += 1 }
    if (row < len(board) - 1 && board[row+1][col] == 1) { neighbours += 1 }
    if (row < len(board) - 1 && col < len(board[row]) - 1 && board[row+1][col+1] == 1) { neighbours += 1 }

    if (alive && neighbours < 2) { board_next_round[row][col] = 0; }
    if (alive && neighbours > 3) { board_next_round[row][col] = 0; }
    if (!alive && neighbours == 3) { board_next_round[row][col] = 1; }
}

func printBoard(board [][]int) {
    for i := range board {
        for j := range board[i] {
            fmt.Print(board[i][j])
            fmt.Print(" ")
        }
        fmt.Println()
    }
}

func copyBoard(board [][]int) [][]int {
    board_next_round := make([][]int, len(board))
    for i := range board {
        board_next_round[i] = make([]int, len(board[i]))
        copy(board_next_round[i], board[i])
    }
    return board_next_round
}
