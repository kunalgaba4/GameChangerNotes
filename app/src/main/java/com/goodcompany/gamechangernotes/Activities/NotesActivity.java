package com.goodcompany.gamechangernotes.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodcompany.gamechangernotes.Adapters.ListDescriptionAdapter;
import com.goodcompany.gamechangernotes.Adapters.NotesListAdapter;
import com.goodcompany.gamechangernotes.Database.DBNote;
import com.goodcompany.gamechangernotes.Listeners.OnListItemClickListeners;
import com.goodcompany.gamechangernotes.Modals.Note;
import com.goodcompany.gamechangernotes.R;
import com.goodcompany.gamechangernotes.Singelton.SubjectSingleton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesActivity extends AppCompatActivity implements OnListItemClickListeners {
    ArrayList<Note> savedNoteArrayList = new ArrayList<>();
    @BindView(R.id.dashboard_tv)
    TextView dashboardTv;
    @BindView(R.id.settings_iv)
    ImageView settingsIv;
    @BindView(R.id.dash_parent_rl)
    RelativeLayout dashParentRl;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.categories_rv)
    RecyclerView categoriesRv;
    @BindView(R.id.add_event_iv)
    ImageView addEventIv;
    @BindView(R.id.rel_ll)
    RelativeLayout relLl;
    @BindView(R.id.dash_parent_fl)
    FrameLayout dashParentFl;

    Context mContext;
    String subjectName;
    DBNote dbNote = new DBNote(this);
    LinearLayoutManager linearLayoutManager;
    private NotesListAdapter notesListAdapter;
    String[] notes = {"I love Maths", "I love English", "I love Science", "I love Coding"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        mContext = NotesActivity.this;
        if (getIntent().getExtras() != null){
            subjectName = getIntent().getExtras().get("SubjectName").toString();
        }

        if (subjectName != null){
            SubjectSingleton.getInstance().setSubjectName(subjectName);
            savedNoteArrayList = dbNote.getNoteOfSubject(this, subjectName);
        }else{
            subjectName = SubjectSingleton.getInstance().getSubjectName();
            savedNoteArrayList = dbNote.getNoteOfSubject(this, subjectName);
        }
        setupDescAdapter();
    }


    private void setupDescAdapter() {
        notesListAdapter = new NotesListAdapter(mContext, savedNoteArrayList, this);
        linearLayoutManager = new LinearLayoutManager(mContext);
        categoriesRv.setLayoutManager(linearLayoutManager);
        categoriesRv.setAdapter(notesListAdapter);
    }

    @OnClick({R.id.settings_iv, R.id.etSearch, R.id.add_event_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.settings_iv:
                break;
            case R.id.etSearch:
                break;
            case R.id.add_event_iv:
                Intent intentToNote = new Intent(getApplicationContext(), AddNoteActivity.class);
                Bundle b = new Bundle();
                b.putString("subjectName", subjectName);
                intentToNote.putExtras(b);
                startActivity(intentToNote);
                break;
        }
    }

    @Override
    public void onListItemDelted(String id, int adapterPos) {

    }

    @Override
    public void onListItemEdited(String id, int adapterPos, String item_name) {

    }

    @Override
    public void onListAllChecked(boolean isAllChecked) {

    }

    @Override
    public void onItemClicked(String id, int adapterPos) {

    }
}
