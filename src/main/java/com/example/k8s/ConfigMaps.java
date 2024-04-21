package com.example.k8s;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.Resource;

public class ConfigMaps {

  public static void main(String[] args) {
    String namespace = "test-ns";
    String cm_name = "test-cm";

    try (KubernetesClient client = new DefaultKubernetesClient()) {
      Resource<ConfigMap> configMapResource = client.configMaps().inNamespace(namespace)
          .resource(new ConfigMapBuilder().withNewMetadata().withName(cm_name)
              .endMetadata().addToData("data1", "test").addToData("data2", "test2")
              .build());

      ConfigMap configMap = configMapResource.createOrReplace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}