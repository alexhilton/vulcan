package com.hilton.vulcan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentTab extends Fragment {

    public static final String EXTRA_TITLE = "title";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tab, container, false);
        Bundle args = getArguments();
        TextView tv = (TextView) root.findViewById(R.id.title);
        tv.setText(args.getString(EXTRA_TITLE));
        TextView mv = (TextView) root.findViewById(R.id.menu);
        mv.setText("Edit");
        return root;
    }

}
