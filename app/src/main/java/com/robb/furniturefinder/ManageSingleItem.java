package com.robb.furniturefinder;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;

/**
 * Created by BobXu on 3/22/16.
 */
public class ManageSingleItem extends Activity {

    TextView title;
    TextView text;
    ImageView photo;
    Intent backIntent;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_single_item);
        final Intent intent = getIntent();
        title = (TextView) this.findViewById(R.id.title);
        text = (TextView) this.findViewById(R.id.text);
        photo = (ImageView) this.findViewById(R.id.image);

        title.setText(intent.getStringExtra("newTitle"));
        text.setText(intent.getStringExtra("newDesc"));
        photo.setImageBitmap((Bitmap) intent.getParcelableExtra("newImage"));

        Button bt_photo = (Button) this.findViewById(R.id.button_photo);
        Button bt_text = (Button) this.findViewById(R.id.button_text);
        Button bt_title = (Button) this.findViewById(R.id.button_title);
        Button bt_back = (Button) this.findViewById(R.id.button_back);

        backIntent = new Intent(this, MainActivity.class);
        backIntent.putExtra("back", 0);
        backIntent.putExtra("pos", intent.getIntExtra("pos", 0));
        backIntent.putExtra("newTitle", title.getText().toString());
        backIntent.putExtra("newDesc", text.getText().toString());
        backIntent.putExtra("newImage", intent.getParcelableExtra("newImage"));
        bt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPhoto();
            }
        });
        bt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTitle();
            }
        });
        bt_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText();
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backIntent.putExtra("newTitle", title.getText().toString());
                backIntent.putExtra("newDesc", text.getText().toString());
                startActivity(backIntent);
            }
        });
    }

    int REQUEST_CAMERA=1;
    int SELECT_FILE=2;

    private void editPhoto() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, SELECT_FILE);
                }
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                photo.setImageBitmap(bm);
                backIntent.putExtra("newImage", bm);
            }
            else if (requestCode == SELECT_FILE) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Bitmap bm = BitmapFactory.decodeFile(picturePath);
                photo.setImageBitmap(bm);
                backIntent.putExtra("newImage", bm);
            }
        }
    }

    protected void editTitle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter a new title");
        final EditText inputTitle = new EditText(this);
        builder.setView(inputTitle);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                title.setText(inputTitle.getText());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    protected void editText() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter new text for description");
        final EditText inputText = new EditText(this);
        builder.setView(inputText);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                text.setText(inputText.getText());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}

