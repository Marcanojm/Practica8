import entidades.Persona;
import freemarker.template.Configuration;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import services.PersonaServices;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import org.h2.tools.Server;
import ubicacion.AddressConverter;
import ubicacion.GoogleResponse;
import ubicacion.Result;

import java.sql.SQLException;
import java.util.*;
import static spark.Spark.get;
import static spark.Spark.staticFiles;

public class Main {

    public static void main(String[] args) {

        abrirPuerto();
        staticFiles.location("/publico");
        Configuration configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(Main.class, "/publico");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

        get("/formulario", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "/formulario.html");
        }, freeMarkerEngine);

        get("/enviar/:lista", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();

            List<Persona> list = mapper.readValue(request.params("lista"), TypeFactory.defaultInstance().constructCollectionType(List.class, Persona.class));
            for (Persona p:list) {
                Persona pTemp = new Persona(p.getNombre(), p.getSector(), p.getNivelEscolar(), p.getLatitud(), p.getLongitud());
                PersonaServices.getInstancia().crear(pTemp);
            }
            return "";
        });

        get("/listaPersonas", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            attributes.put("listaPersonas", PersonaServices.getInstancia().findAll());
            return new ModelAndView(attributes, "/listaPersonas.html");
        }, freeMarkerEngine);

        get("/listaUbicaciones/:latitud/:longitud", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            List<String> ubicaciones = new ArrayList<>();

            GoogleResponse res = new AddressConverter().convertFromLatLong(request.params("latitud") + "," + request.params("longitud"));
            if(res.getStatus().equals("OK"))
            {
                for(Result result : res.getResults())
                {
                    ubicaciones.add(result.getFormatted_address());
                }
            }
            else
            {
                System.out.println(res.getStatus());
            }

            attributes.put("listaUbicaciones", ubicaciones);
            return new ModelAndView(attributes, "/listaUbicaciones.html");
        }, freeMarkerEngine);
    }

    public static void abrirPuerto() {

        try {
            Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
