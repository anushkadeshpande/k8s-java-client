package com.example.k8s;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.LabelSelectorBuilder;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Patch;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class Namespaces {
  public static void main(String[] args) {
    // System.setProperty("kubeconfig", "D:\\Learning\\k8s-fabric8\\kubeconfig");
    System.setProperty(io.fabric8.kubernetes.client.Config.KUBERNETES_AUTH_TRYSERVICEACCOUNT_SYSTEM_PROPERTY, "true");

    String namespace = "test-ns";

    try (KubernetesClient client = new DefaultKubernetesClient()) {

      // List all namespaces
      listNamespaces(client);

      // Create a new namespace
      createNamespace(client, namespace);
      // listNamespaces(client);

      // get a specific namespace
      getNamespace(client, namespace);

      // TODO: Update a namespace (Add a label)
      // updateNamespace(client, namespace);

      // Delete a namespace
      deleteNamespace(client, namespace);

      listNamespaces(client);

    } catch (Exception e) {
      // System.err.println(e);
      e.printStackTrace();
    }
  }

  private static void listNamespaces(KubernetesClient client) {
    NamespaceList namespaceList = client.namespaces().list();

    // Extract and print namespace names
    for (Namespace ns : namespaceList.getItems()) {
      System.out.println(ns.getMetadata().getName());
    }
  }

  public static void getNamespace(KubernetesClient client, String namespace) {
    Namespace ns = client.namespaces().withName(namespace).get();
    System.out.println(ns);
  }

  private static void createNamespace(KubernetesClient client, String namespace) {
    System.out.println("Creating a new ns");
    Namespace ns = new NamespaceBuilder().withNewMetadata().withName(namespace).endMetadata().build();
    client.namespaces().resource(ns).create();
  }

  private static void deleteNamespace(KubernetesClient client, String namespace) {
    client.namespaces().withName(namespace).delete();
    System.out.println("Namespace deleted!");
  }

  private static void updateNamespace(KubernetesClient client, String namespace) throws JsonProcessingException {
    Namespace ns = client.namespaces().withName(namespace).get();

    // Create a LabelSelector object with the new label
    // LabelSelector newLabels = new LabelSelectorBuilder().addToLabels("new-label",
    // "value").build();
    LabelSelector newLabels = new LabelSelectorBuilder().addToMatchLabels("test-label", "abc").build();

    // Patch the namespace with the new labels
    // client.namespaces().patch(namespace, new Patch(new
    // ObjectMapper().writeValueAsString(newLabels)));
    // client.namespaces().withName(namespace).patch(new
    // ObjectMapper().writeValueAsString(newLabels));

    // Create a new namespace object with updated labels
    // Namespace updatedNamespace = new NamespaceBuilder()
    // .withMetadata(ns.getMetadata()).withNewMetadata().addToLabels("new-label",
    // "value").endMetadata()
    // .build();
    ObjectMeta newMetadata = ns.getMetadata();
    // System.out.println(newMetadata);
    newMetadata.getLabels().put("new-label", "value");
    // client.namespaces().withName(namespace).update().setMetadata(newMetadata);
    // ns.edit().editMetadata().addToLabels("new-label", "value");
    ns.setMetadata(newMetadata);

    System.out.println("Updated ns");
  }
}
