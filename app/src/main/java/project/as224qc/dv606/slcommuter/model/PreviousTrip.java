package project.as224qc.dv606.slcommuter.model;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class PreviousTrip extends Trip {

    private StationDTO origin;
    private StationDTO destination;

    public PreviousTrip(StationDTO origin, StationDTO destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public StationDTO getOrigin() {
        return origin;
    }

    public void setOrigin(StationDTO origin) {
        this.origin = origin;
    }

    public StationDTO getDestination() {
        return destination;
    }

    public void setDestination(StationDTO destination) {
        this.destination = destination;
    }
}
