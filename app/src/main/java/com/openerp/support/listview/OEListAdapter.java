/**
 * OpenERP, Open Source Management Solution
 * Copyright (C) 2012-today OpenERP SA (<http://www.openerp.com>)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 * 
 */
package com.openerp.support.listview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;

public class OEListAdapter extends ArrayAdapter<Object> {
	public static final String TAG = "com.openerp.support.listview.OEListAdapter";
	Context mContext = null;
	List<Object> mObjects = null;
	List<Object> mAllObjects = null;
	RowFilter mFilter = null;
	int mResourceId = 0;
	RowFilterTextListener mRowFilterTextListener = null;

	public OEListAdapter(Context context, int resource, List<Object> objects) {
		super(context, resource, objects);
		Log.d(TAG, "OEListAdapter->constructor()");
		mContext = context;
		mObjects = new ArrayList<Object>(objects);
		mAllObjects = new ArrayList<Object>(objects);
		mResourceId = resource;
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new RowFilter();
		}
		return mFilter;
	}

	public int getResource() {
		return mResourceId;
	}

	public void replaceObjectAtPosition(int position, Object object) {
		mAllObjects.remove(position);
		mAllObjects.add(position, object);
		mObjects.remove(position);
		mObjects.add(position, object);
	}

	public void notifiyDataChange(List<Object> objects) {
		Log.d(TAG, "OEListAdapter->notifiyDataChange()");
		mAllObjects.clear();
		mObjects.clear();
		mAllObjects.addAll(objects);
		mObjects.addAll(objects);
		notifyDataSetChanged();
	}

	class RowFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults result = new FilterResults();
			if (!TextUtils.isEmpty(constraint)) {
				String searchingStr = constraint.toString().toLowerCase();
				List<Object> filteredItems = new ArrayList<Object>();
				for (Object item : mAllObjects) {
					String filterText = "";
					if (mRowFilterTextListener != null) {
						filterText = mRowFilterTextListener
								.filterCompareWith(item);
					} else {
						filterText = item.toString().toLowerCase();
					}
					if (filterText.contains(searchingStr)) {
						filteredItems.add(item);
					}
				}
				result.count = filteredItems.size();
				result.values = filteredItems;
			} else {
				synchronized (this) {
					result.count = mAllObjects.size();
					result.values = mAllObjects;
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			clear();
			mObjects = (List<Object>) results.values;
			addAll(mObjects);
			notifyDataSetChanged();
		}
	}

	public void setRowFilterTextListener(RowFilterTextListener listener) {
		mRowFilterTextListener = listener;
	}

	public interface RowFilterTextListener {
		public String filterCompareWith(Object object);
	}

}
