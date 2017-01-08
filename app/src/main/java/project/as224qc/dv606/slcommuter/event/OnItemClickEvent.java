package project.as224qc.dv606.slcommuter.event;

import android.view.View;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.event
 */
public class OnItemClickEvent {

    private View view;
    private int position;

    public OnItemClickEvent(View view, int position) {
        this.view = view;
        this.position = position;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
