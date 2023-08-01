SELECT n1.chat_id, n1.user_id, subscriptions.user_id
FROM (SELECT *
      FROM subscriptions
      WHERE user_id = 10
     ) n1
JOIN subscriptions
    ON (n1.chat_id = subscriptions.chat_id)
WHERE subscriptions.user_id != n1.user_id;

