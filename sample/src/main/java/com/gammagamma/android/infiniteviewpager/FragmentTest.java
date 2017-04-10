package com.gammagamma.android.infiniteviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentTest extends Fragment {
    
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,
                              @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState ) {
        
        ViewGroup rootView = (ViewGroup) inflater.inflate(
            R.layout.fragment_test, container, false );
        
        TextView textTitle = (TextView) rootView.findViewById( R.id.textTest );
        
        Bundle args = getArguments();
        String title = "Unknown";
        
        if ( args != null )
            title = args.getString( "key_photo_url" );
        
        textTitle.setText( title );
        
        return rootView;
        
    }
    
}
