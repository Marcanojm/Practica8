package services;

import entidades.Persona;

public class PersonaServices extends GestionDb<Persona> {

    private static PersonaServices instancia;

    private PersonaServices() {super(Persona.class);}

    public static PersonaServices getInstancia() {
        if(instancia==null){
            instancia = new PersonaServices();
        }
        return instancia;
    }
}
