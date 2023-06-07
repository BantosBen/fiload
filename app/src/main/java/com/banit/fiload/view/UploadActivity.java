package com.banit.fiload.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.banit.fiload.databinding.ActivityUploadBinding;
import com.banit.fiload.utils.DialogUtils;
import com.banit.fiload.utils.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.onimur.handlepathoz.HandlePathOz;
import br.com.onimur.handlepathoz.HandlePathOzListener;
import br.com.onimur.handlepathoz.model.PathOz;

public class UploadActivity extends AppCompatActivity {
    private ActivityUploadBinding binding;
    private Context mContext;

    private ActivityResultLauncher<String> filePickerLauncher;
    private HandlePathOz handlePathOz;
    private String filePath, folderName;

    private HandlePathOzListener.SingleUri handlePathOzListener = new HandlePathOzListener.SingleUri() {
        @Override
        public void onRequestHandlePathOz(PathOz pathOz, Throwable tr) {
            filePath = pathOz.getPath();
            binding.layoutViewSelectedDoc.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadBinding.inflate(getLayoutInflater());
        mContext = this;
        setContentView(binding.getRoot());
        handlePathOz = new HandlePathOz(this, handlePathOzListener);

        binding.txtDelete.setOnClickListener(view -> {
            binding.layoutViewSelectedDoc.setVisibility(View.GONE);
            filePath = null;
        });

        binding.btnSelectFile.setOnClickListener(view -> {
            // Launch the file chooser
            launchFilePicker();
        });

        binding.btnUpload.setOnClickListener(view -> {
            if (filePath != null) {
                folderName = binding.spinnerFolder.getSelectedItem().toString();
                uploadFile();
            } else {
                DialogUtils.toast("Select file to upload", mContext);
            }
        });

        filePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        handlePathOz.getRealPath(result);
                        binding.txtNameFile.setText(FileUtils.getFileNameFromUri(result, getContentResolver()));
                    } else {
                        DialogUtils.toast("Failed to get file path", mContext);
                    }
                });

    }

    private void launchFilePicker() {
        filePickerLauncher.launch("*/*");
    }

    private void uploadFile() {
        String url = "http://banit.co.ke/fiload/upload.php"; // Replace with your server's endpoint
        binding.progressBar.setVisibility(View.VISIBLE);

        try {
            File file = new File(filePath);
            String fileContent = getStringFromFile(file);
            String fileSize = String.valueOf(FileUtils.getFileSize(file));
            String fileType = FileUtils.getFileType(file);

            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
                binding.progressBar.setVisibility(View.GONE);
                try {
                    JSONObject responseObject = new JSONObject(response);
                    if (!responseObject.getBoolean("error")){
                        DialogUtils.successDialog("SUCCESS", responseObject.getString("message"), mContext, this::finish);
                    }else{
                        DialogUtils.errorDialog("Oops! Error", "Failed to upload the file. Internal error occurred", mContext);
                    }
                } catch (JSONException e) {
                    DialogUtils.errorDialog("Oops! Error", "Something went wrong kindly try again", mContext);
                }
            },
                    error -> {
                        error.printStackTrace();
                        binding.progressBar.setVisibility(View.GONE);
                        // Handle the error
                        DialogUtils.errorDialog("Oops! Error", "Failed to upload the file. Kindly check your internet and try again", mContext);
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("folder", folderName);
                    data.put("file", fileContent);
                    data.put("file_size", fileSize);
                    data.put("file_type", fileType);
                    data.put("file_name", binding.txtNameFile.getText().toString());
                    return data;
                }
            };

            queue.add(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getStringFromFile(File file) {
        byte[] bytes = new byte[(int) file.length()];

        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(bytes);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String(bytes);
    }
}