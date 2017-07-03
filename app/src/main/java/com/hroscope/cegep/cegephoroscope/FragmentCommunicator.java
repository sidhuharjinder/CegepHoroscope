package com.hroscope.cegep.cegephoroscope;

import android.net.Uri;

/**
 * Created by SACHIN on 6/28/2017.
 */

public interface FragmentCommunicator{
    public void passDataToFragment(Uri image, String name, String email);
}
