package sportsallaround.utils.gui;

import android.util.Log;

import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;

/**
 * Created by nicolas on 28/06/15.
 */
public class KeyValueItem {

    private Object key;
    private Object value;

    public KeyValueItem(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
