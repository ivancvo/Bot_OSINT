package com.etb.clientes.questions;

import com.etb.clientes.models.DatosCliente;
import com.etb.clientes.ui.YahooPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.questions.Attribute;


public class ExtraerDatosYahoo implements Question<DatosCliente> {

    private final DatosCliente cliente;

    public ExtraerDatosYahoo(DatosCliente cliente){
        this.cliente = cliente;
    }

    public static ExtraerDatosYahoo para(DatosCliente cliente){
        return new ExtraerDatosYahoo(cliente);
    }

    @Override
    public DatosCliente answeredBy(Actor actor) {
        // Verificamos si apareció el título
        if (YahooPage.TITULO_RESULTADO.resolveAllFor(actor).isEmpty()){
            cliente.setTituloProfesional("NO ENCONTRADO");
            return cliente;
        }

        // Extraemos datos crudos
        String titulo = Text.of(YahooPage.TITULO_RESULTADO).answeredBy(actor);
        String url = Attribute.of(YahooPage.TITULO_RESULTADO).named("href").answeredBy(actor);

        String resumen = "";
        if (YahooPage.RESUMEN_RESULTADO.resolveAllFor(actor).size() > 0){
            resumen = Text.of(YahooPage.RESUMEN_RESULTADO).answeredBy(actor);
        }


        //  Limpieza del Título
        String tituloLimpio = titulo.replace(" - LinkedIn", "")
                .replace(" | LinkedIn", "")
                .replace("...", "")
                .replaceAll("^.*?linkedin\\.com.*?>", "")
                .trim();

        // Lista negra de textos que NO sirven
        boolean esResumenBasura = resumen.contains("Mira el perfil") ||
                                  resumen.contains("1.000 millones") ||
                                  resumen.contains("millones de miembros") ||
                                  resumen.contains("Regístrate");

        if (esResumenBasura) {
            //  Si el resumen es malo, sacamos el cargo del Título.

            if (tituloLimpio.contains("-")) {
                String[] partes = tituloLimpio.split("-", 2); // Divide en 2 partes
                if (partes.length > 1) {
                    // Tomamos lo que está a la derecha del guion (el cargo)
                    resumen = "Cargo Detectado: " + partes[1].trim();
                } else {
                    resumen = tituloLimpio; // Si falla, ponemos el título completo
                }
            } else {
                // Si no hay guiones y el resumen es malo, dejamos el título como resumen
                resumen = tituloLimpio;
            }
        }

        // C. Limpieza URL
        if (url.contains("?")) {
            url = url.split("\\?")[0];
        }

        cliente.setTituloProfesional(tituloLimpio);
        cliente.setUrlPerfil(url);
        cliente.setResumen(resumen);

        return cliente;
    }
}


