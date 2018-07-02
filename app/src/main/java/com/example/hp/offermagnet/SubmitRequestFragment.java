package com.example.hp.offermagnet;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.request.SimpleMultiPartRequest;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;


import static android.app.Activity.RESULT_OK;


public class SubmitRequestFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatePickerDialog.OnDateSetListener datePicker;
    Button btnSetDate;
    TextView txtDate;
    Spinner spnCity;
    private String mParam1;
    private String mParam2;
    HashMap<String,Integer> cate ;
    boolean t,d=false;

    public SubmitRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView txtMeters;
    SeekBar seekbar;
    EditText txtTitle;
    Spinner spnCategory;
    EditText txtDesc;
    Button btnSubmit, addAttach;
    String date;
    int a = 1;
    ImageView show_image;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    Database db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_request, container, false);
        cate = new HashMap<>();
        cate.put("Books",2);
        cate.put("Foods",1);
        cate.put("Electronics",4);
        cate.put("Fashions",3);
        cate.put("Offices",5);
        db=new Database(getActivity());
        spnCity=(Spinner)view.findViewById(R.id.spnCity);
        txtMeters = (TextView) view.findViewById(R.id.textKilo);
        seekbar = (SeekBar) view.findViewById(R.id.barLocation);
        requestStoragePermission();
        spnCategory=(Spinner)view.findViewById(R.id.spinnerCategory) ;
        addAttach = (Button) view.findViewById(R.id.addAttachment);
        show_image = (ImageView) view.findViewById(R.id.show_image2);

        if (spnCategory.getSelectedItem().toString().trim().equals("Category")) {
            spnCategory.setFocusable(true);


        }
        if (spnCity.getSelectedItem().toString().trim().equals("Category")) {
            spnCity.setFocusable(true);

        }

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtMeters.setText(seekbar.getProgress() + " kilo maeters");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        txtTitle = (EditText) view.findViewById(R.id.title);
        txtDesc = (EditText) view.findViewById(R.id.description);
        txtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtTitle.getText().length()==0)
                {
                    txtTitle.setError("please enter a valid name");
                }
                else {
                    t=true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtDesc.getText().length()==0)
                {
                    txtDesc.setError("required");
                }

                else {
                    d=true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
     /*   if(spnCategory.getSelectedItem().toString().trim().equals("Category")){
            spnCategory.setFocusable(true);
        }*/

        addAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        btnSetDate = (Button) view.findViewById(R.id.btnSetDate);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        v.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , datePicker
                        , year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = month + "-" + dayOfMonth + "-" + year;
                txtDate.setText(date);
            }
        };
        btnSubmit = (Button) view.findViewById(R.id.submitRequest);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( !(t ||d||date.equals(""))) {
                    Toast.makeText(getContext().getApplicationContext(), "InValid", Toast.LENGTH_SHORT).show();
                } else {
//                    submitReq();

                    String path = "";
                    try {
                        path = getPath(filePath);

                    }catch (Exception e){
                        Toast.makeText(getActivity(),"Can't get Image Path", Toast.LENGTH_LONG).show();
                    }
                    //Uploading code
                    try {
                        String uploadId = UUID.randomUUID().toString();
                        Toast.makeText(getActivity(), path+"\n"+txtTitle.getText().toString()+"\n"+txtDesc.getText().toString()+"\n"+txtDate.getText().toString(), Toast.LENGTH_SHORT).show();

                        //Creating a multi part request
                        new MultipartUploadRequest(getActivity(), uploadId, "https://offer-system.000webhostapp.com/InsertRequest.php")
                                .addFileToUpload(path, "image") //Adding file
                                .addParameter("title", txtTitle.getText().toString())
                                .addParameter("dis", txtDesc.getText().toString())
                                .addParameter("validate_date", txtDate.getText().toString()+"val")
                                .addParameter("cat_id",String.valueOf(cate.get(spnCategory.getSelectedItem().toString()) ))
                                .addParameter("user_id", db.getId())
                                .addParameter("city", spnCity.getSelectedItem().toString())
                                .setNotificationConfig(new UploadNotificationConfig())
                                .setMaxRetries(2)
                                .startUpload();
                        Fragment done = new Task_is_complete();
                        FragmentManager ft = getFragmentManager();
                        ft.beginTransaction().replace(R.id.frameRequest, done).commit();
                       //Starting the upload

//                        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/InsertRequest.php",
//                                new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//                                        Log.d("Response", response);
//                                        Toast.makeText(getActivity(), "Ok", Toast.LENGTH_LONG).show();
//                                    }
//                                }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//                        });
//                        smr.addStringParam("title", txtTitle.getText().toString());
//                        smr.addStringParam("dis", txtTitle.getText().toString());
//                        smr.addStringParam("validate_date", txtTitle.getText().toString());
//                        smr.addStringParam("cat_id", txtTitle.getText().toString());
//                        smr.addStringParam("user_id", txtTitle.getText().toString());
//                        smr.addStringParam("city", txtTitle.getText().toString());
//                        smr.addFile("image", path);

                    } catch (Exception exc) {
                        Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        return view;
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                show_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


//    public void submitReq() {
//        final String URL = "http://192.168.1.26/InsertRequest.php";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//            @Override
//
//            public void onResponse(String response) {
//                try {
//
//                    if (response.contains("Request Approved!")) {
//                        Toast.makeText(getContext().getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
//                        Fragment done = new Task_is_complete();
//                        FragmentManager ft = getFragmentManager();
//                        ft.beginTransaction().replace(R.id.frameRequest, done).commit();
//                    } else {
//                        Toast.makeText(getContext().getApplicationContext(), response, Toast.LENGTH_SHORT).show();
//
//                    }
//
//                } catch (Exception e) {
//
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Toast.makeText(getContext().getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("title", txtTitle.getText().toString());
//                params.put("dis", txtDesc.getText().toString());
//                params.put("validate_date", date);
//                params.put("city", "zagazig");
//                params.put("cat_id", 1 + "");
//                params.put("user_id", 1 + "");
//
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(stringRequest);
//    }
}
