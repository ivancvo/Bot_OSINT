package com.etb.clientes.utils;
import com.etb.clientes.models.DatosCliente;

import javax.sql.ConnectionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import com.etb.clientes.models.DatosCliente;

public class GestionBd {

    // nombre del archvo de base de datos
    private static final String URL_DB = "jdbc:sqlite:BaseDatosClientes.db";

    //iniciar la tabla
    public static void  inicializarBaseDatos(){
        String sql = "CREATE TABLE IF NOT EXISTS  clientes_busqueda("+
                     "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                     "nombre_buscado TEXT,"+
                     "titulo_encontrado TEXT,"+
                     "url_perfil TEXT,"+
                     "resumen TEXT,"+
                     "fecha_proceso DATETIME DEFAULT CURRENT_TIMESTAMP"+
                     ");";
        try (Connection conn = DriverManager.getConnection(URL_DB);
             Statement stmt = conn.createStatement()){
             stmt.execute(sql);
            System.out.println("base de datos con la tabla, conectada.");
        } catch (SQLException e){
            System.err.println("error al iniciar la base de datos: " + e.getMessage());
        }
    }
    // guardar un cliente
    public static void guardarCliente(DatosCliente cliente){
        String sql = "INSERT INTO clientes_busqueda(nombre_buscado, titulo_encontrado, url_perfil, resumen) INTO (?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(URL_DB);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombrecompleto());
            pstmt.setString(2, cliente.getTituloProfesional());
            pstmt.setString(3, cliente.getUrlPerfil());
            pstmt.setString(4, cliente.getResumen());

            pstmt.execute();
        }catch (SQLException e){
            System.err.println("Error al guardar en la bd: "+ e.getMessage());
        }
    }
}

