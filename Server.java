import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread {
   private static ServerSocket serverSocket;
   private static final int PORT = 1234;
   private int score;
   private String enemyScore;
   private boolean isConnected;
   public boolean isGameOver;
   private boolean closed;

   public Server() {
      this.isConnected = false;
      this.isGameOver = false;
      this.closed = false;
   }

   public void run() {
      System.out.println("Membuka port…\n");
      try {
         serverSocket = new ServerSocket(PORT); // Langkah 1.
      } catch (IOException ioEx) {
         System.out.println("Unable to attach to port!");
         System.exit(1);
      }
      do {
         handleClient();
      } while (true);
   }

   private void handleClient() {
      Socket link = null;
      try {
         link = serverSocket.accept();
         if (link.isConnected()) {
            this.isConnected = link.isConnected();

         }
         Scanner input = new Scanner(link.getInputStream());
         PrintWriter output = new PrintWriter(link.getOutputStream(), true);
         String pesan = input.nextLine();
         while (!this.closed) {
            if (this.isGameOver) {
               output.println("" + this.score + "(Game Over)");
            } else {
               output.println(this.score);

            }
            pesan = input.nextLine();
            this.enemyScore = pesan;

         }
      } catch (IOException ioEx) {
         ioEx.printStackTrace();
      } finally {
         try {
            System.out.println("\n* tutup koneksi...*");
            link.close();
         } catch (IOException ioEx) {
            System.out.println("Tidak bisa konek!");
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

   public boolean isConnected() {
      return this.isConnected;
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