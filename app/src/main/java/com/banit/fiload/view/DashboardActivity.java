package com.banit.fiload.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.banit.fiload.R;
import com.banit.fiload.adapter.FolderAdapter;
import com.banit.fiload.databinding.ActivityDashboardBinding;
import com.banit.fiload.model.Folder;
import com.banit.fiload.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private Context mContext;
    private RecyclerView.LayoutManager layoutManager;
    private boolean isGridMode = true;
    private FolderAdapter folderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        mContext = this;
        setContentView(binding.getRoot());

        binding.icGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isGridMode) {
                    switchToGridLayoutManager();
                    isGridMode = true;
                }
            }
        });
        binding.icListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGridMode) {
                    switchToLinearLayoutManager();
                    isGridMode = false;
                }
            }
        });

        binding.fabUpload.setOnClickListener(view -> ActivityUtils.loadActivity(DashboardActivity.this, UploadActivity.class, null));

        String[] folderNames = getResources().getStringArray(R.array.arr_folders);

        ArrayList<Folder> folderList = new ArrayList<>();
        for (String folderName : folderNames) {
            Folder folder = new Folder(folderName, "07 June 2023"); // Set the date value accordingly
            folderList.add(folder);
        }

        folderAdapter = new FolderAdapter(folderList);
        folderAdapter.addOnFolderClickListener(folderName -> {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("title", folderName);
            ActivityUtils.loadActivity(DashboardActivity.this, ViewFileActivity.class, data);
        });
        binding.recylerView.setAdapter(folderAdapter);
    }

    // Switch to LinearLayoutManager (List Mode)
    private void switchToLinearLayoutManager() {
        layoutManager = new LinearLayoutManager(this);
        binding.recylerView.setLayoutManager(layoutManager);
        folderAdapter.notifyDataSetChanged();
    }

    // Switch to GridLayoutManager (Grid Mode)
    private void switchToGridLayoutManager() {
        int spanCount = 2; // Set the desired number of grid columns
        layoutManager = new GridLayoutManager(this, spanCount);
        binding.recylerView.setLayoutManager(layoutManager);
        folderAdapter.notifyDataSetChanged();
    }

}