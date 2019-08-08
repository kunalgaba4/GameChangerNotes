package com.goodcompany.gamechangernotes.Listeners;

/**
 * Created by iapp on 8/8/18.
 */

public interface OnListItemClickListeners {

    void onListItemDelted(String id, int adapterPos);
    void onListItemEdited(String id, int adapterPos, String item_name);
    void onListAllChecked(boolean isAllChecked);
}
