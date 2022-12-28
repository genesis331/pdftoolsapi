FROM quay.io/wildfly/wildfly
COPY ./target/*.war /opt/jboss/wildfly/standalone/deployments/