package model;

public class Board {
    private Gem[][] grid;

    public Board(int rows, int cols) {
        grid = new Gem[rows][cols];
    }
}
