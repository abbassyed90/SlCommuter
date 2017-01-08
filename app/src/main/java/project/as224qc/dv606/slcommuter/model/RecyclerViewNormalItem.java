package project.as224qc.dv606.slcommuter.model;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class RecyclerViewNormalItem extends RecyclerViewItem {

    public int position;

    public RecyclerViewNormalItem(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
