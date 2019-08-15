package com.goodcompany.gamechangernotes.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaDrm;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.goodcompany.gamechangernotes.Adapters.ListItemsAdapter;
import com.goodcompany.gamechangernotes.Listeners.OnActiveListListener;
import com.goodcompany.gamechangernotes.Listeners.OnListCancelListener;
import com.goodcompany.gamechangernotes.Modals.ItemCreateList;
import com.goodcompany.gamechangernotes.R;
import com.goodcompany.gamechangernotes.Utils.Constants;
import com.goodcompany.gamechangernotes.Utils.ShadowLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEventActivity extends AppCompatActivity implements OnListCancelListener {


    @BindView(R.id.yes_iv)
    ImageView yesIv;
    @BindView(R.id.event_top_tv)
    TextView eventTopTv;
    @BindView(R.id.enter_title_tv)
    TextView enterTitleTv;
    @BindView(R.id.title_et)
    EditText titleEt;
    @BindView(R.id.scheduled_tv)
    TextView scheduledTv;
    @BindView(R.id.schedule_checkbox)
    ImageView scheduleCheckbox;
    @BindView(R.id.from_date_tv)
    TextView fromDateTv;
    @BindView(R.id.alarm_rl)
    RelativeLayout alarmRl;
    @BindView(R.id.list_items_tv)
    TextView listItemsTv;
    @BindView(R.id.items_rv)
    RecyclerView itemsRv;
    @BindView(R.id.set_alarms_tv)
    TextView setAlarmsTv;
    @BindView(R.id.set_alarms_sl)
    ShadowLayout setAlarmsSl;
    @BindView(R.id.add_item_et)
    EditText addItemEt;
    @BindView(R.id.done_iv)
    ImageView doneIv;
    @BindView(R.id.add_item_fl)
    FrameLayout addItemFl;
    @BindView(R.id.add_items_tv)
    TextView addItemsTv;
    @BindView(R.id.add_items_sl)
    ShadowLayout addItemsSl;
    @BindView(R.id.back_to_cal_view)
    TextView backToCalView;

    private ArrayList<String> itemList;
    private LinearLayoutManager linearLayoutManager;
    private ListItemsAdapter listItemsAdapter;
    private String itemName;
    private Context mContext;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private StringBuilder alarmBuilder;
    private StringBuilder monthBuilder;
    private OnActiveListListener onActiveListListener;
    private int clickCount = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);

        itemList = new ArrayList();
        addItemEt.addTextChangedListener(new MyTextWatcher(addItemEt));
        titleEt.addTextChangedListener(new MyTextWatcher(titleEt));
        addFontToViews();
        setupAdapter();
        handleCheckBoxSelection();
    }

    private void addFontToViews() {
        eventTopTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        listItemsTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        enterTitleTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        scheduledTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        addItemEt.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        fromDateTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        setAlarmsTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        addItemsTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        titleEt.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));
        backToCalView.setPaintFlags(backToCalView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        backToCalView.setText("Back To List View");
        backToCalView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-SEMIBOLD.OTF"));

        doneIv.getLayoutParams().height = (int) (0.07 * Constants.sWidth);
        doneIv.getLayoutParams().width = (int) (0.07 * Constants.sWidth);
    }

    private void handleCheckBoxSelection() {
        scheduleCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount++;
                if (clickCount == 0) {
                    scheduleCheckbox.setImageResource(R.drawable.checkbox);
                    alarmRl.setVisibility(View.VISIBLE);
                    setAlarmsSl.setVisibility(View.VISIBLE);
                } else if (clickCount == 1) {
                    scheduleCheckbox.setImageResource(R.drawable.uncheckbox);
                    alarmRl.setVisibility(View.GONE);
                    setAlarmsSl.setVisibility(View.GONE);
                    clickCount = -1;
                }
            }
        });

    }

    public void setupAdapter() {
        mContext  = this;
        listItemsAdapter = new ListItemsAdapter(mContext, itemList, this);
        linearLayoutManager = new LinearLayoutManager(mContext);
        itemsRv.setLayoutManager(linearLayoutManager);
        itemsRv.setAdapter(listItemsAdapter);
    }

    @OnClick({R.id.from_date_tv, R.id.add_items_sl, R.id.back_to_cal_view, R.id.add_item_et, R.id.done_iv, R.id.yes_iv, R.id.set_alarms_sl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.from_date_tv:
                openFromPicker();
                break;
            case R.id.yes_iv:
                if (!validateTitle()) {
                    return;
                }
                submitEvent();
                break;
            case R.id.add_items_sl:
                addItemFl.setVisibility(View.VISIBLE);
                doneIv.setVisibility(View.VISIBLE);
                break;
            case R.id.back_to_cal_view:
                getFragmentManager().popBackStack();
                break;
            case R.id.add_item_et:
                break;
            case R.id.done_iv:
                if (!validateItems()) {
                    return;
                }
                itemList.add(itemName);
                listItemsAdapter.notifyDataSetChanged();
                addItemEt.setText("");
                addItemFl.setVisibility(View.GONE);
                break;
            case R.id.set_alarms_sl:
                openFromPicker();
                break;
        }
    }

    private void submitEvent() {
        if (!validateTitle()) {
            return;
        }
    }

    private List<ItemCreateList> getAllTheListItemsFromList() {
        List<ItemCreateList> itemList = new ArrayList<>();
        if (this.itemList != null && this.itemList.size() > 0) {
            for (int i = 0; i < this.itemList.size(); i++) {
                ItemCreateList itemCreateList = new ItemCreateList();
                itemCreateList.setItemName(this.itemList.get(i));
                itemList.add(itemCreateList);
            }
        }

        return itemList;
    }

    @Override
    public void onListCancelledClicked(final int position,  View view, String id) {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Are you sure you want to delete the Item")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listItemsAdapter.deleteItem(position);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    public void onEventCancelledClicked(int position,  View view, int eventId, String from) {

    }

    @Override
    public void onEventClicked(int position) {

    }


    private void openFromPicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.e("Check", "onDateSet: " + year + "--monthfyr" + monthOfYear + "--dayfmnth" + dayOfMonth);
                        monthOfYear = monthOfYear + 1;
                        alarmBuilder = new StringBuilder();
                        monthBuilder = new StringBuilder();
                        alarmBuilder.append(year).append("-");
                        if (monthOfYear + 1 < 10) {
                            alarmBuilder.append("0").append(monthOfYear).append("-");
                        } else {
                            alarmBuilder.append(monthOfYear).append("-");
                        }
                        if (dayOfMonth < 10) {
                            alarmBuilder.append("0").append(dayOfMonth).append(" ");
                            monthBuilder.append("0").append(dayOfMonth).append("/");
                        } else {
                            alarmBuilder.append(dayOfMonth).append(" ");
                            monthBuilder.append(dayOfMonth).append("/");
                        }
                        if (monthOfYear + 1 < 10) {
                            monthBuilder.append("0").append(monthOfYear).append("/");
                        } else {
                            if (monthOfYear == 9) {
                                monthBuilder.append("0").append(monthOfYear).append("/");
                            }
                            monthBuilder.append(monthOfYear).append("/");
                        }
                        monthBuilder.append(year).append(" ");
