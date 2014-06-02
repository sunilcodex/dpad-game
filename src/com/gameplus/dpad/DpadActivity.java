package com.gameplus.dpad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
//import android.view.Menu;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class DpadActivity extends Activity {
	private GameView view;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	 requestWindowFeature(Window.FEATURE_NO_TITLE);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	view = new GameView(this);
        super.onCreate(savedInstanceState);
        setContentView(view);
    }
    
    @Override
    protected void onResume() {
    	view = new GameView(this);
    	setContentView(view);
    	super.onResume();
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
    	final int action = e.getAction();
        switch (action & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: {
            	view.setMouseLocation(e.getX()-37,e.getY()-107);
                break;
            }

            case MotionEvent.ACTION_MOVE:{
                view.drag(e.getX()-37,e.getY()-107);
            	break;
            }
            case MotionEvent.ACTION_UP:{
            	view.dragStop(e.getX()-37,e.getY()-107);
            	break;
            }
        }
    	return super.onTouchEvent(e);
    }
    
    @Override
    protected void onDestroy() {
    	view.destroy();
    	super.onDestroy();
    }
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	Log.e("dpad", keyCode+"  test");
    	return super.onKeyDown(keyCode, event);
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_options_menu, menu);
        
        Intent intent = new Intent(null, getIntent().getData());
        intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
        menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0,
                new ComponentName(this, DpadActivity.class), null, intent, 0, null);

        return super.onCreateOptionsMenu(menu);
    }
    
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Log.e("dpad", item.getTitle().toString());
        switch (item.getItemId()) {
        case R.id.menu_save:
        	File direct = new File(Environment.getExternalStorageDirectory() + "/dpadSource");
        	if(!direct.exists())
        	 {
        	     direct.mkdir();
        	AssetManager assetManager = getAssets();
            String[] files = null;
            try {
                files = assetManager.list("");
            } catch (IOException e) {
                Log.e("tag", e.getMessage());
            }
            for(String filename : files) {
                InputStream in = null;
                OutputStream out = null;
                try {
                  in = assetManager.open(filename);
                  out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/dpadSource/" + filename);
                  copyFile(in, out);
                  in.close();
                  in = null;
                  out.flush();
                  out.close();
                  out = null;

         		 Context context = getApplicationContext();
         		 CharSequence text = "saved all files in dpadSource on you SDcard";
         		 int duration = Toast.LENGTH_SHORT;

         		 Toast toast = Toast.makeText(context, text, duration);
         		 toast.show();
                } catch(Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        	 }else{
        		 Context context = getApplicationContext();
        		 CharSequence text = "dpadSource folder already exist on you SDcard";
        		 int duration = Toast.LENGTH_SHORT;

        		 Toast toast = Toast.makeText(context, text, duration);
        		 toast.show();
        	 }
            break;
//        case R.id.menu_dpadactivity:
//        	inputStream = getResources().openRawResource(R.raw.dpadactivity);
//            break;
//        case R.id.menu_gameloopthread:
//        	inputStream = getResources().openRawResource(R.raw.gameloopthread);
//            break;
//        case R.id.menu_gameview:
//        	inputStream = getResources().openRawResource(R.raw.gameview);
//            break;
//        case R.id.menu_vector2d:
//        	inputStream = getResources().openRawResource(R.raw.vector2d);
//            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        
        

/*    	InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return false;
        }
        text.toString();


Intent intent = new Intent(Intent.ACTION_CHOOSER);
Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
    + "/myFolder/");
intent.setDataAndType(uri, "text/csv");
startActivity(Intent.createChooser(intent, "Open folder"));
*/


        return true;
    }
}
