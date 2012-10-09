package se.egroup.alfred;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsActivity extends ListActivity {

	private FriendsListAdapter friendsAdapter;
	ArrayList<String> friends = new ArrayList<String>();
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);
		
		addFriends();
		
		friendsAdapter = new FriendsListAdapter(this.getBaseContext(), R.layout.friend_row, friends);
		setListAdapter(friendsAdapter);
		registerForContextMenu(getListView());
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("Meny");
		menu.add("Utmana");
		menu.add("Chatta");
		menu.add("Ta bort");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    String choice = info.toString();
	    Toast.makeText(this, choice, Toast.LENGTH_SHORT).show();
	    int index = info.position;
	    if(choice.equals("Utmana") || choice.equals("Chatta")){
		    Toast.makeText(this, choice, Toast.LENGTH_SHORT).show();
	    }
	    else if(choice.equals("Ta bort")){
		    Toast.makeText(this, choice, Toast.LENGTH_SHORT).show();
			friendsAdapter.removeItem(index);
			friendsAdapter.notifyDataSetChanged();
	    }
	    return false;
	}
	public void addFriends()
	{
		friends.add("Caroline Bergstedt");
		friends.add("David Buö");
		friends.add("Emil Bergwik");
		friends.add("Niklas Lavrell");
		friends.add("Magnus Kjellander");
	}	
	/**
	 * Anonymous inner class for filling the mission history list with history items
	 * @author Emil
	 *
	 */
	public class FriendsListAdapter extends ArrayAdapter<String>{
		private Context context;
		private	List<String> friends;

		public FriendsListAdapter(Context context, int textViewResourceId, List<String> friends) {
			super(context, textViewResourceId, friends);
			this.friends = friends;
			this.context = context;
		}

		public int getCount() {
			return friends.size();
		}

		public String getItem(int index) {
			return friends.get(index);
		}
		public void removeItem(int index) {
			friends.remove(index);
		}

		/**
		 * Automatically called by the list activity when populating the list with historyevents.
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater vi = (LayoutInflater)this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = vi.inflate(R.layout.friend_row, parent, false);
			}
			
			String friend = friends.get(position);
			if(friend != null){
				TextView fn = (TextView) row.findViewById(R.id.friend_name);
				TextView fs = (TextView) row.findViewById(R.id.friend_status);
				if(fn != null){
					fn.setText(friend);
				}
				if(fs != null){
					fs.setText("Offline");
				}
			}
			return row;
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}