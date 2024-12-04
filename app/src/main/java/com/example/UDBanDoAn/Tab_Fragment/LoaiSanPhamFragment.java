package com.example.UDBanDoAn.Tab_Fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.UDBanDoAn.Adapter.ThemLoaiSanPhamAdapter;
import com.example.UDBanDoAn.DAO.LoaiSanPhamDAO;
import com.example.UDBanDoAn.MODEL.LoaiSanPham;
import com.example.UDBanDoAn.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;


import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class LoaiSanPhamFragment extends Fragment {
    LoaiSanPhamDAO sanPhamDAO;
    FloatingActionButton add_loai;
    RecyclerView list_sp;
    ThemLoaiSanPhamAdapter phamAdapter;
    ImageView anh_loai;
    String link;
    ActivityResultLauncher<Intent> activityResultLauncher;
    List<LoaiSanPham> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_loai_san_pham, container, false);
        add_loai = view.findViewById(R.id.add_loai_sp);
        list_sp = view.findViewById(R.id.list_loai_sp);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        list_sp.setLayoutManager(manager);
        sanPhamDAO = new LoaiSanPhamDAO(getContext());
        list = sanPhamDAO.getDSLoaiSanPham();
        phamAdapter = new ThemLoaiSanPhamAdapter(getContext(), list);
        list_sp.setAdapter(phamAdapter);

        add_loai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themLoaiSanPham();
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);
                        if (bitmap != null) {
                            anh_loai.setImageBitmap(bitmap);
                            anh_loai.setVisibility(View.VISIBLE);
                        }
                        link = selectedImageUri.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }


    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(),null);
        return Uri.parse(path);
    }

    private void themLoaiSanPham() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loai_sp, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        ImageView add_anh = view.findViewById(R.id.add_anh);
        anh_loai = view.findViewById(R.id.anh_loai_sp);
        TextInputEditText edit_ten = view.findViewById(R.id.edit_ten_loai_sp);
        Button them = view.findViewById(R.id.btn_loai_sp);

        add_anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openImgea();
                openImageGallery();
            }
        });



        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSanPham sanPham = new LoaiSanPham();
                String ten = edit_ten.getText().toString();
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                String text = simpleDateFormat.format(calendar.getTime());
                sanPham.setNameLoaisanpham(ten);
                sanPham.setCreateLoaisanpham(text);
                sanPham.setImgLoaisanpham(link);
                sanPham.setUpdatedLoaisanpham(text);
                sanPham.setIdCuahang(0);

                if (sanPhamDAO.themLoaiSanPham(sanPham)) {
                    Toast.makeText(getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    list.clear();
                    list.addAll(sanPhamDAO.getDSLoaiSanPham());
                    phamAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Them that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // hoi ve phan nay

    private void openImgea() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) !=null){
            activityResultLauncher.launch(intent);
        }else {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }
    private void openImageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            activityResultLauncher.launch(intent);
        } else {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

}