package com.example.foodorderapp.Activity.Admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderapp.Activity.Bean.User;
import com.example.foodorderapp.Adapter.UserAdapter;
import com.example.foodorderapp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminUserManagementActivity extends AppCompatActivity {
    private ListView lvUsers;
    private Button btnAddUser;
    private List<User> userList;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_management);

        lvUsers = findViewById(R.id.lvUsers);
        btnAddUser = findViewById(R.id.btnAddUser);
        userList = new ArrayList<>();

        // Fetch users from Firestore
        fetchUsersFromFirestore();

        userAdapter = new UserAdapter(this, userList);
        lvUsers.setAdapter(userAdapter);

        btnAddUser.setOnClickListener(v -> {
            showUserDialog(null);
        });

        lvUsers.setOnItemClickListener((parent, view, position, id) -> {
            User user = userList.get(position);
            showUserDialog(user);
        });
    }

    private void fetchUsersFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            userList.add(user);
                        }
                        userAdapter.notifyDataSetChanged();
                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                });
    }

    private void showUserDialog(@Nullable User user) {
        // Implement user dialog to add or update user
    }

    private void deleteUser(User user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getId()).delete()
                .addOnSuccessListener(aVoid -> {
                    userList.remove(user);
                    userAdapter.notifyDataSetChanged();
                    Toast.makeText(AdminUserManagementActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error deleting document", e));
    }
}