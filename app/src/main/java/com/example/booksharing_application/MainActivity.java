package com.example.booksharing_application;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_CODE = 101;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private AppBarConfiguration appBarConfiguration;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        imageView = headerView.findViewById(R.id.imageView);

        setSupportActionBar(toolbar);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_profile,
                R.id.nav_sharebook,
                R.id.nav_sharedbooks,
                R.id.nav_contactUs,
                R.id.nav_feedback,
                R.id.nav_aboutApp,
                R.id.nav_logout
        )
                .setDrawerLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    navController.navigate(R.id.nav_home);
                } else if (id == R.id.nav_profile) {
                    navController.navigate(R.id.nav_profile);
                } else if (id == R.id.nav_sharebook) {
                    navController.navigate(R.id.nav_sharebook);
                } else if (id == R.id.nav_sharedbooks) {
                    navController.navigate(R.id.nav_sharedbooks);
                } else if (id == R.id.nav_contactUs) {
                    navController.navigate(R.id.nav_contactUs);
                } else if (id == R.id.nav_feedback) {
                    navController.navigate(R.id.nav_feedback);
                } else if (id == R.id.nav_aboutApp) {
                    navController.navigate(R.id.nav_aboutApp);
                } else if (id == R.id.nav_logout) {
                    showLogoutDialog();
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermission();
            }
        });
    }

    private boolean isCameraAvailable() {
        PackageManager packageManager = getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
    }

    private void checkCameraPermission() {
        if (!isCameraAvailable()) {
            Toast.makeText(this, "Front camera not available on this device", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Set extras to prioritize the front camera
        cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
        cameraIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
        cameraIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);

        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_CAPTURE_CODE);
        } else {
            Toast.makeText(this, "Camera not supported on this device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to use this feature", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_CODE && resultCode == RESULT_OK) {
            // Get the image captured by the camera
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Set the image to the ImageView
            imageView.setImageBitmap(photo);
        }
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity(); // Closes all activities
                        System.exit(0); // Exits the app process
                    }
                })
                .setNegativeButton("No", null)
                .setCancelable(true)
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}