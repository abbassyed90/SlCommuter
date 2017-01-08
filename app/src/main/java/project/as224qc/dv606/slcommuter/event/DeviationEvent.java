package project.as224qc.dv606.slcommuter.event;


import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.model.DeviationDTO;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.event
 */
public class DeviationEvent extends BaseEvent {

    private ArrayList<DeviationDTO> deviationDTOs;

    public DeviationEvent() {
        setSuccess(false);
    }

    public DeviationEvent(ArrayList<DeviationDTO> deviationDTOs) {
        setSuccess(true);
        this.deviationDTOs = deviationDTOs;
    }

    public ArrayList<DeviationDTO> getDeviations() {
        return deviationDTOs;
    }
}
