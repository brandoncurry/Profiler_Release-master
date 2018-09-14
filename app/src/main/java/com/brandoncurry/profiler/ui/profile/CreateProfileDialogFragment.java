package com.brandoncurry.profiler.ui.profile;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.brandoncurry.profiler.R;
import com.brandoncurry.profiler.constants.Constants;
import com.brandoncurry.profiler.models.Profile;
import com.brandoncurry.profiler.ui.main.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

//TODO Create a base class for this. Duplicate code between here and RegisterActivity...
public class CreateProfileDialogFragment extends DialogFragment {
    private ImageView profileImage;
    private EditText etName, etEmail, etPassword, etAge, etHobbies;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private String imageUrl;
    private RadioButton rbMale, rbFemale;

    private static final int SELECT_PICTURE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_register, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
            }
        };


        profileImage = rootView.findViewById(R.id.ivProfilePhoto);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, getResources().getString(R.string.select_photo)),SELECT_PICTURE );
            }
        });
        profileImage.setDrawingCacheEnabled(true);
        profileImage.buildDrawingCache();

        etName = rootView.findViewById(R.id.etUserName);
        etEmail = rootView.findViewById(R.id.etEmail);
        etPassword = rootView.findViewById(R.id.etPassword);
        etAge = rootView.findViewById(R.id.etAge);
        etHobbies = rootView.findViewById(R.id.etHobbies);
        rbMale = rootView.findViewById(R.id.rbMale);
        rbFemale = rootView.findViewById(R.id.rbFemale);
        btnRegister = rootView.findViewById(R.id.btnRegister);
        btnRegister.setText(getResources().getString(R.string.create_profile));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    uploadImage();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getResources().getString(R.string.missing_profile_info))
                            .setCancelable(false)
                            .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode== getActivity().RESULT_OK){
            if(requestCode==SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    profileImage.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private void createNewProfile(String userId, String imageUrl, String name, String age, String gender, String hobbies, String bgColor) {
        //User is already registered, just create a new profile
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String randomId = UUID.randomUUID().toString();
            Profile profile = new Profile(randomId, userId, imageUrl, name, age, gender, hobbies, bgColor);
            databaseReference.child("Profile").child(randomId).setValue(profile);
            dismiss();
        } else {

        }

    }

    public void uploadImage(){
        StorageReference storageRef = firebaseStorage.getReference();

        StorageReference profilePhotosRef = storageRef.child("profilephoto" + UUID.randomUUID() + ".jpg");
        StorageReference mountainImagesRef = storageRef.child("images/profilephoto.jpg");

        profilePhotosRef.getName().equals(mountainImagesRef.getName());
        profilePhotosRef.getPath().equals(mountainImagesRef.getPath());

        Bitmap bitmap = profileImage.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profilePhotosRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                imageUrl = downloadUrl.toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    createNewProfile(user.getUid(), imageUrl, etName.getText().toString().trim(), etAge.getText().toString().trim(), getGender(), etHobbies.getText().toString().trim(), getColorForGender());
                } else {

                }

            }
        });
    }

    public String getGender(){
        String gender = "";

        if(rbMale.isChecked()){
            gender = Constants.PROFILE_MALE;
        } else if (rbFemale.isChecked()){
            gender = Constants.PROFILE_FEMALE;
        }

        return gender;
    }

    public String getColorForGender(){
        switch (getGender()){
            case Constants.PROFILE_MALE:
                return Constants.DEFAULT_BACKGROUND_COLOR_BLUE;
            case Constants.PROFILE_FEMALE:
                return Constants.DEFAULT_BACKGROUND_COLOR_PINK;
            default:
                return Constants.DEFAULT_BACKGROUND_COLOR_BLUE;
        }
    }

    public boolean validateForm(){
        if(!etName.getText().toString().isEmpty()
                && !etAge.getText().toString().isEmpty()
                && !etHobbies.getText().toString().isEmpty()
                && (rbMale.isChecked() || rbFemale.isChecked())){
            return true;
        } else return false;
    }

}