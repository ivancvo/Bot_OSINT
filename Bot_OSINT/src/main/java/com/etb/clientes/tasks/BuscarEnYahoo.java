package com.etb.clientes.tasks;

import com.etb.clientes.ui.YahooPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.Keys;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;


public class BuscarEnYahoo implements Task {
    private final String nombrecompleto;

    public BuscarEnYahoo(String nombrecompleto){
        this.nombrecompleto = nombrecompleto;
    }

    public static BuscarEnYahoo conLosDatos(String nombrecompleto){
        return instrumented(BuscarEnYahoo.class, nombrecompleto);
    }

    @Override
    public <T extends  Actor> void performAs (T actor){
        String query = String.format ("site:linkedin.com/in/ \"%s\" Colombia", nombrecompleto);

        actor.attemptsTo(
                //tiempo de espera para que cargue la caja de busqueda
                WaitUntil.the(YahooPage.CAJA_BUSQUEDA, isVisible()).forNoMoreThan(10).seconds(),

                Enter.theValue(query).into(YahooPage.CAJA_BUSQUEDA).thenHit(Keys.ENTER)
        );
    }
}
