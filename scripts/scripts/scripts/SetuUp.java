package scripts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SetuUp {
  private static File resourcesDir;
  private static BufferedWriter w;
  private static Scanner scan;

  public static void main(String[] args) {
    System.out.println("SetUp script init.....");
    try {
      resourcesDir = new File("src/main/resources/");
      w = new BufferedWriter(new FileWriter(resourcesDir + "/application.properties"));
      scan = new Scanner(System.in);

      resourcesDir.mkdir();
      w.write("spring.datasource.driver-class-name=org.mariadb.jdbc.Driver\n");
      w.write("apiPrefix=/api/v1\n");
      w.write("dev=false\n");

      System.out.println("Insert Your database username: ");
      String username = scan.nextLine();

      System.out.println("Insert Your database password: ");
      String password = scan.nextLine();

      System.out.println("Insert Your database name: ");
      String dbName = scan.nextLine();

      w.write("spring.datasource.username=" + username + System.lineSeparator());
      w.write("spring.datasource.password=" + password + System.lineSeparator());
      w.write(
          "spring.datasource.url=jdbc:mariadb://localhost:3306/" + dbName + System.lineSeparator());

      w.close();
      System.out.println("Finish!!");
      scan.nextLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
