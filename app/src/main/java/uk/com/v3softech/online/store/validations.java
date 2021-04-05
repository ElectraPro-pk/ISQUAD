package uk.com.v3softech.online.store;

import android.text.TextUtils;

public class validations {
    public validations(){
        
    }
    public boolean validate(String valueToCheck){
        if(!TextUtils.isEmpty(valueToCheck)){
            if(valueToCheck.trim().length() > 3){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
}
