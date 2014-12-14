package com.example.traversing;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AboutUs extends Activity{
	//定义Menu菜单选项的ItemId  
    final static int ONE = Menu.FIRST;  
    final static int TWO = Menu.FIRST+1;  
    final static int THREE = Menu.FIRST+2;  
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		MyApplication.getInstance().addActivity(this);
}
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
    	menu.add(0, ONE, 0, "Home Page");
        menu.add(0, TWO, 1, "About Us");  
        menu.add(0, THREE, 2, "Exit");  
        return super.onCreateOptionsMenu(menu); 
    
    }

    
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	 switch(item.getItemId()){  
         case 1:  
        	 Intent homepage = new Intent(AboutUs.this,
						Homepage.class);
				AboutUs.this.startActivity(homepage); 
             break;  
         case 2:  
        	
             break;  
         case 3:  
        	 AboutUs.this.exitDialog(); 
             break;
         }  
        return super.onOptionsItemSelected(item);
    }
    
public void exitDialog() {
	Dialog dialog = new AlertDialog.Builder(AboutUs.this)
			.setTitle("Leave?").setMessage("Are You Sure to Exit?")
			.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// 确定的话就表示退出，此时我们结束我们程序
					// 使用我们Activity提供的finish方法
					MyApplication.getInstance().exit();
				//	Homepage.this.finish();// 操作结束
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			}).create();
	dialog.show();
}
}
