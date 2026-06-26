package out;
import java.util.Scanner;
import java.util.Arrays;
public class Minesweeper {
    public static final String e = "\u001B[0m";
    public static final String one = "\u001B[34m";
    public static final String two = "\u001B[32m";
    public static final String three = "\u001B[31m";
    public static final String four = "\u001B[35m";
    public static final String five = "\u001B[33m";
    public static final String six = "\u001B[36m";
    public static final String seven = "\u001B[30m";
    public static final String eight = "\u001B[38m";
    public static final String bold = "\u001B[1m";

    public static final String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    
    public static Scanner input = new Scanner(System.in);
    public static String[][] board = {{}};
    public static String[][] shown = {{}};
    public static void main(String[] args) {
        System.out.println(three + bold + "HOW TO PLAY:" + e + "\nWhen asked for your input,\ngive your answer in the format\n" + two + "ROW,COLUMN,FLAG" + e + " or if you do not want\nto place a flag, leave it out.\nFor example, \"a,e,f\" would mean that\nyou want to play on row a, column e\nand what you want to play is a flag.\n\"e,u\" would mean you want to play on\nrow e, column u and would like to\nclear that space.\n\nGot it?\n");
        System.out.print("What size minesweeper board do you want (small, medium, or large): " + one);
        String choice = input.nextLine();
        System.out.print(e);
        while (!(choice.equals("small") || choice.equals("medium") || choice.equals("large"))) {
            System.out.println("\nYour choice must be \"small\", \"medium\", or \"large\"");
            System.out.print("What size minesweeper board do you want (small, medium, or large): " + one);
            choice = input.nextLine();
            System.out.print(e);
        }
        int mines = 0;
        int size = 0;
        switch (choice) {
            case "small" -> {board = new String[9][9]; mines = 10; shown = new String[9][9]; size = 9;}
            case "medium" -> {board = new String[15][15]; mines = 28; shown = new String[15][15]; size = 15;}
            case "large" -> {board = new String[21][21]; mines = 61; shown = new String[21][21]; size = 21;}
        }
        for (int i = 0; i < mines; i++) {
            int x = (int)(Math.random() * board.length);
            int y = (int)(Math.random() * board.length);
            while (board[x][y] != null) {
                x = (int)(Math.random() * board.length);
                y = (int)(Math.random() * board.length);
            }
            board[x][y] = "M";
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == null) {
                    board[i][j] = " ";
                }
                shown[i][j] = "F";
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].equals(" ")) {
                    int count = 0;
                    if (i > 0 && i < board.length) {
                        if (board[i - 1][j].equals("M")) {
                            count++;
                        } if (j != 0) {
                            if (board[i - 1][j - 1].equals("M")) {
                                count++;
                            }
                        } if (j != board.length - 1) {
                            if (board[i - 1][j + 1].equals("M")) {
                                count++;
                            }
                        }
                    } if (i < board.length - 1) {
                        if (board[i + 1][j].equals("M")) {
                            count++;
                        } if (j != 0) {
                            if (board[i + 1][j - 1].equals("M")) {
                                count++;
                            }
                        } if (j != board.length - 1) {
                            if (board[i + 1][j + 1].equals("M")) {
                                count++;
                            }
                        }
                    } if (j > 0 && j < board.length) {
                        if (board[i][j - 1].equals("M")) {
                            count++;
                        }
                    } if (j < board.length - 1) {
                        if (board[i][j + 1].equals("M")) {
                            count++;
                        }
                    }
                    board[i][j] = "" + count;
                }
            }
        }
        int x = (int)(Math.random() * board.length);
        int y = (int)(Math.random() * board.length);
        while (!board[x][y].equals("0")) {
            x = (int)(Math.random() * board.length);
            y = (int)(Math.random() * board.length);
        }
        fill(x, y);
        spread();
        spread();
        System.out.println(e);
        printBoard();
        boolean hitMine = false;
        while (numCovered() != mines && hitMine == false) {
            System.out.print("\nWhere would you like to play? " + two);
            String[] play = input.nextLine().split(",");
            System.out.print(e);
            boolean isntValid = true;
            while (isntValid) {
                if (play.length != 2 && play.length != 3) {
                    System.out.println(three + "\nInvalid input!" + e);
                    System.out.print("\nPlease try again: ");
                    play = input.nextLine().split(",");
                }
                else if (Arrays.asList(letters).indexOf(play[0]) >= size || Arrays.asList(letters).indexOf(play[1]) >= size) {
                    System.out.println(three + "\nInvalid space to play!" + e);
                    System.out.print("\nPlease try again: ");
                    play = input.nextLine().split(",");
                }
                else if (Arrays.asList(letters).indexOf(play[0]) == -1 || Arrays.asList(letters).indexOf(play[1]) == -1) {
                    System.out.println(three + "\nInvalid row or column!" + e);
                    System.out.print("\nPlease try again: ");
                    play = input.nextLine().split(",");
                }
                else if (shown[Arrays.asList(letters).indexOf(play[0])][Arrays.asList(letters).indexOf(play[1])].equals(" ")) {
                    System.out.println(three + "\nYou can only play on a square tile or a flag!" + e);
                    System.out.print("\nPlease try a different space: ");
                    play = input.nextLine().split(",");
                } else if (shown[Arrays.asList(letters).indexOf(play[0])][Arrays.asList(letters).indexOf(play[1])].equals("B") && play.length == 2) {
                    System.out.println(three + "\nThat space is flagged!" + e);
                    System.out.print("\nChoose a different space or unflag this one before playing it again: ");
                    play = input.nextLine().split(",");
                }
                else {
                    isntValid = false;
                }
            } 
            int row = Arrays.asList(letters).indexOf(play[0]);
            int col = Arrays.asList(letters).indexOf(play[1]);
            if (play.length == 3) {
                if (shown[row][col].equals("B")) {
                    shown[row][col] = "F";
                    System.out.println();
                    printBoard();
                } else {
                    shown[row][col] = "B";
                    System.out.println();
                    printBoard();
                }
            } else {
                if (board[row][col].equals("M")) {
                    System.out.println(three + "You hit a mine! Game over.\n" + e);
                    printHitMine();
                    hitMine = true;
                } else {
                    fill(row, col);
                    spread();
                    spread();
                    System.out.println();
                    printBoard();
                }
            }
        }
        if (numCovered() == mines) {
            System.out.println(two + "\nCongratulations, you won!\n" + e);
            printHitMine();
        }
        System.out.print("\nType \"done\" to exit: ");
        String isDone = input.nextLine();
        while (!isDone.equals("done")) {
            System.out.print("Ummm, I said to type \"done\" not \"" + isDone + "\"\nTry again: ");
            isDone = input.nextLine();
        }
    }
    public static void printBoard() {
        System.out.print("  ");
        for (int i = 0; i < board.length; i++) {
            System.out.print(letters[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(letters[i] + " ");
            for (int j = 0; j < board.length; j++) {
                if (shown[i][j].equals("F")) {
                    System.out.print("\u25A0 ");
                } else if (shown[i][j].equals("B")) {
                    System.out.print(three + bold + "F " + e);
                } else switch (board[i][j]) {
                    case "M" -> System.out.print("M ");
                    case "0" -> System.out.print("0 ");
                    case "1" -> System.out.print(one + "1 " + e);
                    case "2" -> System.out.print(two + "2 " + e);
                    case "3" -> System.out.print(three + "3 " + e);
                    case "4" -> System.out.print(four + "4 " + e);
                    case "5" -> System.out.print(five + "5 " + e);
                    case "6" -> System.out.print(six + "6 " + e);
                    case "7" -> System.out.print(seven + "7 " + e);
                    case "8" -> System.out.print(eight + "8 " + e);
                }
            }
            System.out.println();
        }
    }
    public static void printHitMine() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                switch (board[i][j]) {
                    case "M" -> System.out.print(bold + three + "M " + e);
                    case "0" -> System.out.print("0 ");
                    case "1" -> System.out.print(one + "1 " + e);
                    case "2" -> System.out.print(two + "2 " + e);
                    case "3" -> System.out.print(three + "3 " + e);
                    case "4" -> System.out.print(four + "4 " + e);
                    case "5" -> System.out.print(five + "5 " + e);
                    case "6" -> System.out.print(six + "6 " + e);
                    case "7" -> System.out.print(seven + "7 " + e);
                    case "8" -> System.out.print(eight + "8 " + e);
                }
            }
            System.out.println();
        }
    }
    public static int numCovered() {
        int count = 0;
        for (String[] row : shown) {
            for (String item : row) {
                if (item.equals("F") || item.equals("B")) {
                    count++;
                }
            }
        }
        return count;
    }
    public static void printRawBoard() {
        for (String[] row : board) {
            for (String item : row) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }
    public static void printRawShown() {
        for (String[] row : shown) {
            for (String item : row) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }
    public static void fill(int x, int y) {
        shown[x][y] = " ";
        if (board[x][y].equals("0")) {
            if (x > 0) {
                if (board[x - 1][y].equals("0") && shown[x - 1][y].equals("F")) {
                    fill(x - 1, y);
                }
            }
            if (x < board.length - 1) {
                if (board[x + 1][y].equals("0") && shown[x + 1][y].equals("F")) {
                    fill(x + 1, y);
                }
            }
            if (y > 0) {
                if (board[x][y - 1].equals("0") && shown[x][y - 1].equals("F")) {
                    fill(x, y - 1);
                }
            }
            if (y < board.length - 1) {
                if (board[x][y + 1].equals("0") && shown[x][y + 1].equals("F")) {
                    fill(x, y + 1);
                }
            }
        }
    }
    public static void spread() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].equals("0") && shown[i][j].equals(" ")) {
                    if (i > 0 && i < board.length) {
                        shown[i - 1][j] = " ";
                        if (j != 0) {
                            shown[i - 1][j - 1] = " ";
                            if (board[i - 1][j - 1].equals("0")) {
                                fill(i - 1, j - 1);
                            }
                        } if (j != board.length - 1) {
                            shown[i - 1][j + 1] = " ";
                            if (board[i - 1][j + 1].equals("0")) {
                                fill(i - 1, j + 1);
                            }
                        }
                    } if (i < board.length - 1) {
                        shown[i + 1][j] = " ";
                        if (j != 0) {
                            shown[i + 1][j - 1] = " ";
                            if (board[i + 1][j - 1].equals("0")) {
                                fill(i + 1, j - 1);
                            }
                        } if (j != board.length - 1) {
                            shown[i + 1][j + 1] = " ";
                            if (board[i + 1][j + 1].equals("0")) {
                                fill(i + 1, j + 1);
                            }
                        }
                    } if (j > 0 && j < board.length) {
                        shown[i][j - 1] = " ";
                    } if (j < board.length - 1) {
                        shown[i][j + 1] = " ";
                    }
                }
            }
        }
    }
}