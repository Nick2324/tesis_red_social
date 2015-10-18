package sportsallaround.utils.gui;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import sportsallaround.snadeportivo.R;

public class PickImagen extends Fragment {

    private static int PICK_IMAGE = 20000;

    public PickImagen() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_imagen, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        this.setUpListeners();
    }

    public void setUpListeners(){
        ImageButton imagen = (ImageButton)getView().findViewById(R.id.button_imagen);
        imagen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(Intent.ACTION_PICK, Uri.parse(
                                "content://media/internal/images/media"));
                startActivityForResult(intent, PICK_IMAGE);
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PickImagen.PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            ImageButton imagenEvento = (ImageButton)getView().findViewById(R.id.button_imagen);
            imagenEvento.setImageURI(data.getData());
        }else{
            Log.e("Imagen", "Error cargando imagen");
        }
    }

    public Bitmap getImagen(){
        return ((BitmapDrawable)((ImageButton)getView().findViewById(R.id.button_imagen)).getDrawable()).getBitmap();
    }

}
