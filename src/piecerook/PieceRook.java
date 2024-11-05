/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package piecerook;

/**
 *
 * @author Aenjiie
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PieceRook {
    int windowWidth = 600;
    int windowHeight = 600;

    JFrame frame = new JFrame("Piece Movement");

    JPanel boardPanel = new JPanel();
    JButton[][] tiles = new JButton[8][8];

    Point piecePosition = new Point(0, 0); 

    PieceRook() {
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        boardPanel.setLayout(new GridLayout(8, 8));
        frame.add(boardPanel, BorderLayout.CENTER);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                JButton tile = new JButton();
                tiles[r][c] = tile;
                boardPanel.add(tile);
                tile.setBackground((r + c) % 2 == 0 ? Color.WHITE : Color.PINK); 
                tile.setFont(new Font("Arial", Font.BOLD, 20));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton clickedTile = (JButton) e.getSource();
                        handleMove(clickedTile);
                    }
                });
            }
        }

        try {
            ImageIcon pieceIcon = new ImageIcon(getClass().getResource("piece.png"));
            Image scaledImage = pieceIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            pieceIcon = new ImageIcon(scaledImage);
            tiles[piecePosition.x][piecePosition.y].setIcon(pieceIcon); 
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to load piece image: " + ex.getMessage());
        }

        frame.setVisible(true);
    }

    private void handleMove(JButton clickedTile) {
        Point clickedPosition = findTile(clickedTile);
        if (isValidMove(clickedPosition)) {
            movePiece(clickedPosition);
        }
    }

    private boolean isValidMove(Point newPosition) {
        int deltaX = Math.abs(newPosition.x - piecePosition.x);
        int deltaY = Math.abs(newPosition.y - piecePosition.y);
        return (deltaX == 0 && deltaY != 0) || (deltaX != 0 && deltaY == 0);
    }

    private void movePiece(Point newPosition) {
        tiles[piecePosition.x][piecePosition.y].setIcon(null);
        try {
            ImageIcon pieceIcon = new ImageIcon(getClass().getResource("piece.png"));
            Image scaledImage = pieceIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            pieceIcon = new ImageIcon(scaledImage);
            tiles[newPosition.x][newPosition.y].setIcon(pieceIcon);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Failed to load piece image: " + ex.getMessage());
        }

        // Update the piece's position
        piecePosition = newPosition;
    }

    private Point findTile(JButton tile) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (tiles[r][c] == tile) {
                    return new Point(r, c);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PieceRook());
    }
}
