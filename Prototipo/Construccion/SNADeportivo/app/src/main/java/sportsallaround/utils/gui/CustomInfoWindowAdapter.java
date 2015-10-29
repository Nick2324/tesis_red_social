package sportsallaround.utils.gui;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by luis on 10/28/15.
 */
public class CustomInfoWindowAdapter implements InfoWindowAdapter {

    Context context;

    public CustomInfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView temp;
        String[] elementosMostrar = marker.getSnippet().split("</br>");
        temp = new TextView(context);
        temp.setTextColor(Color.DKGRAY);
        temp.setText(Html.fromHtml("<b>" + marker.getTitle() + "</b>"));
        temp.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(temp);
        for (String elemento : elementosMostrar){
            if (!elemento.equals("")){
                temp = new TextView(context);
                temp.setText(elemento);
                layout.addView(temp);
            }
        }
        return layout;
    }
}
