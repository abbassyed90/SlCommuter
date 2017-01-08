package project.as224qc.dv606.slcommuter.model;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class Subscription {

    private String name;
    private String type;

    public Subscription() {

    }

    public Subscription(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
