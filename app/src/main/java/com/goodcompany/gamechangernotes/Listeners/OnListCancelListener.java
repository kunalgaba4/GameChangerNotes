package com.goodcompany.gamechangernotes.Listeners;

import android.view.View;

public interface OnListCancelListener {

    void onListCancelledClicked(int position, View view, String id);
    void onEventCancelledClicked(int position, View view, int eventId, String from);
    void onEventClicked(int position);
    void onListClicked(int position);
}
