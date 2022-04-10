/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

/**
 *
 * @author Illuminate
 */
public class Helper {
    private static boolean isStringLowerCase(String str){
        char[] charArray = str.toCharArray();
        
        for(int i=0; i < charArray.length; i++){
            if( !Character.isLowerCase( charArray[i] ))
                return false;
        }
        
        return true;
    }
}
