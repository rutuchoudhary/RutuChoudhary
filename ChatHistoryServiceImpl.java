package com.cg.SparkMessagingAplication.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.SparkMessagingAplication.dao.ChatHistoryDao;
import com.cg.SparkMessagingAplication.dto.ChatHistory;
import com.cg.SparkMessagingAplication.dto.Message;
import com.cg.SparkMessagingAplication.exception.UserException;

@Service
@Transactional
public class ChatHistoryServiceImpl implements ChatHistoryService {

	@Autowired
	ChatHistoryDao dao;
	static final Logger logger = Logger.getLogger(ChatHistoryServiceImpl.class);

	@SuppressWarnings("unused")
	@Override
	public Message addMessage(Message message) {
		PropertyConfigurator
				.configure("D:\\Aug01\\SparkMessagingAplication\\src\\main\\java\\resources\\log4j.properties");
		List<Message> msgList = dao.findBySenderOrReceiverId(message.getSender().getId());
		List<Message> msgListReceiver = dao.findBySenderOrReceiverId(message.getReceiver().getId());
		Integer chathistoryid = dao.findChatBySenderOrReceiverId(message.getSender().getId());
		if (chathistoryid != null  && msgList != null) {

//			System.out.println(message.getChathistory().getId());

			Integer msg=dao.saveMessage(message.getText(), message.getDate(), message.getSender().getId(),
					message.getReceiver().getId(), chathistoryid);
			
			logger.info("Message added successfully");
			return null;
		} else if (chathistoryid != null && msgListReceiver != null) {

			// System.out.println(message.getChathistory().getId());
			
			Integer msg= dao.saveMessage(message.getText(), message.getDate(), message.getSender().getId(),
					message.getReceiver().getId(), chathistoryid);
			// System.out.println(num);
			logger.info("Message added successfully");
			return null;
		}

		else {
			Message msg = dao.save(message);
			logger.info("Message added successfully");
			return msg;
		}
	}

	@Override
	public List<Message> searchBySenderOrReceiverId(Integer id) {
		PropertyConfigurator
				.configure("D:\\Aug01\\SparkMessagingAplication\\src\\main\\java\\resources\\log4j.properties");
		List<Message> msgList = dao.findBySenderOrReceiverId(id);
		if (msgList.isEmpty()) {
			throw new UserException("There are no messages against entered id because no user with this id");
		}
		logger.info("Message searched successfully");
		return msgList;
	}

	@Override
	public List<ChatHistory> showAllChatHistory() {
		PropertyConfigurator
				.configure("D:\\Aug01\\SparkMessagingAplication\\src\\main\\java\\resources\\log4j.properties");
		List<ChatHistory> chathisList = dao.findAllChatHistory();
		if (chathisList.isEmpty()) {
			throw new UserException("No Conversation happened yet");
		}
		logger.info("This is the Conversation happened and stored yet");
		return chathisList;
	}

}
