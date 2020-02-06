
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * MainPage
 */
public class MainPage extends Page implements KeyListener {

    private Board gameBoard;
    private Board nextBoard;
    private Shape currentShape;
    private Shape nextShape;
    private JPanel gameBoardContainer;
    private JPanel nextBoardContainer;
    private JPanel sideBar;
    private JLabel score;
    private JLabel enemyScore;
    private JPanel scoreContainer;
    private JPanel menuContainer;
    private ArrayList<Blocks> blocks;
    private Game parent;
    private boolean paused;
    private int gameSpeed;
    public boolean isServer;

    public MainPage(Game parent, boolean isServer) {
        super();
        this.parent = parent;
        this.isServer = isServer;
    }

    @Override
    public void render() {
        this.setComponent(new JPanel());
        gameSpeed = 1000;

        this.setFocusable(true);
        this.addKeyListener(this);
        this.paused = true;
        this.gameBoardContainer = new JPanel();
        this.nextBoardContainer = new JPanel();
        this.gameBoardContainer.setOpaque(false);
        this.nextBoardContainer.setOpaque(false);
        this.sideBar = new JPanel();
        this.sideBar.setLayout(new GridLayout(3, 1, 0, 5));
        this.sideBar.setOpaque(false);
        this.menuContainer = new JPanel();
        this.menuContainer.setOpaque(false);
        JButton pauseButton = new JButton("Pause");
        pauseButton.setBackground(new Color(52, 152, 219));
        pauseButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        pauseButton.setForeground(Color.WHITE);
        MainPage self = this;
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.paused = !self.paused;
                if (self.paused) {

                    // try {
                    // t.wait();
                    // } catch (Exception err) {
                    // // TODO: handle exception
                    // }
                    self.showModal();
                } else {
                    // t.notify();
                    self.hideModal();
                }
            }
        });
        // this.menuContainer.add(pauseButton);
        this.score = new JLabel("0", SwingConstants.CENTER);

        this.gameBoard = new Board(0, 0, 300, 600, 10, 20, "assets/MainBoardBackground.jpg");
        this.nextBoard = new Board(0, 0, 150, 150, 5, 5, "assets/NextBoardBackground.jpg");
        // this.component.setOpaque(true);
        // this.component.setBackground(Color.red);
        // this.modal.setOpaque(false);
        // this.modal.setBackground(Color.red);
        // this.modal.add(new JLabel("PAUSED"));

        this.blocks = new ArrayList<Blocks>();
        this.currentShape = ShapeFactory.getRandomShape();
        this.nextShape = ShapeFactory.getRandomShape();
        this.currentShape.toActiveShape();
        this.gameBoard.add(this.currentShape);
        this.nextBoard.add(this.nextShape);
        this.gameBoardContainer.setPreferredSize(new Dimension(400, Game.SCREEN_HEIGHT));
        this.sideBar.setPreferredSize(new Dimension(Game.SCREEN_WIDTH - 450, Game.SCREEN_HEIGHT - 10));
        this.sideBar.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);

        this.scoreContainer = new JPanel(new GridLayout(2, 2, 2, 2));
        this.scoreContainer.setOpaque(false);

        this.scoreContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.scoreContainer.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.scoreContainer.add(new JLabel("Enemy Score", SwingConstants.CENTER));
        this.enemyScore = new JLabel("0", SwingConstants.CENTER);
        this.scoreContainer.add(this.enemyScore);
        this.scoreContainer.add(new JLabel("Your Score", SwingConstants.CENTER));
        this.scoreContainer.add(this.score);
        this.gameBoardContainer.add(this.gameBoard);
        this.nextBoardContainer.add(this.nextBoard);
        this.sideBar.add(this.nextBoardContainer);
        this.sideBar.add(this.scoreContainer);
        this.sideBar.add(this.menuContainer);
        this.component.setBackground(Color.WHITE);
        this.component.add(this.gameBoardContainer);
        this.component.add(this.sideBar);
    }

    public void startGame() {
        Random rand = new Random();
        this.paused = false;
        Timer xxy = new Timer();
        MainPage self = this;

        xxy.schedule(new TimerTask() {
            @Override
            public void run() {
                String skorMusuh;
                if (self.isServer) {
                    skorMusuh = self.parent.server.getEnemyScore();
                } else {
                    skorMusuh = self.parent.client.getEnemyScore();
                }

                if (self.isServer) {
                    self.paused = false;
                    if (!self.parent.server.isConnected()) {
                        self.paused = true;
                        skorMusuh = "Waiting for client...";
                    }

                }
                self.enemyScore.setText(skorMusuh);
                if (!self.paused) {

                    if (!self.currentShape.moveDown(self.gameBoard.blocked)) {

                        if (self.isGameOver()) {
                            this.cancel();
                        } else {
                            for (Blocks block : self.currentShape.getBlocks()) {
                                self.blocks.add(block);
                                self.gameBoard.blocked[block.getX()][block.getY() + self.currentShape.y / 30] = true;
                                self.gameBoard.blocks[block.getX()][block.getY() + self.currentShape.y / 30] = block;
                                self.gameBoard.add(block);
                                block.setPos(block.getX(), block.getY() + self.currentShape.y / 30);
                            }
                            self.nextBoard.remove(self.nextShape);
                            self.nextShape.toActiveShape();
                            self.currentShape = self.nextShape;
                            self.nextShape = ShapeFactory.getRandomShape();
                            for (int i = 0; i <= rand.nextInt(4); i++) {
                                self.nextShape.rotate(new boolean[5][5]);
                            }
                            self.nextBoard.add(self.nextShape);
                            self.nextBoard.revalidate();
                            self.nextBoard.repaint();
                            self.gameBoard.add(self.currentShape);
                        }
                    }
                    int newScore = self.gameBoard.clear();
                    if (newScore > 0) {
                        newScore += Integer.parseInt(self.score.getText());
                        self.score.setText(Integer.toString(newScore));
                        if (self.isServer) {
                            self.parent.server.setScore(newScore);
                        } else {
                            self.parent.client.setScore(newScore);
                        }
                    }
                }

            }
        }, 1000, 1000);
        // xxy.schedule(new TimerTask() {

        // }, self.gameSpeed, self.gameSpeed);
        super.render();
    }

    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
        case 'L':
        case 'l':
            if (!this.paused) {
                this.currentShape.moveRight(this.gameBoard.blocked);
            }
            break;
        case 'J':
        case 'j':
            if (!this.paused) {
                this.currentShape.moveLeft(this.gameBoard.blocked);
            }
            break;
        case 'o':
        case 'O':
            if (!this.paused) {
                this.currentShape.rotate(this.gameBoard.blocked);
            }
            break;
        case 'k':
        case 'K':
            if (!this.paused) {
                this.currentShape.moveDown(this.gameBoard.blocked);
            }
            break;
        default:
            break;
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public boolean isGameOver() {
        for (Blocks block : this.currentShape.getBlocks()) {
            if (this.gameBoard.blocked[block.getX()][0] || block.getY() + this.currentShape.y / 30 < 0) {
                this.showModal();
                if (this.isServer) {
                    this.parent.server.isGameOver = true;
                } else {
                    this.parent.client.isGameOver = true;
                }
                String skorMusuh;

                while (true) {
                    if (this.isServer) {
                        skorMusuh = this.parent.server.getEnemyScore();
                    } else {
                        skorMusuh = this.parent.client.getEnemyScore();
                    }
                    System.out.println(skorMusuh);
                    if (skorMusuh.contains("(Game Over)")) {
                        if (Integer.parseInt(this.score.getText()) > Integer
                                .parseInt(skorMusuh.substring(0, skorMusuh.indexOf('(')))) {
                            this.parent.winStatus.setText("YOU WIN!");
                        } else if (Integer.parseInt(this.score.getText()) == Integer
                                .parseInt(skorMusuh.substring(0, skorMusuh.indexOf('(')))) {
                            this.parent.winStatus.setText("DRAW");
                        } else {
                            this.parent.winStatus.setText("YOU LOSE");
                        }
                        break;
                    }
                }

                try {
                    URL url = this.getClass().getClassLoader().getResource("assets/GameOver.mid");
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }

                return true;
            }
        }
        return false;
    }

    public void unpause() {
        this.paused = false;
        this.hideModal();
    }
}
