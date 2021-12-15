package com.armughanaslam.appcon_2021;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.armughanaslam.appcon_2021.Activity.Scanner;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRA extends AppCompatActivity {

    EditText qr_value;
    Button qr_generate;
    Button qr_scan;
    Button qr_scan_camera;
    ImageView qr_img;

    List<Goal_QR> goals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qra);

        // To make a new directory
        String directory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(directory +"/DCIM/QRCodes");
        if(!folder.exists()){ //create folder if doesn't exist
            folder.mkdir();
        }

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
        // Requesting Permissions
        ActivityCompat.requestPermissions(this, PERMISSIONS,
                PERMISSION_ALL);




        qr_value = findViewById(R.id.qr_text);
        qr_generate = findViewById(R.id.qr_generate);
        qr_scan = findViewById(R.id.qr_scan);
        qr_scan_camera = findViewById(R.id.qr_scan_camera);
        qr_img = findViewById(R.id.qr_img);

        //Initialize a list of goals
        goals = new ArrayList<Goal_QR>();
        goals.add(new Goal_QR("123","Armughan Aslam",
                "started fund raising to give access to clean water",
                12000,5000));
        goals.add(new Goal_QR("456","Abdul Mueez",
                "started fund raising to give free education",
                15000,9500));
        goals.add(new Goal_QR("789","Muhammad Tufail",
                "wants to raise funds for an orphanage",
                10000,7000));

        //genarate qr and store in gallery
        qr_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = qr_value.getText().toString();
                if(value.isEmpty()){
                    qr_value.setError("Value Required!");
                }
                else{
                    Date now = new Date();
                    android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                    try {
                        String directory = Environment.getExternalStorageDirectory().toString();
                        File folder = new File(directory +"/DCIM/QRCodes");
                        if(!folder.exists()) //create folder if doesn't exist
                            folder.mkdir();

                        String mPath =  directory + "/DCIM/QRCodes/" + now + ".jpg";


                        QRGEncoder qrgEncoder = new QRGEncoder(value, null, QRGContents.Type.TEXT, 500);
                        Bitmap qrBits = qrgEncoder.getBitmap();
                        qr_img.setImageBitmap(qrBits);

                        File imageFile = new File(mPath);

                        //save the QR Code Image
                        FileOutputStream outputStream = null;

                        outputStream = new FileOutputStream(imageFile);
                        int quality = 100;
                        qrBits.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                        outputStream.flush();
                        outputStream.close();

                        //notify the gallery of new file created
                        MediaScannerConnection.scanFile(QRA.this,
                                new String[] { mPath }, null,
                                new MediaScannerConnection.OnScanCompletedListener() {
                                    @Override
                                    public void onScanCompleted(String path, Uri uri) {
                                        //....
                                    }
                                });

                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        // fetching qr image from gallery
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                        }
                    }
                });

        qr_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanGallery(someActivityResultLauncher);
            }
        });

        //
        qr_scan_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QRA.this, Scanner.class));
            }
        });

    }

    public void scanGallery(ActivityResultLauncher<Intent> someActivityResultLauncher){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        someActivityResultLauncher.launch(photoPickerIntent);
    }


    @Override

    protected void onActivityResult(int reqCode, int resultCode, Intent data) {

        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            try {

                final Uri imageUri = data.getData();

                final InputStream imageStream = getContentResolver().openInputStream(imageUri);

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                try {

                    Bitmap bMap = selectedImage;

                    String contents = null;

                    int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];

                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

                    LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);

                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                    Reader reader = new MultiFormatReader();

                    Result result = reader.decode(bitmap);

                    contents = result.getText();

                    Toast.makeText(getApplicationContext(),contents,Toast.LENGTH_LONG).show();

                    Intent i = new Intent(QRA.this, GoalDetailsQR.class);
                    i.putExtra("code", contents);
                    startActivity(i);

                }catch (Exception e){

                    e.printStackTrace();

                }

                //  image_view.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {

                e.printStackTrace();

                Toast.makeText(QRA.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }



        }else {

            Toast.makeText(QRA.this, "You haven't picked Image",Toast.LENGTH_LONG).show();

        }

    }

}