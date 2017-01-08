package project.as224qc.dv606.slcommuter.event;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.model.StationDTO;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.event
 */
public class StationEvent extends BaseEvent {

    private ArrayList<StationDTO> stationDTOs;

    public StationEvent() {
        setSuccess(false);
    }

    public StationEvent(ArrayList<StationDTO> stationDTOs) {
        setSuccess(true);
        this.stationDTOs = stationDTOs;
    }

    public ArrayList<StationDTO> getStations() {
        return stationDTOs;
    }
}
