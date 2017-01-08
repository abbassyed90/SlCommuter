package project.as224qc.dv606.slcommuter.model;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.model
 */
public class RecyclerViewHeaderItem extends RecyclerViewItem {

    private String headerTitle;

    public RecyclerViewHeaderItem(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }
}
