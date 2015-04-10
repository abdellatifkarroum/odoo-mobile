package com.openerp.addons.sifa;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.openerp.R;
import com.openerp.addons.sifa.providers.contrat.ContratProvider;
import com.openerp.addons.sifa.ContratDB;
import com.openerp.orm.OEDataRow;
import com.openerp.receivers.SyncFinishReceiver;
import com.openerp.support.AppScope;
import com.openerp.support.BaseFragment;
import com.openerp.support.listview.OEListAdapter;
import com.openerp.util.Base64Helper;
import com.openerp.util.drawer.DrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abenbouchta on 09/04/15.
 */
public class Contrat extends BaseFragment{
    public static final String TAG = Contrat.class.getSimpleName();
    ContratLoader mContratLoader = null;
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
        bundle.putString("contrat_value", value);
        contrat.setArguments(bundle);
        return contrat;

    }

    View mView = null;
           ListView mListview = null;
          OEListAdapter mListAdapter = null;
           List<Object> mContratItems = new ArrayList<Object>();

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                           Bundle savedInstanceState) {
     scope = new AppScope(getActivity());
     mView = inflater.inflate(R.layout.sifa_contrat_layout, container, false);
         init();
     return mView;
           }
    private void init() {
        mListview = (ListView) mView.findViewById(R.id.sifaContratListView);
        mListAdapter = new OEListAdapter(getActivity(),
                R.layout.sifa_contrat_custom_row, mContratItems) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null)
                    view = getActivity().getLayoutInflater().inflate(
                            getResource(), parent, false);

                OEDataRow row = (OEDataRow) mContratItems.get(position);

                ImageView imgCustomerPic = (ImageView) view
                        .findViewById(R.id.imgCustomerPic);


                TextView txvName, txvSalesPerson, txvDescription;
                txvName = (TextView) view.findViewById(R.id.txvContratName);


                txvName.setText(row.getString("name"));

                String salesPerson = "";

                return view;
            }
        };
        mListview.setAdapter(mListAdapter);
        mContratLoader = new ContratLoader();
        mContratLoader.execute();
    }

    class ContratLoader extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            mContratItems.clear();
            ContratDB db = new ContratDB(getActivity());
            mContratItems.addAll(db.select());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mListAdapter.notifiyDataChange(mContratItems);
            checkStatus();
        }
    }

    private void checkStatus() {
        if (db().isEmptyTable()) {
            scope.main().requestSync(ContratProvider.AUTHORITY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mSyncFinish,
                new IntentFilter(SyncFinishReceiver.SYNC_FINISH));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mSyncFinish);
    }

    SyncFinishReceiver mSyncFinish = new SyncFinishReceiver() {
        public void onReceive(Context context, android.content.Intent intent) {
            mContratLoader = new ContratLoader();
            mContratLoader.execute();
        };
    };
}
