FROM maven AS build
WORKDIR /usr/src/app
COPY . .
RUN mvn package

FROM quay.io/wildfly/wildfly
COPY --from=build /usr/src/app/target/*.war /opt/jboss/wildfly/standalone/deployments/