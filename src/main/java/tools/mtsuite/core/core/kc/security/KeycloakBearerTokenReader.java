package tools.mtsuite.core.core.kc.security;

import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tools.mtsuite.core.common.dao.IUserDao;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

@Service
public class KeycloakBearerTokenReader{

    @Autowired
    private KeycloakAuthenticationProperties properties ;

    private PublicKey publicKey;

    @Autowired
    private IUserDao userDao;

    @PostConstruct
    public void init()  {
    String c = "-----BEGIN CERTIFICATE-----\n"
               +"MIICnTCCAYUCBgFwTsFJ3TANBgkqhkiG9w0BAQsFADASMRAwDgYDVQQDDAdQcmVsaW9zMB4XDTIwMDIxNjE2MDU1NVoXDTMwMDIxNjE2MDczNVowEjEQMA4GA1UEAwwHUHJlbGlvczCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJJ5p31GbWI5kl0s8Dz9VtwMNY8fP+4j1sFO3BjFeh9bCA9bDPh2aqV50FalKcCzul/heFn1L87rjxBynSo1TlZ67T1enM8IIFssn8niXhXGoFgzvymyiR67+qLBNINWhHDAKNmaKV+sR57dmMCg6J8hJ8stslm5rkTjcY1FV55mDgyefB7+g+TO9cxGTuHDSeXRDaExvSUhOzdWitRMPtN02wDo23Q48JmmbqvxDDAZIKOFqwmsnx2gXrld1l3pqsBYq7nmbekt9CVPOABIVAxfiDLbeIGW8idKWXMSFz9FGYTnKyYjMXf1g9KTDSj4sU3FnoYrvTDrzwVs7vkjwpUCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAfF+DShTThSFZU5TuWAUg6WOp9OdfGsnUHhSgCZRyM+21gAyb/H393Cq3PCKoCPejJi3ccVuHcAKj8gKeXkXC+56kTwpKlcFAlBdhwVHVsO17g0WTUSyDmVfEbqdKweAkr1w7+7DSMVSAGPMvfNFsOMVNAkxVPEkWNqOthJBf5qaV2ZawQLCnz78y0aZxbRdbx8PK5p3HWVJ1VsHiVHCxFzU0O+RpUFcgwi1SDRQL/6Uv0YH54pHVvEYtMDrHNDjUkkVtSxPjZQ7+SNks61Oie7u89hw+bxkrSV4PjESCz3+MaQqVbWZ907DKi5pZACi2ThSzebl0Nbpflyi+IVv8NA==" + "\n"
               +"-----END CERTIFICATE-----";

        this.publicKey = SecurityUtils.tryParseX509Certificate(c);

    }
    public Authentication tokenValidation(String token) {
        try {

            @SuppressWarnings("unchecked")
            AccessToken accessToken = TokenVerifier.create(token.replace("Bearer ", "").trim(), AccessToken.class)
                    .audience(this.properties.getAudience())
                    .issuedFor(this.properties.getClient())
                    .publicKey(this.publicKey)
                    .withChecks((tok) -> tok.isActive((int)this.properties.getSkew().toSeconds()), TokenVerifier.SUBJECT_EXISTS_CHECK)
                    .verify()
                    .getToken();

          /*  if(!userDao.existsByUsername(accessToken.getPreferredUsername()))
                throw new VerificationException();
*/
            KcAuthenticationToken kcAuthenticationToken = new KcAuthenticationToken(accessToken);

            return kcAuthenticationToken;
        } catch(VerificationException ex) {
            return  new KcAuthenticationToken();
        }
    }

}
