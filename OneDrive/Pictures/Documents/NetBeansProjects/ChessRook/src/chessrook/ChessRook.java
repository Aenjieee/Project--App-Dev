/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package chessrook;
/**
 *
 * @author Aenjiie
 */

import java.awt.*;
import java.io.*;
import java.util.Stack;
import javax.sound.sampled.*;
import javax.swing.*;

public class ChessRook {
    JFrame frame = new JFrame("Chess - Rook Movement");
    JPanel boardPanel = new JPanel(new GridLayout(8, 8));
    JButton[][] board = new JButton[8][8];
    JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton restartButton = new JButton("Restart");
    JButton undoButton = new JButton("Undo");
    Point rookPosition = new Point(0, 0);
    Stack<Point> moveStack = new Stack<>();
    Point lastMove = new Point(0, 0);

    ChessRook() {
        frame.setSize(500, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.NORTH);
        controlPanel.add(restartButton);
        controlPanel.add(undoButton);

        initializeBoard();

        try {
            ImageIcon rookIcon = new ImageIcon(getClass().getResource("rook.png"));
            Image scaledImage = rookIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            rookIcon = new ImageIcon(scaledImage);
            board[rookPosition.x][rookPosition.y].setIcon(rookIcon);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to load rook image: " + ex.getMessage());
        }

        restartButton.addActionListener(e -> restartGame());
        undoButton.addActionListener(e -> undoMove());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initializeBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);
                tile.setBackground((r + c) % 2 == 0 ? Color.WHITE : new Color(135, 206, 250));
                tile.setFont(new Font("Arial", Font.BOLD, 20));
                tile.setFocusable(false);

                tile.addActionListener(e -> handleMove((JButton) e.getSource()));
            }
        }
    }

    private void handleMove(JButton clickedTile) {
        Point clickedPosition = findTile(clickedTile);
        if (clickedPosition.equals(rookPosition)) {
            clearHighlights();
            highlightMoves(clickedTile);
        } else if (clickedTile.getBackground().equals(Color.BLACK)) {
            moveRook(clickedPosition);
        }
    }

    private void moveRook(Point newPosition) {
        moveStack.push(new Point(rookPosition));
        lastMove = new Point(rookPosition);
        board[rookPosition.x][rookPosition.y].setIcon(null);
        try {
            ImageIcon rookIcon = new ImageIcon(getClass().getResource("rook.png"));
            Image scaledImage = rookIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            rookIcon = new ImageIcon(scaledImage);
            board[newPosition.x][newPosition.y].setIcon(rookIcon);
            playSound("move.wav");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to load rook image: " + ex.getMessage());
        }
        rookPosition = newPosition;
        clearHighlights();
    }

    private void playSound(String soundFile) {
        try {
            InputStream is = getClass().getResourceAsStream(soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to play sound: " + e.getMessage());
        }
    }

    private void clearHighlights() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board[r][c].getBackground().equals(Color.BLACK)) {
                    board[r][c].setBackground((r + c) % 2 == 0 ? Color.WHITE : new Color(135, 206, 250));
                }
            }
        }
    }

    private void highlightMoves(JButton rookTile) {
        Point position = findTile(rookTile);

        for (int r = 0; r < 8; r++) {
            if (r != position.x) {
                board[r][position.y].setBackground(Color.BLACK);
            }
        }

        for (int c = 0; c < 8; c++) {
            if (c != position.y) {
                board[position.x][c].setBackground(Color.BLACK);
            }
        }
    }

    private Point findTile(JButton tile) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board[r][c] == tile) {
                    return new Point(r, c);
                }
            }
        }
        return null;
    }

    private void undoMove() {
        if (!moveStack.isEmpty()) {
            Point currentPosition = new Point(rookPosition); 
            Point previousMove = moveStack.pop(); 
            moveRook(previousMove);
            lastMove = currentPosition;
        }
    }

    private void restartGame() {
        board[rookPosition.x][rookPosition.y].setIcon(null);
        rookPosition = new Point(0, 0);
        try {
            ImageIcon rookIcon = new ImageIcon(getClass().getResource("rook.png"));
            Image scaledImage = rookIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            rookIcon = new ImageIcon(scaledImage);
            board[rookPosition.x][rookPosition.y].setIcon(rookIcon);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to load rook image: " + ex.getMessage());
        }
        clearHighlights();
        moveStack.clear();
        lastMove = new Point(0, 0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessRook::new);
    }
}
//Updated Files