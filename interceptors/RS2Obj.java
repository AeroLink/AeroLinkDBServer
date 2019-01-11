/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interceptors;

import Synapse.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;

/**
 *
 * @author Lei
 */
public class RS2Obj {
    
    public static JSONArray convert(ResultSet rs){
        
        JSONArray list = new JSONArray();
        
        try {
        
            while(rs.next()){
  
                    HashMap row = new HashMap(rs.getMetaData().getColumnCount());
                    for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++ ){
                        //System.err.println(rs.getObject(i).toString());
                        row.put(rs.getMetaData().getColumnName(i), rs.getObject(i).toString());
                    }
                    list.add(row);

            }
            
        } catch (SQLException | NullPointerException ex ) {
            System.out.println(ex.getMessage());
        }
        return list;

    }
}