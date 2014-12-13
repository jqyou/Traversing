package com.example.traversing;
import android.app.Application;  

public class NameStore extends Application{
	private String username="";  
    
    public String getText(){  
        return this.username;  
    }  
    public void setText(String username){  
        this.username= username;  
    }  
    
}
