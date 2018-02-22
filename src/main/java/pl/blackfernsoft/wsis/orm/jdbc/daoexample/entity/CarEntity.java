package pl.blackfernsoft.wsis.orm.jdbc.daoexample.entity;

public class CarEntity implements Entity {

    private Long id;
    private String name;
    private String plates;
    private Boolean available = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlates() {
        return plates;
    }

    public void setPlates(String plates) {
        this.plates = plates;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "CarEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", plates='" + plates + '\'' +
                ", available=" + available +
                '}';
    }
}
