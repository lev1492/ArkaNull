package com.example.android.arkanull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAORecord {
    private DatabaseReference databaseReference;
public DAORecord(){
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    databaseReference = db.getReference(Record.class.getSimpleName());
}

    public Task<Void> add (Record record){
        return databaseReference.push().setValue(record);
    }

    public Task<Void> update(String key , HashMap<String , Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }


    
}
