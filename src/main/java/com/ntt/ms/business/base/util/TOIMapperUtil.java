/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntt.ms.business.base.util;

import java.util.ArrayList;
import java.util.List;

/**
 * mapp entities to TOIs
 * @author UNGERW
 */
public final class TOIMapperUtil {
    
    /**
     * map list of entity objects to entity TOI objects
     * @param <T>
     * @param source
     * @return  List<T> TOIs
     */
    public static <T>List<T> mapList(List source){
        List<T> result = new ArrayList<>();
        for(Object o : source){
            result.add((T)o);
        }
        return result;
    }
    
}
