package com.cg.SparkMessagingAplication.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.SparkMessagingAplication.dto.ChatHistory;
import com.cg.SparkMessagingAplication.dto.Message;


public interface ChatHistoryDao extends JpaRepository<Message, Integer> {

	@Query(nativeQuery = true, value = "select * from Message m where m.sender_id=:userid or m.receiver_id=:userid")
	public List<Message> findBySenderOrReceiverId(@Param("userid") Integer id);
	
	@Query(nativeQuery = true, value = "select chat_history_id from chat_history m where user_id=:userid")
	public Integer findChatBySenderOrReceiverId(@Param("userid") Integer id);

	/*@Query(nativeQuery=true, value="select new com.cg.SparkMessagingApplicationSpringBoot.dto.ChatHistory(c.chat_history_id,"
			+ "m.text, m.date, m.sender_id, m.receiver_id, m.chat_histoy_id_fk) from ChatHistory c,Message m ")
*/
	//	@Query("select DISTINCT m from Message m left join fetch m.ChatHistory")
//	@Query(nativeQuery=true, value="select m from Message, IN (m.chat_history) c")
//	@Query(nativeQuery=true,value="select m.* from chat_history c inner join Message m on c.chat_history_id = m.chat_histoy_id_fk")
//	@Query(nativeQuery=true,value="select * from Message m inner join chat_history c on c.chat_history_id = m.chat_histoy_id_fk")
//	@Query(nativeQuery=true,value="select m from chat_history m join fetch m.Message")
	@Query("select c from ChatHistory c join fetch c.message")
	public List<ChatHistory> findAllChatHistory();
	
	@Modifying
	@Query(nativeQuery=true,value="Insert into Message (text,date,sender_id,receiver_id,chat_histoy_id_fk) values(:text, :date,:sender_id,:reciever_id,:chat_histoy_id_fk)")
	public Integer saveMessage(@Param("text") String text, @Param("date") Timestamp date,@Param("sender_id")Integer sender_id,@Param("reciever_id")Integer reciever_id,@Param("chat_histoy_id_fk")int chat_histoy_id_fk);

}

