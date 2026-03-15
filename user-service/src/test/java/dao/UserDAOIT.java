//package dao;
//
//import model.User;
//import org.hibernate.cfg.Configuration;
//import org.junit.jupiter.api.*;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import util.HibernateUtil;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Testcontainers
//class UserDAOIT { // IT - Integration Test
//
//    @Container
//    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
//            .withDatabaseName("testdb")
//            .withUsername("test")
//            .withPassword("test");
//
//    private static UserDAO userDAO;
//
//    @BeforeAll
//    static void setup() {
//
//        Configuration configuration = new Configuration().configure();
//        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
//        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
//        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
//        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//
//
//        userDAO = new UserDAO();
//    }
//
//    @Test
//    void testSaveAndFindUser() {
//        User user = new User("John", "john@test.com", 20);
//        userDAO.save(user);
//
//        User found = userDAO.findById(user.getId());
//
//        assertNotNull(found);
//        assertEquals("John", found.getName());
//    }
//}
