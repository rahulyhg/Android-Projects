package com.example.uj.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.Date;

/**
 * Created by UJ on 18-Sep-18.
 */

public class CameraFragment extends Fragment {


    File destination;
    private static final int PICK_Camera_IMAGE = 2;
    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String name = String.valueOf(new Date());
        destination = new File(Environment
                .getExternalStorageDirectory(), name + ".jpg");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(destination));
        startActivityForResult(intent, PICK_Camera_IMAGE);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

}
