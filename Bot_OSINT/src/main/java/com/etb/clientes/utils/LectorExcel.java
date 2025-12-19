package com.etb.clientes.utils;
import com.etb.clientes.models.DatosCliente;
import io.cucumber.java.bs.Dato;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LectorExcel {

    public static List<DatosCliente> leerDatos(String rutaArchivo){
        List<DatosCliente> listaClientes = new ArrayList<>();

        //abrir el archivo
        try (FileInputStream archivo = new FileInputStream(new File(rutaArchivo));
             Workbook workbook = new XSSFWorkbook(archivo)){
            Sheet hoja = workbook.getSheetAt(0); //leer la hoja 1

            // se comienza con la variable (i) en 1 para saltar los encabezados
            for (int i = 1; i <=hoja.getLastRowNum(); i++){
                Row fila = hoja.getRow(i);

                //si la fila esta vacia la saltamos
                if (fila == null) continue;

                DatosCliente cliente = new DatosCliente();

                //leemos la columna "A"
                Cell celdaNombre = fila.getCell(0);

                //validar que la celda tenga texto
                if(celdaNombre != null ){
                    cliente.setNombrecompleto(celdaNombre.toString());
                }
                listaClientes.add(cliente);
            }
            System.out.println("Archivo leido, se encontraron: "+ listaClientes.size()+" registros.");

        }catch (IOException e){
            //lanzar error si el archivo falla o no esta
            throw new RuntimeException("error al leer el archivo: " + e.getMessage());
        }
        return listaClientes;
    }
}
