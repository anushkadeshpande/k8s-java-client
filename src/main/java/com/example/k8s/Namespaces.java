package com.example.k8s;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class Namespaces {
  public static void main(String[] args) {
    System.setProperty("kubeconfig", "D:\\Learning\\k8s-fabric8\\kubeconfig");

    try (KubernetesClient client = new DefaultKubernetesClient()) {
      System.out.println("Creating a new ns");
      Namespace ns = new NamespaceBuilder().withNewMetadata().withName("test-ns").endMetadata().build();
      client.namespaces().resource(ns).create();
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}
