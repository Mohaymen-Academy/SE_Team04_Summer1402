package Query;

import Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;

public class UserQuery {

    public static void addUser(SessionFactory sessionFactory, User user){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(user);

            session.getTransaction().commit();
        }
    }

    public static void deleteUserById(SessionFactory sessionFactory, Long userId){
        User user = null;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            user = session.get(User.class, userId);

            session.remove(user);

            session.getTransaction().commit();
        }
    }

    public static void changeBioById(SessionFactory sessionFactory, Long userId, String newBio){
        User user = null;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            user = session.get(User.class, userId);
            user.setBio(newBio);

            session.getTransaction().commit();
        }
    }

    public static ArrayList<Long> getRelationsById(SessionFactory sessionFactory, Long userId){
        ArrayList<Long> result = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            String queryString = "select sub1.userId " +
                                    "from Subscription sub1 " +
                                    "where sub1.userId != ?1 and" +
                                    " sub1.chatId in " +
                                                    "(select sub2.chatId " +
                                                    "from Subscription sub2 " +
                                                    "where sub2.userId = ?2)";
            List<Long> relations = session.createQuery(queryString , Long.class).setParameter(1, userId).setParameter(2, userId).getResultList();
            result = new ArrayList<>(relations);
            session.getTransaction().commit();
        }
        return result;
    }
}
