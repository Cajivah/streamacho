package com.streamcho.chatdb.repository;

import com.streamcho.chatdb.model.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
     Page<ChatMessage> getByChatId(Long chatId, Pageable pageable);
}
