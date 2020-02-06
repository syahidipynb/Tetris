
/**
 * Game
 */
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class Game extends JFrame implements ActionListener {

    private MainPage mainPage;
    private Page startPage;
    private JPanel pages;
    private JPanel pauseModal;
    private PanelWithBg mainPanel;
    private PanelWithBg startPanel;
    private JPanel startPanelButtons;
    public JPanel pausePanel;
    private JPanel highScorePanel;
    private JPanel highScoreModal;
    private JPanel highScoreData;
    private JButton exitButton;
    private JButton startButton;
    private JButton highScoreButton;
    private JButton mainMenuButton;
    private JButton closeButton;
    private JButton restartButton;
    public JButton continueButton;
    private CardLayout layout;
    public JLabel winStatus;
    public Clip music;
    public Server server;
    public Client client;

    public final String START_PANEL = "Start Panel";
    public final String MAIN_PANEL = "Main Panel";
    public static final int SCREEN_WIDTH = 650;
    public static final int SCREEN_HEIGHT = 650;

    public Game() {
        super();
        Game self = this;

        this.layout = new CardLayout();
        this.pages = new JPanel(layout);
        this.startPage = new Page();
        this.mainPanel = new PanelWithBg("assets/Background.jpg");
        this.pauseModal = new JPanel();

        this.startPanel = new PanelWithBg("assets/Background.jpg");
        this.startPanelButtons = new JPanel();
        this.startPanelButtons.setOpaque(false);
        this.startPanelButtons.setLayout(new GridLayout(3, 1, 10, 10));
        this.startPanelButtons.setMaximumSize(new Dimension(150, 650));
        this.startPanelButtons.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        startButton = new JButton("Client");
        exitButton = new JButton("Exit");
        mainMenuButton = new JButton("Main Menu");
        highScoreButton = new JButton("Server");
        restartButton = new JButton("Restart");
        continueButton = new JButton("Continue");

        startButton.setBackground(new Color(46, 204, 113));
        exitButton.setBackground(new Color(231, 76, 60));
        highScoreButton.setBackground(new Color(241, 196, 15));
        restartButton.setBackground(new Color(46, 204, 113));
        mainMenuButton.setBackground(new Color(241, 196, 15));
        continueButton.setBackground(new Color(52, 152, 219));

        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        highScoreButton.addActionListener(this);
        mainMenuButton.addActionListener(this);
        restartButton.addActionListener(this);
        continueButton.addActionListener(this);

        startButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        startButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        exitButton.setForeground(Color.WHITE);
        highScoreButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        highScoreButton.setForeground(Color.WHITE);
        mainMenuButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        mainMenuButton.setForeground(Color.WHITE);
        restartButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        restartButton.setForeground(Color.WHITE);
        continueButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        continueButton.setForeground(Color.WHITE);

        this.startPanelButtons.add(startButton);
        this.startPanelButtons.add(highScoreButton);
        this.startPanelButtons.add(exitButton);

        this.startPage.setComponent(this.startPanel);
        this.startPage.getComponent().setLayout(new BoxLayout(this.startPage.getComponent(), BoxLayout.Y_AXIS));
        this.startPage.getComponent().add(Box.createVerticalGlue());
        this.startPage.getComponent().add(this.startPanelButtons);
        this.startPage.getComponent().add(Box.createVerticalGlue());
        this.startPage.render();

        this.highScorePanel = new JPanel();
        this.highScorePanel.setLayout(new BoxLayout(this.highScorePanel, BoxLayout.Y_AXIS));
        this.highScorePanel.setMaximumSize(new Dimension(200, 500));
        this.highScorePanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        JLabel highScoreTitle = new JLabel("Server");
        highScoreTitle.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));

        this.highScorePanel.add(highScoreTitle);
        this.highScorePanel.setBackground(new Color(44, 204, 113));
        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        closeButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(231, 76, 60));

        this.highScoreData = new JPanel(new GridLayout(6, 2, 10, 10));
        this.highScoreData.setOpaque(false);
        this.highScorePanel.add(this.highScoreData);

        this.highScoreModal = new JPanel();
        this.highScoreModal.setBackground(new Color(0, 0, 0, 120));
        this.highScoreModal.add(Box.createVerticalGlue());
        this.highScoreModal.add(this.highScorePanel);
        this.highScoreModal.add(Box.createVerticalGlue());
        this.startPage.setModal(this.highScoreModal);

        pausePanel = new JPanel();
        pausePanel.setLayout(new GridLayout(3, 1, 10, 10));
        pausePanel.setMaximumSize(new Dimension(150, 500));
        pausePanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        winStatus = new JLabel("Waiting your enemy..");
        this.pauseModal.setBackground(new Color(0, 0, 0, 120));
        this.pauseModal.add(Box.createVerticalGlue());
        this.pauseModal.add(pausePanel);
        this.pauseModal.add(Box.createVerticalGlue());

        this.pages.add(this.startPage, this.START_PANEL);

        this.setTitle("Wolf Tetris");
        this.setPreferredSize(new Dimension(Game.SCREEN_HEIGHT, Game.SCREEN_WIDTH));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.layout.show(this.pages, this.START_PANEL);

        this.setContentPane(pages);
        this.startPage.setBackground(Color.BLUE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();
        new Thread(new Runnable() {
            public void run() {
                try {
                    URL url = this.getClass().getClassLoader().getResource("assets/Music.mid");
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                    game.music = AudioSystem.getClip();
                    game.music.open(audioIn);
                    game.music.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Game self = this;
        switch (e.getActionCommand()) {
        case "Start":
        case "Restart":
            this.mainPage = new MainPage(this, true);
            this.mainPage.setComponent(this.mainPanel);
            this.mainPage.setModal(this.pauseModal);
            this.pages.add(this.mainPage, this.MAIN_PANEL);
            this.layout.show(this.pages, this.MAIN_PANEL);
            this.mainPage.requestFocusInWindow();
            this.mainPage.render();
            this.mainPage.startGame();

            this.pausePanel.add(this.mainMenuButton);
            exitButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
            this.pausePanel.add(this.exitButton);
            break;
        case "Main Menu":
            this.layout.show(this.pages, this.START_PANEL);
            exitButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
            this.startPanelButtons.add(this.exitButton);
            if (this.mainPage.isServer) {
                this.server.close();
            } else {
                this.client.close();
            }
            break;
        case "Highscore":
            break;
        case "Continue":
            this.mainPage.unpause();
            break;
        case "Client":
            String IP = JOptionPane.showInputDialog(this, "Enter Server IP Addres:", null);
            this.client = new Client(IP);
            this.client.start();

            if (this.client.isConnected) {
                this.mainPage = new MainPage(this, false);
                this.mainPage.setComponent(this.mainPanel);
                this.mainPage.setModal(this.pauseModal);
                this.pages.add(this.mainPage, this.MAIN_PANEL);
                this.layout.show(this.pages, this.MAIN_PANEL);
                this.mainPage.requestFocusInWindow();
                this.mainPage.render();
                this.mainPage.startGame();
                this.pausePanel.add(winStatus);
                exitButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
                this.pausePanel.add(this.exitButton);
            } else {
                JOptionPane.showMessageDialog(this, "Server not found");
            }

            break;
        case "Server":
            try {
                String serverIp = InetAddress.getLocalHost().toString();
                serverIp = serverIp.substring(serverIp.indexOf("/") + 1);
                JOptionPane.showMessageDialog(this, "Connect to this IP Address : " + serverIp);
            } catch (UnknownHostException uhEx) {
                System.out.println("ID host tidak ditemukan!");
            }
            this.server = new Server();
            this.server.start();
            this.mainPage = new MainPage(this, true);
            this.mainPage.setComponent(this.mainPanel);
            this.mainPage.setModal(this.pauseModal);
            this.pages.add(this.mainPage, this.MAIN_PANEL);
            this.layout.show(this.pages, this.MAIN_PANEL);
            this.mainPage.requestFocusInWindow();
            this.mainPage.render();
            this.mainPage.startGame();
            this.pausePanel.add(winStatus);
            exitButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
            this.pausePanel.add(this.exitButton);
            break;
        case "Exit":
            this.setVisible(false);
            this.dispose();
            System.exit(0);
            break;
        case "Close":
            this.startPage.hideModal();
            break;
        default:
            break;
        }
    }
}
