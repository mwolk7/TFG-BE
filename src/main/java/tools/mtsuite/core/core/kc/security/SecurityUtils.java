package tools.mtsuite.core.core.kc.security;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SecurityUtils {
    //Generates the certificate object to verify token signature
    public static PublicKey parseX509Certificate(final String certificateString) throws CertificateException {
        CertificateFactory f = CertificateFactory.getInstance("X.509");
        final X509Certificate certificate = (X509Certificate) f.generateCertificate(new ByteArrayInputStream(certificateString.getBytes(StandardCharsets.UTF_8)));
        return certificate.getPublicKey();
    }

    public static PublicKey tryParseX509Certificate(final String certificateString) {
        try{
            return parseX509Certificate(certificateString);
        }catch (Exception e){
            return null;
        }


    }
}
