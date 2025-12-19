package com.etb.clientes.runners;

import com.microsoft.schemas.office.office.STInsetMode;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.random.RandomGenerator;


public class ConversorTxt {

    String archivoEntrada = "clientesbot.txt"; //archivo exportado de teradata
    String archivoSalida = "clientesbotp.csv";

    @Test
    public void convertirFormato(){
        System.out.println("convirtiendo...");

        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(new FileInputStream(archivoEntrada), StandardCharsets.ISO_8859_1));

             BufferedWriter escritor = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream(archivoSalida),StandardCharsets.UTF_8))) {

            String linea;
            int contador = 0;

            // escribir el encabezado limpio en el nuevo csv
            escritor.write("NombreCompleto, CODd_BD");
            escritor.newLine();

            while ((linea = lector.readLine())!= null){
                //ignorar anterior encabezado
                if (linea.contains("COD_BD") || linea.trim().isEmpty()){
                    continue;
                }

                String[] columnas = linea.split("\t");

                //prevencion de errores
                if (columnas.length <2){
                    columnas=linea.split("\\s{2,}"); // cortar donde haya 2 o mas espacios
                }
                if (columnas.length >= 2){
                    String id = columnas[0].trim();
                    String nombre= columnas[1].trim();

                    //cambiamos el orden, (nombre, id)
                    escritor.write(nombre + "," + id);
                    escritor.newLine();
                    contador++;
                }
            }
            System.out.println("conversion terminada " + contador + " clientes listos");
        }catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
