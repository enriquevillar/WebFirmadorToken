package cl.vo;

import lombok.Data;

import java.security.PrivateKey;
import java.security.cert.Certificate;

@Data
public class CertificadoVO {
    private String alias;
    Certificate[] chain;
    private PrivateKey privateKey;
}
