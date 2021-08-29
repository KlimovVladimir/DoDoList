package com.example.dodolist.ui.schedule;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dodolist.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;


public class SelectGroup extends AppCompatActivity {

    private ListView listdata;
    private AutoCompleteTextView search;
    DatabaseReference mref;


    FirebaseStorage fbStorage;
    StorageReference sReference;

    StorageReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selectgroup);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Загрузить расписание");

        mref = FirebaseDatabase.getInstance().getReference("groups");
        listdata = (ListView) findViewById(R.id.listData);
        search = (AutoCompleteTextView) findViewById(R.id.search);

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                populateSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        mref.addListenerForSingleValueEvent(event);


    }

    private void populateSearch(DataSnapshot snapshot) {
        ArrayList<String> groups = new ArrayList<>();
        if(snapshot.exists()) {
            for (DataSnapshot ds:snapshot.getChildren()) {
                String group = ds.child("GROUP").getValue(String.class);
                groups.add(group);
            }

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, groups);
            search.setAdapter(adapter);

            String group = search.getText().toString();
            searchGroup(group);

            search.setOnItemClickListener( new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView , View view , int position ,long arg3)
                {
                    String group = search.getText().toString();
                    searchGroup(group);
                }
            });
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Группа не найдена", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    Group[] group1 = new Group[100];
    private void searchGroup(String group) {

        Query query = mref.orderByChild("GROUP").equalTo(group);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    ArrayList<String> listgroups = new ArrayList<>();
                    int i = 0;
                    for (DataSnapshot ds:snapshot.getChildren()) {
                        group1[i] = new Group(ds.child("GROUP").getValue(String.class),ds.child("FAC").getValue(String.class),ds.child("VUZ").getValue(String.class),ds.child("DIR").getValue(String.class));
                        listgroups.add("ВУЗ: " + group1[i].getVUZ() + "\n" + "Факультет: " + group1[i].getFAC() + "\n" + "Группа: " + group1[i].getGroupName());
                        i++;
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listgroups);
                    listdata.setAdapter(adapter);

                    listdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            download(group1[position].getDIR() + "/DodoDB", "DodoDB", group1[position].getDIR() + "/DodoDB-shm", "DodoDB-shm", group1[position].getDIR() + "/DodoDB-wal", "DodoDB-wal");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    class Group
    {
        public Group(String group, String fac, String vuz, String dir) {
            this.groupName = group;
            this.FAC = fac;
            this.VUZ = vuz;
            this.DIR = dir;
        }

        public Group() {}

        public String getDIR() {
            return DIR;
        }

        public String getFAC() {
            return FAC;
        }

        public String getVUZ() {
            return VUZ;
        }

        public String getGroupName() {
            return groupName;
        }

        public String groupName;
        public String FAC;
        public String VUZ;
        public String DIR;
    }



    public void download(String fFilewithDir, String fFile, String sFilewithDir, String sFile, String tFilewithDir, String tFile) {
        sReference = fbStorage.getInstance().getReference();
        ref = sReference.child(fFilewithDir);

        Toast toast = Toast.makeText(getApplicationContext(),
                "Дождитесь окончания загрузки", Toast.LENGTH_SHORT);
        toast.show();

        // ref = sReference.child("AO71").child(fFile);


        File fdbFile = new File("/data/data/com.example.dodolist/databases/", fFile);

        ref.getFile(fdbFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ошибка загрузки", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        ref = sReference.child(sFilewithDir);
        File sdbFile = new File("/data/data/com.example.dodolist/databases/", sFile);

        ref.getFile(sdbFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ошибка загрузки", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        ref = sReference.child(tFilewithDir);
        File tdbFile = new File("/data/data/com.example.dodolist/databases/", tFile);

        ref.getFile(tdbFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                triggerRebirth(getContext());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ошибка загрузки", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    private static void triggerRebirth(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }

    private static Context getContext() {
        return SelectGroup.getContext();
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
