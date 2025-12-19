package com.etb.clientes.runners;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.presentationml.x2006.main.STTLTimeNodeRestartType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DivisorCSV {
    //configuracion
    String archivoEntrada = "clientesbotp.csv";
    int registrosPorLote = 1000;

    @Test
    public void dividirArchivo() throws  IOException{
        System.out.println("dividiendo csv...");

        Reader in = new FileReader(archivoEntrada);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        List<String> headerNames = ((org.apache.commons.csv.CSVParser) records).getHeaderNames();

        int contador = 0;
        int lote = 1;
        CSVPrinter printer = crearNuevoLote(lote, headerNames);

        for (CSVRecord record : records){
            //escribir el registro en el archivo actual
            printer.printRecord(record);
            contador++;

            // si se llega al limite se cierra este y se abre otro
            if (contador >= registrosPorLote){
                printer.close();
                System.out.println("lote : "+ lote + " creado ("+ contador + "registros).");

                contador= 0;
                lote ++;
                printer = crearNuevoLote(lote, headerNames);
            }
        }
        printer.close();
        System.out.println("proceso terminado, se crearon " + lote +" archivos");
    }
    private CSVPrinter crearNuevoLote(int numeroLote, List<String> headers) throws  IOException{
        String nombreArchivo = "lote_" + numeroLote+ ".csv";
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(nombreArchivo));
        return new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers.toArray(new String[0])));

    }
}


