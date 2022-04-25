package tictactoe;

public enum Player {
    X {
        public String toString() {
            return "X";
        }
    }, O {
        public String toString() {
            return "O";
        }
    }
}
