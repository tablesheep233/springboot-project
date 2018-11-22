package org.table.neweims.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class Encryption {

    private static String hashAlgorithmName = "MD5";

    private static int hashIterations = 2333;

    public static String getEncryption(Object credentials,Object salt){
        salt = ByteSource.Util.bytes(salt);
        Object result =  new SimpleHash(hashAlgorithmName,credentials,salt,hashIterations);
        return result.toString();
    }

}
