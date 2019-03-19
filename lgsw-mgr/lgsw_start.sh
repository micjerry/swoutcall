#!/bin/bash

export JAVA_HOME="/opt/jdks/jdk1.8.0_121/"
export SPRING_PROFILES_ACTIVE="production"
export RABBITMQ_CONF_USERNAME="mhconf"
export RABBITMQ_CONF_PASSWORD="mhearts2015"
export RABBITMQ_SYS_PORT=5672
export RABBITMQ_CONF_CCVHOST="conf"
export RABBITMQ_CONF_FSCMDVHOST="fscmd"
export RABBITMQ_CONF_FSNTYVHOST="fsnty"
export RABBITMQ_CONF_CCEX="confex"
export RABBITMQ_CONF_FSCMDEX="fscmdex"
export RABBITMQ_CONF_FSNTYEX="fsntyex"

./camel-example-spring-boot-1.0.0.jar

