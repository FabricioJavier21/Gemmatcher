package model;

import java.util.*;

public class Board {

    private Gem[][] grid;
    private int rows;
    private int cols;
    private Random random = new Random();

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Gem[rows][cols];
        generateBoard();
    }

    private void generateBoard() {
        GemType[] types = GemType.values();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Gem(types[random.nextInt(types.length)]);
            }
        }
    }

    public Gem getGem(int row, int col) {
        return grid[row][col];
    }

    public void swap(int r1, int c1, int r2, int c2) {
        Gem temp = grid[r1][c1];
        grid[r1][c1] = grid[r2][c2];
        grid[r2][c2] = temp;
    }

    public boolean isAdjacent(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2) == 1;
    }

    public Set<int[]> findMatches() {
        Set<int[]> matches = new HashSet<>();

        // Horizontal
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols - 2; c++) {
                GemType t = grid[r][c].getType();
                if (grid[r][c + 1].getType() == t &&
                        grid[r][c + 2].getType() == t) {

                    matches.add(new int[]{r, c});
                    matches.add(new int[]{r, c + 1});
                    matches.add(new int[]{r, c + 2});
                }
            }
        }

        // Vertical
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows - 2; r++) {
                GemType t = grid[r][c].getType();
                if (grid[r + 1][c].getType() == t &&
                        grid[r + 2][c].getType() == t) {

                    matches.add(new int[]{r, c});
                    matches.add(new int[]{r + 1, c});
                    matches.add(new int[]{r + 2, c});
                }
            }
        }

        return matches;
    }

    public void removeMatches(Set<int[]> matches) {
        for (int[] pos : matches) {
            grid[pos[0]][pos[1]] = null;
        }
    }

    public void collapse() {
        for (int c = 0; c < cols; c++) {
            int writeRow = rows - 1;
            for (int r = rows - 1; r >= 0; r--) {
                if (grid[r][c] != null) {
                    grid[writeRow][c] = grid[r][c];
                    writeRow--;
                }
            }
            while (writeRow >= 0) {
                grid[writeRow][c] =
                        new Gem(GemType.values()[random.nextInt(GemType.values().length)]);
                writeRow--;
            }
        }
    }
}


