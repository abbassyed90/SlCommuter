package project.as224qc.dv606.slcommuter.event;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.model.RecyclerViewItem;
import project.as224qc.dv606.slcommuter.model.TransportationDTO;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.event
 */
public class TimeEvent extends BaseEvent {

    private ArrayList<TransportationDTO> transportationDTOs;
    private ArrayList<RecyclerViewItem> recyclerViewItems;

    public TimeEvent() {
        setSuccess(false);
    }

    public TimeEvent(ArrayList<TransportationDTO> transportationDTOs, ArrayList<RecyclerViewItem> recyclerViewItems) {
        setSuccess(true);
        this.transportationDTOs = transportationDTOs;
        this.recyclerViewItems = recyclerViewItems;
    }

    public ArrayList<TransportationDTO> getTransportationDTOs() {
        return transportationDTOs;
    }

    public ArrayList<RecyclerViewItem> getRecyclerViewItems() {
        return recyclerViewItems;
    }

    public void setRecyclerViewItems(ArrayList<RecyclerViewItem> recyclerViewItems) {
        this.recyclerViewItems = recyclerViewItems;
    }
}
