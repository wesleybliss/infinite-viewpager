package com.gammagamma.android.infiniteviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gammagamma.lib.infiniteviewpager.InfiniteViewPager;
import com.gammagamma.lib.infiniteviewpager.InfiniteViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        
        ArrayList<String> items = new ArrayList<>();
        items.addAll( Arrays.asList( "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" ) );
        
        InfiniteViewPager viewPager = (InfiniteViewPager) findViewById( R.id.viewPager );
        InfiniteViewPagerAdapter adapter = new InfiniteViewPagerAdapter<>(
            getSupportFragmentManager(), FragmentTest.class, "key_photo_url" );
        
        final int pagePreview = 100;
        
        viewPager.setPageTransformer( false, new CoverTransformer( 0.3f, 0f, 0f, 0f ) );
        
        viewPager.setClipToPadding( false );
        viewPager.setPadding( pagePreview, 0, pagePreview, 0 );
        viewPager.setPageMargin( 20 );
        viewPager.setAdapter( adapter );
    
        adapter.setItems( items );
        viewPager.setCurrentItem( Math.round( items.size() / 2 ) );
        
    }
    
}
