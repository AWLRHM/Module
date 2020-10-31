package ir.awlrhm.modules.security;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class PeerCertificateExtractor {

    public static String extract(File certificate){

        FileInputStream inputStream = null;

        try{
            inputStream = new FileInputStream(certificate);
            X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X509")
                    .generateCertificate(inputStream);

            byte[] publicKeyEncoded = x509Certificate.getPublicKey().getEncoded();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] publicKeySha256 = messageDigest.digest(publicKeyEncoded);
            return "sha256/" + Base64.encodeToString(publicKeySha256, Base64.DEFAULT);

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }
}
