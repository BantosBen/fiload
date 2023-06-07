package com.banit.fiload.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banit.fiload.R;
import com.banit.fiload.model.MyFile;
import com.banit.fiload.utils.ActivityUtils;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    private final ArrayList<MyFile> files;

    public FileAdapter() {
        this.files = new ArrayList<>();
    }

    public void addData(ArrayList<MyFile> _files) {
        this.files.clear();
        this.files.addAll(_files);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.ViewHolder holder, int position) {
        holder.bind(files.get(position));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFile;
        private TextView txtName;
        private TextView txtDate;
        private TextView txtSize;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFile = itemView.findViewById(R.id.img_file);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtSize = itemView.findViewById(R.id.txt_size);
        }

        @SuppressLint("SetTextI18n")
        void bind(MyFile file) {
            imgFile.setImageResource(getFileIconResource(file.getType()));
            txtName.setText(file.getName());
            txtSize.setText(ActivityUtils.formatNumberWithCommas(file.getSize()) + "Kb");
            txtDate.setText(ActivityUtils.formatDate(file.getDate()));
        }

        private int getFileIconResource(String fileType) {
            switch (fileType) {
                case "word":
                    return R.drawable.ic_word;
                case "pdf":
                    return R.drawable.ic_pdf;
                case "image":
                    return R.drawable.ic_image;
                case "excel":
                    return R.drawable.ic_excel;
                default:
                    return R.drawable.ic_other_file;
            }
        }
    }
}
