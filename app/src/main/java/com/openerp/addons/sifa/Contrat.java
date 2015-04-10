package com.openerp.addons.sifa;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.openerp.support.BaseFragment;
import com.openerp.util.drawer.DrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abenbouchta on 09/04/15.
 */
public class Contrat extends BaseFragment{
    public static final String TAG = Contrat.class.getSimpleName();

    @Override
    public Object databaseHelper(Context context) {
        return new ContratDB(context);
    }

    @Override
    public List<DrawerItem> drawerMenus(Context context) {
        List<DrawerItem> menu = new ArrayList<DrawerItem>();

        menu.add(new DrawerItem(TAG, "Contrats", true));
        menu.add(new DrawerItem(TAG, "Contrats", 0, "#cc0000", object("all")));
        return menu;
    }

    private Fragment object(String value) {
        Contrat contrat = new Contrat();
        Bundle bundle = new Bundle();
        bundle.putString("lead_value", value);
        contrat.setArguments(bundle);
        return contrat;

    }
}
