package org.example;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

    public class PDFGenerator {
        PlayerDAO playerDAO;

        public static void generateTeamsReport(List<Team> teams, PlayerDAO playerDAO) {

            File directorio = new File("Reports");
            if (!directorio.exists()){
                directorio.mkdir();
            }
            String dest = "Reports/teams_report.pdf";

            try {
                PdfWriter writer = new PdfWriter(dest);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                for (Team t : teams) {

                    List<Player> players = playerDAO.getPlayersByTeam(t.getId());


                    Paragraph teamTitle = new Paragraph(t.getName())
                            .setBold()
                            .setFontSize(18)
                            .setTextAlignment(TextAlignment.CENTER);
                    document.add(teamTitle);


                    document.add(new Paragraph("Ciudad: " + t.getCity()));
                    document.add(new Paragraph("Estadio: " + t.getStadium()));
                    document.add(new Paragraph(" "));


                    com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List().setSymbolIndent(12).setListSymbol("•");
                    int count = 1;

                    if(players.isEmpty()){
                        list.add(new ListItem("No hay Jugadores aun"));
                    }else {
                        for (Player p : players) {
                            list.add(new ListItem(count + ". " + p.getName() + " - " + p.getPos()));
                            count++;
                        }
                    }

                    document.add(list);


                    document.add(new Paragraph(" ").setFontSize(12));
                    document.add(new Paragraph("---------------------------------------").setTextAlignment(TextAlignment.CENTER));
                    document.add(new Paragraph(" "));
                }

                document.close();
                System.out.println("PDF generado en: " + new File(dest).getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
    }
}
