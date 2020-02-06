import java.io.*;
import java.net.*;
import java.util.*;

public class Client extends Thread {
   private static InetAddress host;
   private static final int PORT = 1234;
   private int score;
   private String enemyScore;
   private String IP;
   public boolean isGameOver;
   public boolean isConnected;
   private Socket link;
   private boolean closed;

   public Client(String IP) {
      this.closed = false;
      this.IP = IP;
      this.isGameOver = false;
      this.isConnected = true;
      try {
         host = InetAddress.getByName(this.IP);
         link = null;
         link = new Socket(host, PORT);
         this.isConnected = this.link.isConnected();

      } catch (UnknownHostException uhEx) {
         this.isConnected = false;
      } catch (IOException ioEx) {
         this.isConnected = false;
      }
   }

   public void run() {

      this.score = 0;
      this.accessServer();
   }

   private void accessServer() {
      try {
         Scanner input = new Scanner(link.getInputStream());
         PrintWriter output = new PrintWriter(link.getOutputStream(), true);
         String pesan, response;
         do {
            if (this.isGameOver) {
               output.println("" + this.score + "(Game Over)");
            } else {
               output.println(this.score);
            }
            response = input.nextLine();
            this.enemyScore = response;
         } while (!this.closed);
      } catch (IOException ioEx) {
         ioEx.printStackTrace();
      } finally {
         try {
            link.close();
         } catch (IOException ioEx) {
            System.exit(1);
         }
      }
   }

   public String getEnemyScore() {
      return this.enemyScore;
   }

   public void setScore(int score) {
      this.score = score;
   }

   public void close() {
      this.closed = true;
      // try {
      // link.close();
      // } catch (IOException ioEx) {
      // System.exit(1);
      // }
   }
}