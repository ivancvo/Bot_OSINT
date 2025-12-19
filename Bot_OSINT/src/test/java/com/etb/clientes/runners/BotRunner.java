package com.etb.clientes.runners;

import com.etb.clientes.models.DatosCliente;
import com.etb.clientes.questions.ExtraerDatosYahoo;
import com.etb.clientes.tasks.BuscarEnYahoo;
import com.etb.clientes.tasks.GestionCookies;
import com.etb.clientes.utils.EscritorExcel;
import com.etb.clientes.utils.LectorExcel;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import java.util.List;

@ExtendWith(SerenityJUnit5Extension.class)
public class BotRunner {
    //managed da la instruccion a serenity para abrir el navegador
    @Managed(driver = "Chrome")
    WebDriver driver;

    //crea el actor
    Actor investigador = Actor.named("bot de Recoleccion");

    @Test // prueba que vamos a ejecutar
    public void ProbarFlujoDeBusquedaEnBing(){
        // dar la "habilidad" de usar el navegador
        investigador.can(BrowseTheWeb.with(driver));

        //LEER DATOS REALES DEL ARCHIVO
        List<DatosCliente> listaClientes = LectorExcel.leerDatos("clientes.xlsx");

        //preparacion para datos quemados

        //System.out.println("PRUEBA DE HUMO");
        //DatosCliente clientePrueba = new DatosCliente();

        //clientePrueba.setNombrecompleto("Shakira");

        //flujo rpa
        //abrir la url
        //investigador.attemptsTo(Open.url("https://www.bing.com"));

        //abrir y gestionar cookies
        investigador.attemptsTo(Open.url("https://es.search.yahoo.com/"));
        investigador.attemptsTo(GestionCookies.aceptarTodo());

        //repetir el proceso por cada cliente en la lista
        for (DatosCliente cliente : listaClientes){
            //si el nombre esta vacio se salta
            if(cliente.getNombrecompleto() == null || cliente.getNombrecompleto().isEmpty()){
                continue;
            }
            System.out.println("PROCESANDO A..." + cliente.getNombrecompleto() +"\n");

            investigador.attemptsTo(
                    BuscarEnYahoo.conLosDatos(cliente.getNombrecompleto())
            );


            //ejecutar la question para extraer los datos
            DatosCliente resultado = investigador.asksFor(ExtraerDatosYahoo.para(cliente));

            //verificacion por consola
            System.out.println("RESULTADOS EXTRAIDOS PARA: " + resultado.getNombrecompleto());
            System.out.println("TITULO: " + resultado.getTituloProfesional());
            System.out.println("URL: " + resultado.getUrlPerfil());
            System.out.println("RESUMEN: " + resultado.getResumen()+ "\n");

            //pausa para ver el navegador antes que se cierre
            try {Thread.sleep(5000);} catch (InterruptedException e){e.printStackTrace();}

        }
        System.out.println("generando archivo...");
        //usamos la misma losta porque java actualiza ya los objertos en la memoria
        EscritorExcel.guardarDatos(listaClientes, "resultados_busqueda_clientes.xlsx");

        System.out.println("proceso terminado ");
    }
}
