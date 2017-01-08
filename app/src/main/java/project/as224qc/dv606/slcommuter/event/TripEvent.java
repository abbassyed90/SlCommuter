package project.as224qc.dv606.slcommuter.event;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.model.TripDTO;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.event
 */
public class TripEvent extends BaseEvent {

    private ArrayList<TripDTO> tripDTOs;

    public TripEvent() {
        setSuccess(false);
    }

    public TripEvent(ArrayList<TripDTO> tripDTOs) {
        setSuccess(true);
        this.tripDTOs = tripDTOs;
    }

    public ArrayList<TripDTO> getTrips() {
        return tripDTOs;
    }
}
