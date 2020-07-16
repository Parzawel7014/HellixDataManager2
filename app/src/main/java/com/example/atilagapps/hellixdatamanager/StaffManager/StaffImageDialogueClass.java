package com.example.atilagapps.hellixdatamanager.StaffManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.atilagapps.hellixdatamanager.DataBaseHelper;
import com.example.atilagapps.hellixdatamanager.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class StaffImageDialogueClass extends DialogFragment {

    ImageView proImgD,editProImgD,deleteProImage;
    byte[] thumb;
    byte[] initThumb;
    private Uri imageUri;
    String id;
    private Bitmap compressor;
    private StaffImageDialogueListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
       // return super.onCreateDialog(savedInstanceState);


        MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(requireActivity(),R.style.AlertDialogTheme);
        //  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.image_dialogue, null);


        proImgD=v.findViewById(R.id.dialogueImgId);
        editProImgD=v.findViewById(R.id.dialogueEditImageId);
        deleteProImage=v.findViewById(R.id.dialogueDeleteImageId);


        assert getArguments() != null;
        initThumb=getArguments().getByteArray("Staff_IMG");
        id=getArguments().getString("Staff_Id_img");

        if (initThumb!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(initThumb, 0, initThumb.length);
            proImgD.setImageBitmap(bitmap);
        }

        editProImgD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        deleteProImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStaffImg();
            }
        });

        builder.setView(v);



        return builder.create();
    }


    private void deleteStaffImg() {
        DataBaseHelper db=new DataBaseHelper(getContext());
        db.deleteStaffImage(id);
        proImgD.setImageResource(R.drawable.user);
        listener.deleteImage();
        Toast.makeText(getContext(), "Profile Image deleted Successfully", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (StaffImageDialogueListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Listener");
        }
    }

    public interface StaffImageDialogueListener {
        void getStaffImage(byte[] img);
        void deleteImage();

    }

    private void chooseImage() {

        Intent intent = CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .getIntent(requireActivity());
        startActivityForResult(intent,CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                assert result != null;
                imageUri=result.getUri();
                proImgD.setImageURI(imageUri);
                getfile(imageUri);
            }else if (resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error=result.getError();
            }
        }
    }

    private void getfile(Uri imageUri) {

        File newFile=new File(Objects.requireNonNull(imageUri.getPath()));
        try{
            compressor =new Compressor(getActivity())
                    .setMaxWidth(100)
                    .setMaxHeight(100)
                    .setQuality(50)
                    .compressToBitmap(newFile);
        }catch (IOException e){
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        compressor.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        thumb=byteArrayOutputStream.toByteArray();
        listener.getStaffImage(thumb);

    }


}
