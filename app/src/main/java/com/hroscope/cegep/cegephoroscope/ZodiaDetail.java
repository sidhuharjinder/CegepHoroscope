package com.hroscope.cegep.cegephoroscope;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ZodiaDetail extends Activity {

    PagerContainer mContainer;
    ViewPager pager;
    PagerAdapter adapter;
    int[] mResources = {
            R.layout.today,
            R.layout.tomorrow,
            R.layout.today,
            R.layout.tomorrow,

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zodia_detail);
        initializeviews();

    }

    private void initializeviews() {

        mContainer = (PagerContainer) findViewById(R.id.pager_container);
        pager = mContainer.getViewPager();
        adapter = new MyPagerAdapter(this);
        pager.setAdapter(adapter);
        //Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        pager.setOffscreenPageLimit(adapter.getCount());
        //A little space between pages
        pager.setPageMargin(50);
        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        pager.setClipChildren(false);
    }

//Nothing special about this adapter, just throwing up colored views for demo

    private  class MyPagerAdapter extends  PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public MyPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View today = mLayoutInflater.inflate(R.layout.today, container, false);
            View tomorrow = mLayoutInflater.inflate(R.layout.tomorrow, container, false);
            View viewrrr [] ={today,tomorrow,today,tomorrow};
//            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            imageView.setImageResource(mResources[position]);
//
            container.addView(viewrrr[position]);

            return viewrrr[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
