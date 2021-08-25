package com.example.trabus.Driver_Navigation_fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trabus.R;
import com.example.trabus.adapter.AdapterHistory;
import com.example.trabus.models.DriverHelper;
import com.example.trabus.models.HistoryModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {
   View v;
    private RecyclerView recyclerView;
    private Query query;
    FirebaseUser user;
    DatabaseReference reference,mDb;
    ArrayList<HistoryModel>list=new ArrayList<>();
    AdapterHistory adapterHistory;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_history);

        //query = reference.child()

        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryFragment.this.getActivity()));
        recyclerView.setHasFixedSize(true);
        reference = FirebaseDatabase.getInstance().getReference();
        mDb=FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        adapterHistory=new AdapterHistory(list,HistoryFragment.this.getActivity());
        recyclerView.setAdapter(adapterHistory);
        mDb.child("User").child("Drivers").child("Profile").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    DriverHelper helper = snapshot.getValue(DriverHelper.class);
                    assert helper != null;
                    query = reference.child("User").child("Drivers").child("Maintanance").child(user.getUid()).child(helper.getBusno());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            list.clear();
                            if(snapshot.exists())
                            {
                                for (DataSnapshot ds:snapshot.getChildren()) {
                                    HistoryModel model = ds.getValue(HistoryModel.class);
                                    assert model!=null;
                                    list.add(model);
                                }
                                adapterHistory.notifyDataSetChanged();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        return v;
    }
}