//                            .setText(monthBuilder);
                        openFromTimePicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    void openFromTimePicker() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        formatTimeForDisplay(hourOfDay, minute);
                        formatEventTimeForBackend(hourOfDay, minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void formatEventTimeForBackend(int hourOfDay, int minute) {
        if (hourOfDay < 10) {
            if (hourOfDay == 0) {
                alarmBuilder.append("12").append(":");
            } else {
                alarmBuilder.append("0").append(hourOfDay).append(":");
            }

        } else {
            alarmBuilder.append(hourOfDay).append(":");
        }
        if (minute == 0) {
            alarmBuilder.append("0").append(minute).append(":").append("00");
        } else {
            alarmBuilder.append(minute).append(":").append("00");
        }
        Log.e("Check", "formatTimeForDisplay:" + alarmBuilder);

    }

    private void formatTimeForDisplay(int hourOfDay, int minute) {
        int mHourOfDay = hourOfDay;
        StringBuilder timeBuilder;
        String format;
        timeBuilder = new StringBuilder();
        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = "AM";

        } else if (hourOfDay == 12) {
            format = "PM";

        } else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = "PM";

        } else {
            format = "AM";
        }
        if (hourOfDay < 10) {
            monthBuilder.append("0").append(hourOfDay).append(":");
        } else {
            monthBuilder.append(hourOfDay).append(":");
        }
        if (minute == 0 || minute < 10) {
            monthBuilder.append(0).append(minute).append(format);
        } else {
            monthBuilder.append(minute).append(format);
        }
        fromDateTv.setText(monthBuilder);
    }

    @Override
    public void onListClicked(int position) {

    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.title_et:
                    validateTitle();
                    break;
                case R.id.add_item_et:
                    validateItems();
                    break;
            }
        }

    }

    private boolean validateItems() {
        if (addItemEt.getText().toString().trim().isEmpty()) {
            addItemEt.setError("Enter Item Name");
            return false;
        } else {
            itemName = addItemEt.getText().toString();
//            createEventModal.setEventNotes(notesEt.getText().toString());
        }
        return true;
    }

    private boolean validateTitle() {
        if (titleEt.getText().toString().trim().isEmpty()) {
            titleEt.setError(getString(R.string.err_pass_title));
            return false;
        } else {
        }
        return true;
    }
}
