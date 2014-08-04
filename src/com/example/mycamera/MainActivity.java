package com.example.mycamera;

import java.util.Iterator;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.CameraInfo;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public final static String TAG = "com.example.mycamera.SUSHMA";
	
	public static Parameters mCameraParameters; 
	public static Camera mCamera;
    private int cameraId = 0;
	  
	int cameraID = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
      cameraId = findFrontFacingCamera();
      if (cameraId < 0) {
        Toast.makeText(this, "No front facing camera found.",
            Toast.LENGTH_LONG).show();
      } else {
        mCamera = Camera.open(cameraId);
        Log.e(TAG, "Camera Opened");
      }
     
     
     mCameraParameters = mCamera.getParameters();
     String colorEffect = mCameraParameters.getColorEffect();
     Log.e(TAG, "Color Effect "+ colorEffect);
     
     List<int[]> supportedPreviewFps = mCameraParameters.getSupportedPreviewFpsRange();
     Iterator<int[]> supportedPreviewFpsIterator=supportedPreviewFps.iterator();
     
     while(supportedPreviewFpsIterator.hasNext()){
         int[] tmpRate=supportedPreviewFpsIterator.next();
         StringBuffer sb=new StringBuffer();
         sb.append("supportedPreviewRate: ");
         for(int i=tmpRate.length,j=0;j<i;j++){
             sb.append(tmpRate[j]+", ");
         }
         Log.e("CameraTest",sb.toString());
     }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	

	  private int findFrontFacingCamera() {
	    int cameraId = -1;
	    // Search for the front facing camera
	    int numberOfCameras = Camera.getNumberOfCameras();
		Log.e(TAG, "Number of Cameras " + numberOfCameras);
		
	    for (int i = 0; i < numberOfCameras; i++) {
	      CameraInfo info = new CameraInfo();
	      Camera.getCameraInfo(i, info);
	      if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
	        Log.d(TAG, "Camera found");
	        cameraId = i;
	        Log.e(TAG, "CameraId " + cameraId);
	        break;
	      }
	    }
	    return cameraId;
	  }

}
