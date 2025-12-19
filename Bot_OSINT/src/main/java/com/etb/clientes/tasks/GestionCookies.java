package com.etb.clientes.tasks;

import com.etb.clientes.ui.YahooPage ;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;


public class GestionCookies implements  Task {
    public static GestionCookies aceptarTodo(){
        return instrumented(GestionCookies.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor){
        //verificar si edl boton aparece
        if (YahooPage.BOTON_COOKIES.resolveFor(actor).isVisible()){
            actor.attemptsTo(
                    //scrolear para presionar el boton
                    Scroll.to(YahooPage.BOTON_COOKIES),
                    Click.on(YahooPage.BOTON_COOKIES)
            );
        }
    }
}

