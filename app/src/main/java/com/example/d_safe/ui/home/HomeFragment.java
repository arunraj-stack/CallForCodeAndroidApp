package com.example.d_safe.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.d_safe.MainActivity;
import com.example.d_safe.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    ListView simpleList;
    String countryList[] = {"Don's Marriage", "Litty's Birthday Party", "Richy's Wedding Anniversary", "Sunday Prayer", "Jack's Marriage", "Owen's Marriage"};
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ListView listView = (ListView)root.findViewById(R.id.simpleListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                countryList);
        listView.setAdapter(adapter);
        // Set an item click listener for ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);

                // Display the selected item text on TextView
                // tv.setText("Your favorite : " + selectedItem);
                ((MainActivity)getActivity()).checkForCheckin(selectedItem);
            }
        });
        return root;
    }


}

//public class EventAdapter extends ArrayAdapter<Invitation> {
//    public EventAdapter(Context context, ArrayList<Invitation> users) {
//        super(context, 0, users);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        Invitation user = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_inviation, parent, false);
//        }
//        // Lookup view for data population
//        TextView eventName = (TextView) convertView.findViewById(R.id.eventName);
//        TextView eventDuration = (TextView) convertView.findViewById(R.id.eventDuration);
//        // Populate the data into the template view using the data object
//        eventName.setText(user.name);
//        eventDuration.setText(user.hometown);
//        // Return the completed view to render on screen
//        return convertView;
//    }
//}

