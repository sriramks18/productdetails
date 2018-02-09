package com.myretail.service

import com.datastax.driver.core.*
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy
import com.datastax.driver.core.policies.PercentileSpeculativeExecutionPolicy
import com.datastax.driver.core.policies.TokenAwarePolicy
import com.google.inject.Inject
import com.myretail.config.CassandraConfig
import groovy.util.logging.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.service.Service
import ratpack.service.StartEvent
import ratpack.service.StopEvent

@Slf4j
class CassandraService implements Service {

  private Cluster cluster
  private Session session
  private final String[] cipherSuites = ["TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA"]
  private final CassandraConfig cassandraConfig

  private Logger logger = LoggerFactory.getLogger(CassandraService.class)

  @Inject
  CassandraService(CassandraConfig cassandraConfig) {
    this.cassandraConfig = cassandraConfig
  }

  private void connect() {
    PerHostPercentileTracker tracker = PerHostPercentileTracker.builder(SocketOptions.DEFAULT_READ_TIMEOUT_MILLIS + 500).build()

    DCAwareRoundRobinPolicy dcAwareRoundRobinPolicy = DCAwareRoundRobinPolicy.builder().withUsedHostsPerRemoteDc(1).build()

    Cluster.Builder builder = Cluster.builder()
        .withLoadBalancingPolicy(new TokenAwarePolicy(dcAwareRoundRobinPolicy))
        .withSpeculativeExecutionPolicy(new PercentileSpeculativeExecutionPolicy(tracker, 0.99, 3))

    if(cassandraConfig.clustername) {
      builder.withClusterName(cassandraConfig.clustername)
    }

    for (String seed : cassandraConfig.seeds) {
      if (seed.contains(":")) {
        String[] tokens = seed.split(":")
        builder.addContactPoint(tokens[0]).withPort(Integer.parseInt(tokens[1]))
      } else {
        builder.addContactPoint(seed)
      }
    }

    if (cassandraConfig.user != null) {
      builder.withCredentials(cassandraConfig.user, cassandraConfig.password)
    }

    cluster = builder.build()

    if (cassandraConfig.keyspace != null) {
      session = cluster.connect(cassandraConfig.keyspace)
    } else {
      session = cluster.connect()
    }
  }

  @Override
  void onStart(StartEvent event) throws Exception {
    connect()
  }

  @Override
  void onStop(StopEvent event) throws Exception {
    session.closeAsync()
  }

  Session getSession() {
    return this.session
  }

  Cluster getCluster() {
    return cluster
  }

}