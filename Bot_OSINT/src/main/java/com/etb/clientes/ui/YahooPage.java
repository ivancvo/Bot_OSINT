package com.etb.clientes.ui;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class YahooPage {
    // barra del navegador de bing

    public static final Target CAJA_BUSQUEDA = Target.the("caja de busqueda")
            .located(By.name("p"));

    //enlace principal del perfil

    public static final Target TITULO_RESULTADO = Target.the("Titulo del enlace")
            .located(By.cssSelector("div.algo h3.title a"));

    //resumen de la targeta

    public static final Target RESUMEN_RESULTADO =  Target.the("Resumen del perfil")
            .located(By.cssSelector("div.algo div.compText"));

    // "div.algo p" <-- selector por si el del resumen no funciona

    // El botón azul de aceptar
    public static final Target BOTON_COOKIES = Target.the("Botón Aceptar Cookies")
            .located(By.name("agree"));
    // Si 'agree' falla By.xpath("//button[contains(text(), 'Aceptar')]")
}
