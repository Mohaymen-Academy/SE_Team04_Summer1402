package Query;

import Entity.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class MessageQuery {

    public static void addMessage(SessionFactory sessionFactory, Message message){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(message);

            session.getTransaction().commit();
        }
    }

    public static void deleteMessage(SessionFactory sessionFactory, Long message_id){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            Message message = session.get(Message.class, message_id);

            session.remove(message);

            session.getTransaction().commit();
        }
    }

    public static Message getMessageById(SessionFactory sessionFactory, Long message_id){
        Message message = null;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            message = session.get(Message.class, message_id);
            session.getTransaction().commit();
        }

        return message;
    }

    public static void editMessage(SessionFactory sessionFactory, Long message_id, String context){
        try(Session session = sessionFactory.openSession()){
            Transaction tx=session.beginTransaction();
            Query<Message> q = session.createNativeQuery("UPDATE messages SET context=:n WHERE message_id=:i", Message.class);
            q.setParameter("n",context);
            q.setParameter("i",message_id);
            int status=q.executeUpdate();
            tx.commit();
        }
    }

    public static List<Message> getAllMessageOfUser(SessionFactory sessionFactory, Long user_id){
        try(Session session = sessionFactory.openSession()){
            Query q=session.createNativeQuery("SELECT * FROM messages WHERE user_id=:i", Message.class);
            q.setParameter("i",user_id);
            List<Message> list=q.list();
            return list;
        }
    }

    public static Long getNumberOfAllMessageOfUser(SessionFactory sessionFactory, Long user_id){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            List<Long> result = session.createQuery("select count(m.id) from Message m where m.userId = ?1 group by userId", Long.class).setParameter(1, user_id).getResultList();
            session.getTransaction().commit();

            return result.get(0);
        }
    }

    public static int getNumberOfViewOfMessage(SessionFactory sessionFactory, Long message_id){
        try(Session session = sessionFactory.openSession()){
            Query q=session.createNativeQuery("SELECT view_count FROM messages WHERE message_id=:i", Message.class);
            q.setParameter("i",message_id);
            Message message = getMessageById(sessionFactory, message_id);
            return message.getViewCount();
        }
    }
}
