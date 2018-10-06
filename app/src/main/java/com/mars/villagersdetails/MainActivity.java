package com.mars.villagersdetails;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int SELECT_FILE = 1;
    Button b;
    ImageButton ib,ib2;
    protected static final int CAMERA_REQUEST = 1;
    protected static final int GALLERY_PICTURE = 1;
    String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isStoragePermissionGranted();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ib = findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                selectImage();
//                Intent takePicture = new Intent("android.media.action.IMAGE_CAPTURE");
//                startActivityForResult(takePicture,0);
//                Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(pickPhoto,"Select Picture"),1);
            }
        });

        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserBean user = new UserBean(((EditText) findViewById(R.id.editText)).getText().toString(), ((EditText) findViewById(R.id.editText2)).getText().toString(), ((EditText) findViewById(R.id.editText3)).getText().toString(), ((EditText) findViewById(R.id.editText4)).getText().toString(), ((Spinner) findViewById(R.id.spinner9)).getSelectedItem().toString(), ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString(), ((Spinner) findViewById(R.id.spinner2)).getSelectedItem().toString(), ((Spinner) findViewById(R.id.spinner3)).getSelectedItem().toString(), ((Spinner) findViewById(R.id.spinner4)).getSelectedItem().toString(), ((EditText) findViewById(R.id.editText5)).getText().toString(), ((EditText) findViewById(R.id.editText6)).getText().toString(), ((EditText) findViewById(R.id.editText9)).getText().toString(), ((EditText) findViewById(R.id.editText12)).getText().toString(), ((EditText) findViewById(R.id.editText13)).getText().toString(), ((EditText) findViewById(R.id.editText14)).getText().toString(), ((EditText) findViewById(R.id.editText15)).getText().toString(), ((Spinner) findViewById(R.id.spinner5)).getSelectedItem().toString(), ((EditText) findViewById(R.id.editText17)).getText().toString(), ((Spinner) findViewById(R.id.spinner6)).getSelectedItem().toString(), ((Spinner) findViewById(R.id.spinner7)).getSelectedItem().toString(), ((Spinner) findViewById(R.id.spinner8)).getSelectedItem().toString());
                File pdfFolder = new File(Environment.getExternalStorageDirectory().toString() + "/VillagersDetails");
                if (!pdfFolder.exists()) {
                    pdfFolder.mkdir();
                    Log.i("Created", "PDF directory created");
                }
                String FILE = pdfFolder + "/" + user.getName() + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";
                try {
                    Document document = new Document(PageSize.A4);
                    PdfWriter.getInstance(document, new FileOutputStream(FILE));
                    document.open();
                    addMetaData(document);
                    addContent(document, user);
                    document.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(MainActivity.this, "PDF generated at " + FILE, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void selectImage(){
        final CharSequence[] items = { "Take Photo" , "Choose from Library" , "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")){
                    userChoosenTask="Take Photo";
//                    if(result)
                        cameraIntent();
                }else if (items[item].equals("Choose from Library")){
                    userChoosenTask="Choose from Library";
//                    if (result)
                        galleryIntent();
                }else if (items[item].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent,CAMERA_REQUEST);
    }

    private void galleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select File"),SELECT_FILE);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("INFO", "Permission is granted");
                return true;
            } else{

                Log.v("INFO", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("INFO", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (userChoosenTask.equals("Take Photo"))
                cameraIntent();
            else if(userChoosenTask.equals("Choose from Library"))
                galleryIntent();
            Log.v("INFO", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
        if (grantResults[1] == PackageManager.PERMISSION_GRANTED)
            Log.v("INFO", "Permission: " + permissions[1] + "was " + grantResults[1]);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data){
        super.onActivityResult(reqCode, resultCode,data);
        if (resultCode == Activity.RESULT_OK){
            if (reqCode == SELECT_FILE)
                onSelectFromGallaryResult(data);
            else if(reqCode == CAMERA_REQUEST)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGallaryResult(Intent data){
        Bitmap bm=null;
        if (data!=null){
            try{
                bm =MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ib.setImageBitmap(bm);
    }
    private void onCaptureImageResult(Intent data){
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG,90,bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".jpg");
        FileOutputStream fo;
        try{
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ib.setImageBitmap(thumbnail);
    }

    //    TextView txtScroll = (TextView) findViewById(R.id.scrollView2);
//        txtScroll.setMovementMethod(new ScrollingMovementMethod());
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("Villager's Detail");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("MARStech");
        document.addCreator("MARStech");
    }

    private static void addContent(Document document, UserBean user) throws DocumentException {
        Anchor anchor = new Anchor("Villager's Details", catFont);
        anchor.setName("Villager's Details");

        // Second parameter is the number of the chapter
        Paragraph catPart = new Paragraph(anchor);
        // add a table
        createTable(catPart, user);

        // now add all this to the document
        document.add(catPart);
    }

    private static void createTable(Paragraph subCatPart, UserBean user)
            throws BadElementException {
        PdfPTable table = new PdfPTable(2);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.addCell("Name: ");
        table.addCell(user.getName());
        table.addCell("Father's Name: ");
        table.addCell(user.getFname());
        table.addCell("Mother's Name: ");
        table.addCell(user.getMname());
        table.addCell("Address: ");
        table.addCell(user.getAddress());
        table.addCell("Gender: ");
        table.addCell(user.getGender());
        table.addCell("Marrital Status: ");
        table.addCell(user.getStatus());
        table.addCell("Qualification: ");
        table.addCell(user.getQual());
        table.addCell("Category: ");
        table.addCell(user.getCategory());
        table.addCell("Religion: ");
        table.addCell(user.getReligon());
        table.addCell("Aadhar No.: ");
        table.addCell(user.getAadhar());
        table.addCell("Voter ID No.: ");
        table.addCell(user.getVoterId());
        table.addCell("Bank Account No.: ");
        table.addCell(user.getBankAC());
        table.addCell("Bank IFSC Code.: ");
        table.addCell(user.getIfsc());
        table.addCell("Branch Name: ");
        table.addCell(user.getBranch());
        table.addCell("Job Card Number: ");
        table.addCell(user.getJobId());
        table.addCell("Handpump: ");
        table.addCell(user.getSupply());
        table.addCell("House Details: ");
        table.addCell(user.getDetail());
        table.addCell("Toilet: ");
        table.addCell(user.getToilet());
        table.addCell("Number of Persons: ");
        table.addCell("Gents: " + user.getGents() + " Ladies: " + user.getLadies());

        subCatPart.add(table);

    }
}
