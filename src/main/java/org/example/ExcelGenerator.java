package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Clase para generar reportes en excel
 *
 *
 * @author Jorge
 * @version 1.0
 * @since 2025-02-05
 */

public class ExcelGenerator {

    /**
     * Genera un reporte de equipos en formato Excel.
     *
     * @param teams Lista de equipos a incluir en el reporte.
     * @param playerDAO DAO de jugadores utilizado para obtener los jugadores de cada equipo.
     *
     */
    public static void generateTeamsReport(List<Team> teams, PlayerDAO playerDAO) {

        File directorio = new File("Reports");
        if (!directorio.exists()) {
            directorio.mkdir();
        }

        String dest = "Reports/teams_report.xlsx";
        String tempDest = "Reports/temp_teams_report.xlsx";

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Teams Report");

            sheet.setColumnWidth(0, 20 * 256);
            sheet.setColumnWidth(1, 30 * 256);
            sheet.setColumnWidth(2, 25 * 256);

            CellStyle headerStyle = createHeaderCellStyle(workbook);
            CellStyle textStyle = createTextCellStyle(workbook);

            int rowNum = 0;
            for (Team t : teams) {
                List<Player> players = playerDAO.getPlayersByTeam(t.getId());

                Row teamRow = sheet.createRow(rowNum++);
                teamRow.createCell(0).setCellValue("Equipo:");
                teamRow.getCell(0).setCellStyle(textStyle);
                teamRow.createCell(1).setCellValue(t.getName());
                teamRow.getCell(1).setCellStyle(textStyle);

                Row cityRow = sheet.createRow(rowNum++);
                cityRow.createCell(0).setCellValue("Ciudad:");
                cityRow.getCell(0).setCellStyle(textStyle);
                cityRow.createCell(1).setCellValue(t.getCity());
                cityRow.getCell(1).setCellStyle(textStyle);

                Row stadiumRow = sheet.createRow(rowNum++);
                stadiumRow.createCell(0).setCellValue("Estadio:");
                stadiumRow.getCell(0).setCellStyle(textStyle);
                stadiumRow.createCell(1).setCellValue(t.getStadium());
                stadiumRow.getCell(1).setCellStyle(textStyle);

                Row headerRow = sheet.createRow(rowNum++);
                headerRow.createCell(0).setCellValue("#");
                headerRow.createCell(1).setCellValue("Nombre");
                headerRow.createCell(2).setCellValue("Posición");

                headerRow.getCell(0).setCellStyle(headerStyle);
                headerRow.getCell(1).setCellStyle(headerStyle);
                headerRow.getCell(2).setCellStyle(headerStyle);

                if (players.isEmpty()) {
                    Row noPlayersRow = sheet.createRow(rowNum++);
                    noPlayersRow.createCell(0).setCellValue("No hay jugadores aún");
                    noPlayersRow.getCell(0).setCellStyle(textStyle);
                } else {
                    int count = 1;
                    for (Player p : players) {
                        Row playerRow = sheet.createRow(rowNum++);
                        playerRow.createCell(0).setCellValue(count++);
                        playerRow.getCell(0).setCellStyle(textStyle);
                        playerRow.createCell(1).setCellValue(p.getName());
                        playerRow.getCell(1).setCellStyle(textStyle);
                        playerRow.createCell(2).setCellValue(p.getPos());
                        playerRow.getCell(2).setCellStyle(textStyle);
                    }
                }

                rowNum++;
            }


            try (FileOutputStream fileOut = new FileOutputStream(tempDest)) {
                workbook.write(fileOut);
            }


            if (replaceFile(tempDest, dest)) {
                System.out.println("Excel generado en: " + new File(dest).getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(null,"No se pudo reemplazar el archivo. Verifica si está en uso." , "Error",JOptionPane.WARNING_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Intenta reemplazar el archivo destino con un archivo temporal, verificando si el archivo esta en uso.
     *
     * @param tempPath Ruta del archivo temporal.
     * @param destPath Ruta del archivo destino.
     * @return true si el archivo se reemplazo correctamente, false si no fue posible.
     */
    private static boolean replaceFile(String tempPath, String destPath) {
        File tempFile = new File(tempPath);
        File destFile = new File(destPath);



            if (!isFileInUse(destPath)) {
                if (destFile.exists() && !destFile.delete()) {
                    JOptionPane.showMessageDialog(null,"No se pudo reemplazar el archivo. Verifica si está en uso." , "Error",JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                return tempFile.renameTo(destFile);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        return false;
    }

    /**
     * Verifica si el archivo esta en uso intentando abrirlo en modo escritura.
     *
     * @param filePath Ruta del archivo a verificar.
     * @return true si el archivo esta en uso, false si no lo esta.
     */
    private static boolean isFileInUse(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false; // Si no existe, no está en uso
        }
        try {
            Files.newByteChannel(file.toPath(), StandardOpenOption.WRITE).close();
            return false; // Si podemos abrirlo en modo escritura, no está en uso
        } catch (IOException e) {
            return true; // No se pudo abrir, está en uso
        }
    }

    /**
     * Crea el estilo para las celdas de encabezado en el archivo Excel.
     *
     * @param workbook El libro de trabajo de Excel.
     * @return El estilo para las celdas de encabezado.
     */
    private static CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    /**
     * Crea el estilo para las celdas de texto en el archivo Excel.
     *
     * @param workbook El libro de trabajo de Excel.
     * @return El estilo para las celdas de texto.
     */
    private static CellStyle createTextCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }
}