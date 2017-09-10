import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.get;
import static spark.Spark.staticFiles;

public class Main {

    public static void main(String[] args) {

        staticFiles.location("/publico");
        Configuration configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(Main.class, "/publico");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

        get("/formulario", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "/formulario.html");
        }, freeMarkerEngine);

        get("/prueba/:lista", (request, response) -> {
            System.out.println(request.params("lista"));
            return "asdasd";
        });
    }
}
