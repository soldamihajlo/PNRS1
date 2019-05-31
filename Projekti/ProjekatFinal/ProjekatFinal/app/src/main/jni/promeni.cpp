//
// Created by Korisnik on 31.5.2019..
//
#include "promeni.h"

JNIEXPORT jdouble JNICALL Java_com_example_projekat10_MyNDK_promeni
                (JNIEnv * env, jobject obj, jdouble tempValue, jboolean farah){
    jdouble result;

    if(!farah){
        result=((tempValue-32)/1.8);
    }else{
        result=(tempValue*1.8+32);
    }
    return result;
}
