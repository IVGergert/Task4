package com.gergert.task4.model.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONFIG_FILE = "database.properties";

    private BlockingQueue<Connection> freeConnections;
    private BlockingQueue<Connection> givenAwayConnections;

    private String url;
    private String user;
    private String password;
    private String driver;
    private int poolSize;

    private static final class Holder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    private ConnectionPool() {
        loadProperties();
        initPool();
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Error getting connection from pool", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            givenAwayConnections.remove(connection);
            try {
                freeConnections.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread interrupted while releasing connection", e);
            }
        }
    }

    public void destroyPool() {
        while (!freeConnections.isEmpty()) {
            try {
                Connection conn = freeConnections.poll();
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error("Error closing connection", e);
            }
        }
        logger.info("Connection Pool destroyed");
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = ConnectionPool.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            properties.load(inputStream);
            this.url = properties.getProperty("db.url");
            this.user = properties.getProperty("db.user");
            this.password = properties.getProperty("db.password");
            this.driver = properties.getProperty("db.driver");
            this.poolSize = Integer.parseInt(properties.getProperty("db.pool.size"));
        } catch (IOException e) {
            logger.fatal("Error loading database.properties", e);
            throw new RuntimeException("database.properties not found", e);
        }
    }

    private void initPool() {
        try {
            Class.forName(driver);
            freeConnections = new ArrayBlockingQueue<>(poolSize);
            givenAwayConnections = new ArrayBlockingQueue<>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnections.offer(connection);
            }
            logger.info("Connection Pool initialized with {} connections", poolSize);
        } catch (ClassNotFoundException | SQLException e) {
            logger.fatal("Error initializing connection pool", e);
            throw new RuntimeException("DB Connection failed", e);
        }
    }
}
