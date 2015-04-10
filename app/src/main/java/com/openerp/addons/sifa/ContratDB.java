package com.openerp.addons.sifa;

import android.content.Context;

import com.openerp.base.res.ResPartnerDB;
import com.openerp.orm.OEColumn;
import com.openerp.orm.OEDatabase;
import com.openerp.orm.OEFields;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abenbouchta on 09/04/15.
 */
public class ContratDB extends OEDatabase {
    Context mContext;
    public ContratDB(Context context){
        super(context);
        this.mContext = context;
    }

    @Override
    public String getModelName() {
        return "sfp.contrat";
    }

    @Override
    public List<OEColumn> getModelColumns() {
        ArrayList<OEColumn> cols = new ArrayList<OEColumn>();
        cols.add(new OEColumn("name","name",OEFields.varchar(64)));
        cols.add(new OEColumn("partner_id", "Customer", OEFields.manyToOne(new ResPartnerDB(mContext))));



        return cols;
    }


}
