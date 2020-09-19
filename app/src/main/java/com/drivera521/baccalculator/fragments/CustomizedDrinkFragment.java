package com.example.daniel.riveradaniel_project1.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.daniel.riveradaniel_project1.R;
import com.example.daniel.riveradaniel_project1.drink_database.DrinkContract;
import com.example.daniel.riveradaniel_project1.drink_database.DrinkDatabaseHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class CustomizedDrinkFragment extends Fragment {

    private Button customAddBtn;
    private Button customCancelBtn;
    private Button customAddPhotoBtn;

    private EditText customDrinkName;
    private EditText customDrinkPercentage;
    private EditText customDrinkVolume;

    private ImageView customDrinkImage;

    private static final int REQUEST_IMAGE_CAPTURE = 0x01001;

    private String imageFileLocation;


    public static CustomizedDrinkFragment newInstance() {

        return new CustomizedDrinkFragment();
    }

        @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.customized_drink_fragment_layout,container,false);

        customDrinkName = v.findViewById(R.id.custom_drink_name);
        customDrinkPercentage = v.findViewById(R.id.custom_drink_alcohol_percent);
        customDrinkVolume = v.findViewById(R.id.custom_drink_volume);

        customDrinkImage = v.findViewById(R.id.custom_drink_image);

        customAddBtn = v.findViewById(R.id.custom_drink_add_btn);
        customCancelBtn = v.findViewById(R.id.custom_drink_cancel_btn);
        customAddPhotoBtn = v.findViewById(R.id.add_image_btn);

        customAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drinkName;
                String drinkPercentage;
                String drinkVolume;

                if(validateField(customDrinkName.getText().toString()) &&
                        validateField(customDrinkPercentage.getText().toString()) &&
                        validateField(customDrinkVolume.getText().toString())){

                    drinkName = customDrinkName.getText().toString();
                    drinkPercentage = customDrinkPercentage.getText().toString();
                    drinkVolume = customDrinkVolume.getText().toString();

                    if(Double.valueOf(drinkPercentage) <= 0 || Double.valueOf(drinkVolume) <= 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Invalid Value")
                                .setMessage("Please ensure that all values are greater than zero.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        builder.show();
                    } else {

                        saveDrink(drinkName,drinkPercentage,drinkVolume);
                        getActivity().finish();

                    }

                }


            }
        });

        customAddPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                invokeCamera();
            }
        });

        customCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });



        return v;
    }



    private void invokeCamera(){

        dispatchTakePictureIntent();

        customAddPhotoBtn.setVisibility(View.INVISIBLE);
        customAddPhotoBtn.setEnabled(false);
    }
    private String calculateStdDrink(String drinkPercent, String drinkVolume){

        double doubleDrinkPercent = Double.parseDouble(drinkPercent);
        double doubleDrinkVolume = Double.parseDouble(drinkVolume);

        double stdDrinkVolume = 60/doubleDrinkPercent;
        double numStdDrinks = doubleDrinkVolume/stdDrinkVolume;

        String result = String.format(Locale.US,"%1$.1f",numStdDrinks);
        return String.valueOf(result);

    }

    private boolean validateField(String string){

        if (string.isEmpty()||string.trim().equals("")){
            Toast.makeText(getActivity(),"Please ensure that all fields are filled out",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void saveDrink(String name, String percent, String volume){

        if (imageFileLocation == null){
            Toast.makeText(getActivity(),"Missing image. Use camera to set the drink image",Toast.LENGTH_LONG).show();
        } else {
            String stdDrink = calculateStdDrink(percent,volume);
            DrinkDatabaseHelper db = new DrinkDatabaseHelper(getActivity());
            SQLiteDatabase sq = db.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(DrinkContract.DRINKNAME,name);
            cv.put(DrinkContract.DRINKPERCENTAGE,percent);
            cv.put(DrinkContract.DRINKVOLUME,volume);
            cv.put(DrinkContract.STANDARDDRINK,stdDrink);
            cv.put(DrinkContract.DRINKIMAGE,imageFileLocation);

            sq.insert(DrinkContract.DATA_TABLE,null,cv);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            setpic();
        }
    }

    private File getOutputFile() throws IOException{
            //get our external Storage location.
            File protectedStorage = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            //create a folder in our external storage


            // Generate name for the image to be saved.
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new java.util.Date());
            String imageName = "BAC" + timeStamp;
            //create a file inside of our pictures folder.

            File pictureFile = File.createTempFile(
                    imageName, //prefix
                    ".jpg", //suffix
                    protectedStorage
            );

            imageFileLocation = pictureFile.getAbsolutePath();
            return pictureFile;
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null){
            File photoFile = null;

            try{
                photoFile = getOutputFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //continues if the file was successfully created.

            if(photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(getActivity(),
                        getActivity().getApplicationContext().getPackageName() + ".fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void setpic(){

        // get dimensions of the view

        int targetW = customDrinkImage.getWidth();
        int targetH = customDrinkImage.getHeight();

        // get dimensions of the bitMap

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFileLocation,bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(imageFileLocation, bmOptions);
        customDrinkImage.setImageBitmap(bitmap);
    }
}
