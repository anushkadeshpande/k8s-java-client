package com.example.k8s;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class Pods {
  public static void main(String[] args) {

    String namespace = "test-ns";
    String podName = "test-pod";

    String image = "nginx:latest";
    String containerName = "nginx-container";

    System.setProperty(io.fabric8.kubernetes.client.Config.KUBERNETES_AUTH_TRYSERVICEACCOUNT_SYSTEM_PROPERTY, "true");

    try (KubernetesClient client = new DefaultKubernetesClient()) {
      // List all pods in test-ns
      listPods(client, namespace);

      // Create a new pod
      createPod(client, containerName, image, podName, namespace);
      listPods(client, namespace);

      // Delete newly created pod
      deletePod(client, namespace, podName);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void listPods(KubernetesClient client, String namespace) {
    client.pods().inNamespace(namespace).list().getItems().forEach(pod -> {
      System.out.println(pod.getMetadata().getName());
    });
  }

  private static void createPod(KubernetesClient client, String containerName, String image, String podName,
      String namespace) {
    Container container = new ContainerBuilder()
        .withName(containerName)
        .withImage(image)
        .build();

    // Create a new pod in test ns
    Pod pod = new PodBuilder()
        .withNewMetadata()
        .withName(podName)
        .addToLabels("test-label", "abc")
        .endMetadata()
        .withNewSpec()
        .addToContainers(container)
        .endSpec()
        .build();

    client.pods().inNamespace(namespace).create(pod);
  }

  private static void deletePod(KubernetesClient client, String namespace, String podName) {
    client.pods().inNamespace(namespace).withName(podName).delete();
  }
}
