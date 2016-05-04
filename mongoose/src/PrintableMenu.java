/**
 * Created by jordan on 03/05/16.
 */

import java.io.*;

public class PrintableMenu {

    StringBuilder sb;
    String logo;
    FileWriter fw;
    BufferedWriter bw;

    DatabaseHandler db;
    String [][] dummy;

    PrintableMenu() {
        //Initialises relevant variables, allows logo filename to be changed
        logo = "./mongoose.jpg";
        sb = new StringBuilder();
        db = new DatabaseHandler();
        dummy = db.returnMenuAsArray();

        sb.append("<html>");
        sb.append("<title>Menu HTML File" + "</title>");

        //Sets up the 'body' style and main header
        sb.append("<body bgcolor=khaki ");
        sb.append("style=\"font-family:helvetica;\">");
        sb.append("<img src=" + logo + " height=164 width=196>");
        sb.append("<font size=20 color=saddlebrown>" + "<center>");
        sb.append("<b><u>" + "Restaurant Menu" + " </b></u>");
        sb.append("</font>" + "</center>");
        sb.append("<br><br><br>");
        createTable();

        sb.append("</body>" + "</html>");

        //Writes menu to a .html file to be printed, raises error if it can't
        try {
            fw = new FileWriter("menu.html");
            bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();
        }

        catch (IOException e) {
            System.out.println("Error writing to file: " + e);
        }
    }

    //Creates an invisible table which contains and formats each food item
    public void createTable() {
        sb.append("<table style=\"width:100%\">");
        sb.append("<tr>");
        sb.append("<td><i><b>" + "Product: " + "</b></i></td>");
        sb.append("<td><i><b>" + "Category: " + "</b></i></td>");
        sb.append("<td><i><b>" + "Price: " + "</b></i></td>");
        for (int i = 0; i<dummy.length; i++) {
            if (dummy[i][1].equals("starters")) {
                sb.append("<tr>");
                String correctedString;
                for (int j=0; j<dummy[i].length; j++) {
                    sb.append("<td>");
                    sb.append("<i>");
                    if (j==dummy[i].length - 1) {
                        correctedString = dummy[i][j];
                        correctedString = correctedString.replace("£","&pound");
                        sb.append(correctedString);
                    }
                    else {
                        sb.append(dummy[i][j]);
                        System.out.println(j);
                    }
                    sb.append("<br><br>");
                    sb.append("</i>");
                    sb.append("</td>");
                }
                sb.append("</tr>");
            }
        }
        for (int i = 0; i<dummy.length; i++) {
            if (dummy[i][1].equals("mains")) {
                sb.append("<tr>");
                String correctedString;
                for (int j=0; j<dummy[i].length; j++) {
                    sb.append("<td>");
                    sb.append("<i>");
                    if (j==dummy[i].length - 1) {
                        correctedString = dummy[i][j];
                        correctedString = correctedString.replace("£","&pound");
                        sb.append(correctedString);
                    }
                    else {
                        sb.append(dummy[i][j]);
                        System.out.println(j);
                    }
                    sb.append("<br><br>");
                    sb.append("</i>");
                    sb.append("</td>");
                }
                sb.append("</tr>");
            }
        }
        for (int i = 0; i<dummy.length; i++) {
            if (dummy[i][1].equals("desserts")) {
                sb.append("<tr>");
                String correctedString;
                for (int j=0; j<dummy[i].length; j++) {
                    sb.append("<td>");
                    sb.append("<i>");
                    if (j==dummy[i].length - 1) {
                        correctedString = dummy[i][j];
                        correctedString = correctedString.replace("£","&pound");
                        sb.append(correctedString);
                    }
                    else {
                        sb.append(dummy[i][j]);
                        System.out.println(j);
                    }
                    sb.append("<br><br>");
                    sb.append("</i>");
                    sb.append("</td>");
                }
                sb.append("</tr>");
            }
        }
        for (int i = 0; i<dummy.length; i++) {
            if (dummy[i][1].equals("drinks")) {
                sb.append("<tr>");
                String correctedString;
                for (int j=0; j<dummy[i].length; j++) {
                    sb.append("<td>");
                    sb.append("<i>");
                    if (j==dummy[i].length - 1) {
                        correctedString = dummy[i][j];
                        correctedString = correctedString.replace("£","&pound");
                        sb.append(correctedString);
                    }
                    else {
                        sb.append(dummy[i][j]);
                        System.out.println(j);
                    }
                    sb.append("<br><br>");
                    sb.append("</i>");
                    sb.append("</td>");
                }
                sb.append("</tr>");
            }
        }
}

    public static void main(String[] args) {
        PrintableMenu pMenu = new PrintableMenu();
    }

}

//http://stackoverflow.com/questions/5744919/generating-output-in-java
//http://www.w3schools.com/html/html_tables.asp
