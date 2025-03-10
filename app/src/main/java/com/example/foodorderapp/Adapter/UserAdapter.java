package com.example.foodorderapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodorderapp.Activity.Bean.User;
import com.example.foodorderapp.R;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        super(context, 0, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        }

        User user = userList.get(position);

        TextView tvUserName = convertView.findViewById(R.id.tvUserName);
        TextView tvUserEmail = convertView.findViewById(R.id.tvUserEmail);
        TextView tvUserPhoneNumber = convertView.findViewById(R.id.tvUserPhoneNumber);
        TextView tvUserAddress = convertView.findViewById(R.id.tvUserAddress);
        TextView tvUserRole = convertView.findViewById(R.id.tvUserRole);

        tvUserName.setText(user.getName());
        tvUserEmail.setText(user.getEmail());
        tvUserPhoneNumber.setText(user.getPhoneNumber());
        tvUserAddress.setText(user.getAddress());
        tvUserRole.setText(user.getRole());

        return convertView;
    }
}