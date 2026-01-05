module org.jboss.logmanager.slf4j {
    requires org.jboss.logmanager;
    requires org.slf4j;

    exports org.slf4j.impl to org.slf4j;

    provides org.slf4j.spi.SLF4JServiceProvider with org.slf4j.impl.JBossSlf4jServiceProvider;
}
