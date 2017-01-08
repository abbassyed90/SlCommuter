package project.as224qc.dv606.slcommuter;

import com.squareup.otto.Bus;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public class EventBus {

    private static final Bus bus = new Bus();

    private EventBus() {
    }

    public static Bus getInstance() {
        return bus;
    }

}
