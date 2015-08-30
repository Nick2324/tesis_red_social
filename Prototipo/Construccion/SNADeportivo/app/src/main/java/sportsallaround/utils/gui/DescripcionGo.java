package sportsallaround.utils.gui;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import sportsallaround.snadeportivo.R;

public class DescripcionGo extends Fragment {

    private DescripcionGoCallBack goCallBack;

    public interface DescripcionGoCallBack{
        public String getNombreBoton();
        public String getDefaultDescripcion();
        public void go();
    }

    public DescripcionGo() {}

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            this.goCallBack = (DescripcionGoCallBack)activity;
        }catch(Exception e){
            Log.d("Nick","DescripcionGoCallBack no soportado");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_descripcion_go, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        this.setUpWidgets();
        this.setUpListeners();
    }

    private void setUpListeners(){
        Button go = (Button)getView().findViewById(R.id.button_go);
        go.setText(this.goCallBack.getNombreBoton());
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCallBack.go();
            }
        });
    }

    private void setUpWidgets(){
        TextSwitcher informacionGo = (TextSwitcher)getView().findViewById(R.id.switcher_informacion);
        informacionGo.setFactory(
                new ViewSwitcher.ViewFactory() {

                    public View makeView() {
                        TextView descripcionGeneralEv = new TextView(getActivity());
                        descripcionGeneralEv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                        descripcionGeneralEv.setTextSize(16);
                        return descripcionGeneralEv;
                    }

                });
        informacionGo.setText(this.goCallBack.getDefaultDescripcion());
        informacionGo.setInAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left));
        informacionGo.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right));
    }

    public void setDescripcion(String informacion){
        TextSwitcher descripcion = (TextSwitcher)getView().findViewById(R.id.switcher_informacion);
        descripcion.setText(informacion);
    }

}
