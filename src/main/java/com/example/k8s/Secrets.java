package com.example.k8s;

import java.util.HashMap;
import java.util.Map;

import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.api.model.SecretBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class Secrets {
  public static void main(String[] args) {
    String NAMESPACE = "test-ns";
    String SECRET = "test-secret";

    try (KubernetesClient client = new DefaultKubernetesClient()) {
      // Map<String, String> data = new HashMap<>();
      // data.put("pass", "YWFh");
            
      final Secret secret = new SecretBuilder()
          .withNewMetadata().withName(SECRET).endMetadata()
          .withType("Opaque")
          .withImmutable(false)
          .addToStringData("key", "password")
          .build();

      // !NOTE: If you want to store the value as it is (i.e avoid base64 encoding), add it like this:
      // .addToData("pass", "welcome1")

      Secret secretCreated = client.secrets().inNamespace(NAMESPACE).resource(secret).create();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }  
}
