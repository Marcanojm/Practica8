package ubicacion;


import org.codehaus.jackson.annotate.JsonIgnore;

public class Result {

    private String formatted_address;

    @JsonIgnore
    private Object geometry;

    @JsonIgnore
    private Object address_components;

    @JsonIgnore
    private Object types;

    @JsonIgnore
    private Object place_id;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public Object getGeometry() {
        return geometry;
    }

    public void setGeometry(Object geometry) {
        this.geometry = geometry;
    }

    public Object getAddress_components() {
        return address_components;
    }

    public void setAddress_components(Object address_components) {
        this.address_components = address_components;
    }

    public Object getTypes() {
        return types;
    }

    public void setTypes(Object types) {
        this.types = types;
    }

    public Object getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Object place_id) {
        this.place_id = place_id;
    }
}
