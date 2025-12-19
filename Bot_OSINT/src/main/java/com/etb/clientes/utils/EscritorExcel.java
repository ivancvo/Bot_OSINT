package com.etb.clientes.utils;
import com.etb.clientes.models.DatosCliente;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class EscritorExcel {
    public static void guardarDatos(List<DatosCliente>datos, String rutaSalida){
        // crear el libro y lahoja
        try (Workbook workbook = new XSSFWorkbook()){
            Sheet hoja = workbook.createSheet("Resultados OSINT clientes ");

            // estilo del encabezado
            CellStyle estiloHeader = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            estiloHeader.setFont(font);
            estiloHeader.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            estiloHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            //fila de encabezados
            Row headerRow = hoja.createRow(0);
            String[] columnas = {"Nombre Buscado", "Titulo Encontrado", "URL perfil", "Resumen"};

            for (int i = 0; i < columnas.length; i ++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(estiloHeader);
            }
            int rowNum = 1;
            for (DatosCliente cliente: datos){
                Row row = hoja.createRow(rowNum++);

                // llenamos las celdas, manejando los nulos
                row.createCell(0).setCellValue(cliente.getNombrecompleto() != null ? cliente.getNombrecompleto() : "");
                row.createCell(1).setCellValue(cliente.getTituloProfesional() != null ? cliente.getTituloProfesional() : "no encontrado");
                row.createCell(2).setCellValue(cliente.getUrlPerfil() !=null ? cliente.getUrlPerfil() : "");
                row.createCell(3).setCellValue(cliente.getResumen() !=null ? cliente.getResumen() : "");
            }
            //auto ajustar ancho de las columnas
            for (int i = 0; i <columnas.length; i++){
                hoja.autoSizeColumn(i);
            }

            //escribir el archivo
            try (FileOutputStream fileOut = new FileOutputStream(rutaSalida)){
                workbook.write(fileOut);
                System.out.println("artchivo guardado correctamente " + rutaSalida);
            }

        }catch (IOException e){
            System.err.println("error al guardar el archivo: "+ e.getMessage());

        }
    }
}
