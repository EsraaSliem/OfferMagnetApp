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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class SubmitOfferFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatePickerDialog.OnDateSetListener DatePicker1, DatePicker2;


    public SubmitOfferFragment() {
        // Required empty public constructor
    }

boolean titleOffer=false;
    boolean descOffer=false;
    boolean descProduct=false;
    boolean price=false;
    EditText txtTitle, txtDesc, txtPrice, productDesc;
    Spinner spnCategory, producTitle, spinnerCity;
    Button btnFrom, btnTo;
    TextView txtFrom, txtTo;
    Button btnOffer, addAttach;
    String dateFrom, dateTo;
    ArrayList<String> arraySpinner;
    ImageView show_image;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    HashMap<String,Integer> cate ;
    Database db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_submit_offer, container, false);
        db=new Database(getActivity());
        txtTitle = (EditText) view.findViewById(R.id.title);
        txtDesc = (EditText) view.findViewById(R.id.description);
        producTitle = (Spinner) view.findViewById(R.id.productName);
        txtPrice = (EditText) view.findViewById(R.id.productPrice);
        productDesc = (EditText) view.findViewById(R.id.productDescription);
        spnCategory = (Spinner) view.findViewById(R.id.spinnerCategory);
        txtFrom = (TextView) view.findViewById(R.id.textFromDate);
        txtTo = (TextView) view.findViewById(R.id.textToDate);
        btnFrom = (Button) view.findViewById(R.id.btnFromDate);
        btnTo = (Button) view.findViewById(R.id.btnToDate);
        btnOffer = (Button) view.findViewById(R.id.btnOffer);
        addAttach = (Button) view.findViewById(R.id.addAttachment);
        show_image = (ImageView) view.findViewById(R.id.show_image);
        spinnerCity = (Spinner) view.findViewById(R.id.spinnerCity);
        final DatePickerDialog.OnDateSetListener datePicker1, datePicker2;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        requestStoragePermission();
        cate = new HashMap<>();
        cate.put("Books",2);
        cate.put("Foods",1);
        cate.put("Electronics",4);
        cate.put("Fashions",3);
        cate.put("Offices",5);
        addAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        txtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtPrice.getText().length() == 0) {
                    txtPrice.setError("required,please enter your price");

                }
                else price=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        productDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                productDesc.setError("Required.");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (productDesc.getText().length() == 0) {
                    productDesc.setError("required");

                }else descProduct=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtTitle.setError("enter your title here");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtTitle.getText().length() == 0) {
                    txtTitle.setError("required");
                }
                else titleOffer=true;
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
                if (txtDesc.getText().length() == 0)
                    txtDesc.setError("required");
                else descOffer=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (spnCategory.getSelectedItem().toString().trim().equals("Category")) {
            spnCategory.setFocusable(true);

        }


        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , DatePicker1
                        , year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }

        });
        DatePicker1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dateFrom = month + "/" + dayOfMonth + "/" + year;
                txtFrom.setText(dateFrom);
            }
        };
        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , DatePicker2
                        , year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });
        DatePicker2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dateTo = month + "/" + dayOfMonth + "/" + year;
                txtTo.setText(dateTo);
            }
        };

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadProductNames(cate.get(spnCategory.getSelectedItem().toString())+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(titleOffer || descOffer||descProduct ||dateFrom==""||dateTo==""))
                    Toast.makeText(getActivity(),"Please, Fill Your Date ",Toast.LENGTH_SHORT).show();
                    else{

                    String path = getPath(filePath);
                    Toast.makeText(getActivity(), path, Toast.LENGTH_SHORT).show();
//                insertOffer();
               /* if(txtPrice.getText().toString().equals("")&&txtTitle.getText().toString().equals("")&&txtDesc.getText().toString().equals("")
                     &&producTitle.getSelectedItem().toString().equals("")&&spnCategory.getSelectedItem().toString().equals("Category")  )

                {
                    Toast.makeText(getActivity(), "please full all ", Toast.LENGTH_SHORT).show();
                }*/
                    try {
                        String uploadId = UUID.randomUUID().toString();

                        //Creating a multi part request
                        new MultipartUploadRequest(getActivity(), uploadId, "https://offer-system.000webhostapp.com/InsertOffer.php")
                                .addFileToUpload(path, "image") //Adding file
                                .addParameter("user_id", db.getId())//Adding text parameter to the request
                                .addParameter("title", txtTitle.getText().toString())
                                .addParameter("city", spinnerCity.getSelectedItem().toString())
                                .addParameter("date_from", dateFrom)
                                .addParameter("date_to", dateTo)
                                .addParameter("description", txtDesc.getText().toString())
                                .addParameter("product_name", producTitle.getSelectedItem().toString())
                                .addParameter("price", txtPrice.getText().toString())
                                .addParameter("product_desc", productDesc.getText().toString())
                                .addParameter("user_id", db.getId())
                                .setNotificationConfig(new UploadNotificationConfig())
                                .setMaxRetries(2)
                                .startUpload();//Starting the upload


                    } catch (Exception exc) {
                        Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void insertOffer() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/InsertOffer.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getContext().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("response").contains("User Added Successfuly!")) {

                                Toast.makeText(getContext().getApplicationContext(), "connect", Toast.LENGTH_LONG).show();
                                Fragment productDetailFragment = new Offer_Done();
                                FragmentManager ft = getFragmentManager();
                                ft.beginTransaction().replace(R.id.offerFrame, productDetailFragment).commit();


                            } else if (jsonObject.getString("response").equals("OooPS ... something went wrong!"))

                                Toast.makeText(getContext().getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof ServerError)
                    Toast.makeText(getContext().getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(getContext().getApplicationContext(), "Connection Timed Out", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(getContext().getApplicationContext(), "Bad Network Connection", Toast.LENGTH_SHORT).show();
            }


        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("title", txtTitle.getText().toString());
                param.put("city", spnCategory.getSelectedItem().toString());
                param.put("date_from", dateFrom);
                param.put("date_to", dateTo);
                param.put("description", txtDesc.getText().toString());
                param.put("product_name", producTitle.getSelectedItem().toString());
                param.put("price", txtPrice.getText().toString());
                param.put("product_desc", productDesc.toString());
                param.put("user_id", String.valueOf(1));
                param.put("cat_id", String.valueOf(cate.get(spnCategory.getSelectedItem().toString()) ));
                return param;

            }
        };

        Volley.newRequestQueue(getContext()).add(postRequest);

    }

    public void loadProductNames(final String s) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/GetProducts.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getContext().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("names");
                            arraySpinner = new ArrayList<>();
                            for (int x = 0; x < jsonArray.length(); x++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(x);
                                arraySpinner.add(jsonObject1.getString("name"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, arraySpinner);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            producTitle.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof ServerError)
                    Toast.makeText(getContext().getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(getContext().getApplicationContext(), "Connection Timed Out", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(getContext().getApplicationContext(), "Bad Network Connection", Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("cat_id", s);
                return hashMap;
            }
        };
        Volley.newRequestQueue(getContext()).add(postRequest);
    }

    //Requesting permission
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
}

