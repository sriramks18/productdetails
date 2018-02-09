package com.myretail.config

class CassandraConfig {
  String user
  String password

  String clustername

  String keyspace

  void setCreateKeySpaceIfNotExists(String createKeySpaceIfNotExists) {
    this.createKeySpaceIfNotExists = Boolean.parseBoolean(createKeySpaceIfNotExists)
  }
  boolean createKeySpaceIfNotExists

  List<String> seeds

  void setSeeds(String seedString) {
    seeds = seedString.split(',')?.toList()
  }

}
