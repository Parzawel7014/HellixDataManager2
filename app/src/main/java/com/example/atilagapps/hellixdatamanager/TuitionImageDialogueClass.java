package com.example.atilagapps.hellixdatamanager;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class TuitionImageDialogueClass extends DialogFragment {

    ImageView proImgD,editProImgD,deleteProImgId;
    byte[] thumb;
    byte[] initThumb;
    private Uri imageUri;
    private Bitmap compressor;
    private ImageDialogueListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
       // return super.onCreateDialog(savedInstanceState);


        MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(requireActivity());
        //  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.tui_image_dialogue, null);


        proImgD=v.findViewById(R.id.dialogueImgId);
        editProImgD=v.findViewById(R.id.dialogueEditImageId);
        deleteProImgId=v.findViewById(R.id.dialogueDeleteImageId);


        assert getArguments() != null;
        initThumb=getArguments().getByteArray("IMG1");

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

        deleteProImgId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImg();
            }
        });


        builder.setView(v);



        return builder.create();
    }

    private void deleteImg() {
        DataBaseHelper db=new DataBaseHelper(getContext());
        db.deleteTuiImage();
        proImgD.setImageResource(R.drawable.camera);
        listener.deleteImage();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ImageDialogueListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Listener");
        }
    }

    public interface ImageDialogueListener {
        void getImage(byte[] img);
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
        listener.getImage(thumb);

    }


}
