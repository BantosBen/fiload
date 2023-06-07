package com.banit.fiload.view;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.banit.fiload.adapter.FileAdapter;
import com.banit.fiload.databinding.ActivityViewFileBinding;
import com.banit.fiload.model.MyFile;
import com.banit.fiload.utils.DialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewFileActivity extends AppCompatActivity {

    private ActivityViewFileBinding binding;
    private Context mContext;

    private FileAdapter fileAdapter;
    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewFileBinding.inflate(getLayoutInflater());
        mContext = this;
        setContentView(binding.getRoot());

        fileAdapter = new FileAdapter();

        binding.recylerView.setAdapter(fileAdapter);
        title = getIntent().getStringExtra("title");
        binding.toolbar.setTitle(title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFiles();
    }

    private void loadFiles() {
        String url = "https://banit.co.ke/fiload/view.php?folder=" + title; // Replace with your API URL

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    binding.progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");
                        String message = jsonObject.getString("message");

                        if (!error) {
                            JSONArray dataArray = jsonObject.getJSONArray("data");

                            ArrayList<MyFile> dataList = new ArrayList<>();

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataObj = dataArray.getJSONObject(i);
                                int id = dataObj.getInt("id");
                                String name = dataObj.getString("name");
                                String folder = dataObj.getString("folder");
                                String type = dataObj.getString("type");
                                int size = dataObj.getInt("size");
                                String date = dataObj.getString("date");

                                MyFile data = new MyFile(String.valueOf(id), name, folder, type, String.valueOf(size), date);
                                dataList.add(data);
                            }

                            if (dataList.isEmpty())
                                binding.txtMessage.setText("No files here");
                            else
                                fileAdapter.addData(dataList);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        binding.txtMessage.setText("Oops! Something went wrong. Please try again");
                    }
                },
                error -> {
                    binding.progressBar.setVisibility(View.GONE);
                    // Handle error case
                    binding.txtMessage.setText("Oops! Check your internet and try again");
                });

        queue.add(request);
    }

    private void downloadFile(String fileName, String fileUrl) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
        request.setTitle(fileName);
        request.setDescription("Downloading");

        String downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = downloadManager.enqueue(request);

        DialogUtils.toast("Download started", mContext);
    }
}