package com.myretail.config

class CassandraConfig {
  String user
  String password

  String clustername

  String keyspace

  List<String> seeds

  void setSeeds(String seedString) {
    seeds = seedString.split(',')?.toList()
  }

}
