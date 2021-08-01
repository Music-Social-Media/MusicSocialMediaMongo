//package com.SocialMediaCassandra.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.cassandra.SessionFactory;
//import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
//import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
//import org.springframework.data.cassandra.config.SchemaAction;
//import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
//import org.springframework.data.cassandra.core.CassandraOperations;
//import org.springframework.data.cassandra.core.CassandraTemplate;
//import org.springframework.data.cassandra.core.convert.CassandraConverter;
//import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
//import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
//import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
//
//@Configuration
//public class CassandraConfig extends AbstractCassandraConfiguration {
//
//    @Override
//    protected String getKeyspaceName() {
//        return "SocialMedia";
//    }
//
//    public String getContactPoints() {
//        return "localhost";
//    }
//
//
//    @Override
//    protected String getLocalDataCenter() {
//        return "datacenter1";
//    }
//
//    @Bean
//    @Primary
//    public CqlSessionFactoryBean session() {
//
//        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
//        session.setContactPoints(getContactPoints());
//        session.setKeyspaceName(getKeyspaceName());
//        session.setLocalDatacenter(getLocalDataCenter());
//        return session;
//    }
//
////    @Bean
////    @Primary
////    public SessionFactoryFactoryBean sessionFactory(CqlSession session, CassandraConverter converter) {
////        SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
////        sessionFactory.setSession(session);
////        sessionFactory.setConverter(converter);
////        sessionFactory.setSchemaAction(SchemaAction.NONE);
////        return sessionFactory;
////    }
////
////    @Bean
////    @Primary
////    public CassandraMappingContext mappingContext(CqlSession cqlSession) {
////
////        CassandraMappingContext mappingContext = new CassandraMappingContext();
////        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));
////        return mappingContext;
////    }
//
//    @Bean
//    @Primary
//    public CassandraConverter converter(CassandraMappingContext mappingContext) {
//        return new MappingCassandraConverter(mappingContext);
//    }
//
//    @Bean
//    public CassandraOperations cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
//        return new CassandraTemplate(sessionFactory, converter);
//    }
//
//}

//package com.SocialMediaCassandra.config;
//
//import com.datastax.driver.core.PlainTextAuthProvider;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
//import org.springframework.data.cassandra.config.SchemaAction;
//
//import java.security.AuthProvider;
//
//public class CassandraConfig extends AbstractCassandraConfiguration {
//
//    @Value("${spring.data.cassandra.contact-points:placeholder}")
//    private String contactPoints;
//
//    @Value("${spring.data.cassandra.port:0000}")
//    private int port;
//
//    @Value("${spring.data.cassandra.keyspace:placeholder}")
//    private String keySpace;
//
//    @Value("${spring.data.cassandra.username}")
//    private String username;
//
//    @Value("${spring.data.cassandra.password}")
//    private String password;
//
//    @Value("${spring.data.cassandra.schema-action}")
//    private String schemaAction;
//
//    public void setContactPoints(String contactPoints) {
//        this.contactPoints = contactPoints;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    public void setKeySpace(String keySpace) {
//        this.keySpace = keySpace;
//    }
//
//    @Override
//    protected String getKeyspaceName() {
//        return keySpace;
//    }
//
//    @Override
//    protected String getContactPoints() {
//        return contactPoints;
//    }
//
//    @Override
//    protected int getPort() {
//        return port;
//    }
//
//    @Override
//    public SchemaAction getSchemaAction() {
//        return SchemaAction.valueOf(schemaAction);
//    }
//}

package com.SocialMediaCassandra.config;

import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import java.util.Arrays;
import java.util.List;

public class CassandraConfig extends AbstractCassandraConfiguration {
    public static final String KEYSPACE = "test2";

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(KEYSPACE).with(KeyspaceOption.DURABLE_WRITES, true);
        return Arrays.asList(specification);
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(KEYSPACE));
    }

    @Override
    protected String getKeyspaceName() {
        return null;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.SocialMediaCassandra.model"};
    }
}