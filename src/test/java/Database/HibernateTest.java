package Database;

import Entity.Chat;
import Entity.ChatType;
import Entity.Subscription;
import Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class HibernateTest {

    private SessionFactory sessionFactory;

    @BeforeEach
    public void setUp(){
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    @Test
    public void save_user_to_database(){
        User user = new User("hossein2frs090", "092319349823", "hossein", "1234");

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(user);

            session.getTransaction().commit();
        }
    }

    @Test
    public void save_chat_to_database(){
        Chat chat = new Chat(ChatType.pv, "");

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(chat);

            session.getTransaction().commit();
        }

    }

    @Test
    public void save_subscription_to_database(){
        Subscription subscription = new Subscription();

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(subscription);

            session.getTransaction().commit();
        }
    }

    @AfterEach
    protected void tearDown(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }
}
