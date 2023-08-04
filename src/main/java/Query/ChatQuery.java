package Query;

import Entity.Chat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ChatQuery {

    public static void addChat(SessionFactory sessionFactory, Chat chat){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            session.persist(chat);

            session.getTransaction().commit();
        }
    }

    public static void deleteChatById(SessionFactory sessionFactory, Long chatId){
        Chat chat = null;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            chat = session.load(Chat.class, chatId);

            session.remove(chat);

            session.getTransaction().commit();
        }
    }
}
