package com.kh.lucky.websocket;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.lucky.dao.MemberDao;
import com.kh.lucky.dao.MessageDao;
import com.kh.lucky.dto.MemberDto;
import com.kh.lucky.dto.MessageDto;
import com.kh.lucky.service.JwtService;
import com.kh.lucky.vo.ChatRequestVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberChatWebSocketServer extends TextWebSocketHandler {

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MemberDao memberDao;

	private Set<WebSocketSession> users = new CopyOnWriteArraySet<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		users.add(session);

		// 회원 목록 조회 및 전송
		List<MemberDto> memberList = memberDao.selectMemberIdList();
		ObjectMapper mapper = new ObjectMapper();
		String jsonMemberList = mapper.writeValueAsString(memberList);
		TextMessage memberListMessage = new TextMessage(jsonMemberList);
		session.sendMessage(memberListMessage);

		// 이전 메시지 조회 및 전송
		List<MessageDto> messageList = messageDao.selectList();
		for (MessageDto messageDto : messageList) {
			String json = mapper.writeValueAsString(messageDto);
			TextMessage message = new TextMessage(json);
			session.sendMessage(message);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		users.remove(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ChatRequestVO requestVO = mapper.readValue(message.getPayload(), ChatRequestVO.class);
		String token = requestVO.getToken();
		String memberId = jwtService.parse(token).getMemberId();
		String memberLevel = jwtService.parse(token).getMemberLevel();

		// 관리자면 특정 회원에게 메세지 전송
		if ("관리자".equals(memberLevel)) {
			String receiverId = requestVO.getReceiverId();

			MessageDto messageDto = messageDao.insert(MessageDto.builder().messageSender(memberId)
					.messageReceiver(receiverId).messageContent(requestVO.getContent())
					.messageSenderLevel(memberLevel).messageReceiverLevel("일반회원")
					.build());

			String json = mapper.writeValueAsString(messageDto);
			TextMessage response = new TextMessage(json);
			session.sendMessage(response);

		}
		// 관리자가 아니면 문의 전용 관리자에게 메세지 전송
		else {
			String adminUserId = "adminuser1"; // 관리자 아이디 (DB에만 저장되는 값 / 그냥 관리자 한테 보냈다는 걸 알림) 

			// 메시지 생성 및 전송
			MessageDto messageDto = messageDao.insert(MessageDto.builder().messageSender(memberId)
					.messageReceiver(adminUserId).messageContent(requestVO.getContent())
					.messageSenderLevel(memberLevel).messageReceiverLevel("관리자")
					.build());

			String json = mapper.writeValueAsString(messageDto);
			TextMessage response = new TextMessage(json);
			session.sendMessage(response);
		}
	}

}

