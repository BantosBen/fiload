package com.banit.fiload.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banit.fiload.R;
import com.banit.fiload.model.Folder;
import com.banit.fiload.model.MyColor;
import com.banit.fiload.utils.ColorsUtils;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Random;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private OnFolderClickListener onFolderClickListener;
    private final ArrayList<Folder> folders;

    public FolderAdapter(ArrayList<Folder> _folders) {
        this.folders = _folders;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView icFolder;
        private TextView txtName;
        private TextView txtDate;
        private ImageView imgMore;
        private MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icFolder = itemView.findViewById(R.id.ic_folder);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDate = itemView.findViewById(R.id.txt_date);
            imgMore = itemView.findViewById(R.id.img_more);
            cardView = itemView.findViewById(R.id.card_view);
        }

        void bind(Folder folder) {
            txtName.setText(folder.getName());
            txtDate.setText(folder.getDate());
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onFolderClickListener.onFolderClick(folder.getName());
                }
            });
            applyColors();
        }

        private void applyColors() {
            MyColor myColor = getRandomColor(); // Get a random myColor from your list of Colors

            // Apply colors to the views in the ViewHolder
            icFolder.setColorFilter(Color.parseColor(myColor.getMain()));
            imgMore.setColorFilter(Color.parseColor(myColor.getMain()));
            txtName.setTextColor(Color.parseColor(myColor.getMain()));
            txtDate.setTextColor(Color.parseColor(myColor.getMain()));
            cardView.setCardBackgroundColor(Color.parseColor(myColor.getLight()));
        }

        private MyColor getRandomColor() {
            ArrayList<MyColor> colorsList = ColorsUtils.getColors();
            Random random = new Random();
            int index = random.nextInt(colorsList.size());
            return colorsList.get(index);
        }
    }

    @NonNull
    @Override
    public FolderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_item, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FolderAdapter.ViewHolder holder, int position) {
        holder.bind(folders.get(position));
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public void addOnFolderClickListener(OnFolderClickListener listener) {
        onFolderClickListener = listener;
    }

    public interface OnFolderClickListener {
        void onFolderClick(String folderName);
    }
}
