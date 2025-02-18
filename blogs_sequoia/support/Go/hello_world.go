package main

import (
    "strings"
    "bufio"
    "fmt"
    "os"
)

func main() {
    fmt.Print("Who are you? : ")
    reader := bufio.NewReader(os.Stdin)
    text, _ := reader.ReadString('\n')
    text = strings.TrimSpace(text)
    fmt.Println("Hello, " + text + "!")
}
