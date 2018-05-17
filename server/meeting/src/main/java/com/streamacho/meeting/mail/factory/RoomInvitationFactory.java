package com.streamacho.meeting.mail.factory;

import com.streamacho.api.mail.config.properties.MailExtendedProperties;
import com.streamacho.api.mail.utils.LocalizedMessageSource;
import com.streamacho.api.mail.utils.MultipartMimeMessageBuilder;
import com.streamacho.meeting.config.properties.RoutingProperties;
import com.streamacho.meeting.room.model.dto.RoomInvitationMailDTO;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

import static com.streamacho.meeting.mail.util.RoomInvitationMessageKeys.ACTION_URL_VARIABLE;
import static com.streamacho.meeting.mail.util.RoomInvitationMessageKeys.MAIN_MESSAGE_TEMPLATE;
import static com.streamacho.meeting.mail.util.RoomInvitationMessageKeys.MAIN_MESSAGE_VARIABLE;
import static com.streamacho.meeting.mail.util.RoomInvitationMessageKeys.STARTING_GREETER_TEMPLATE;
import static com.streamacho.meeting.mail.util.RoomInvitationMessageKeys.STARTING_GREETER_VARIABLE;
import static com.streamacho.meeting.mail.util.RoomInvitationMessageKeys.TEMPLATE_NAME;
import static com.streamacho.meeting.mail.util.RoomInvitationMessageKeys.TITLE;
import static java.util.Map.entry;

@Component
@AllArgsConstructor
@EnableConfigurationProperties({MailExtendedProperties.class, RoutingProperties.class})
public class RoomInvitationFactory {

     private final MailExtendedProperties mailExtendedProperties;
     private final RoutingProperties routingProperties;
     private final TemplateEngine templateEngine;
     private final MessageSource messageSource;
     private final JavaMailSender javaMailSender;

     public MimeMessage createMessage(RoomInvitationMailDTO mailDTO) throws MessagingException {
          final LocalizedMessageSource localizedMessageSource =
                  new LocalizedMessageSource(messageSource, mailDTO.getLocale());

          return MultipartMimeMessageBuilder.of(javaMailSender)
                  .from(mailExtendedProperties.getNoReplyAddress())
                  .to(mailDTO.getRecipientAddress())
                  .subject(localizedMessageSource.get(TITLE))
                  .htmlText(createHTMLContent(localizedMessageSource, mailDTO))
                  .getMessage();
     }

     private String createHTMLContent(LocalizedMessageSource messages, RoomInvitationMailDTO mail) {
          final Map<String, Object> variables = buildVariables(messages, mail);
          Context context = new Context(messages.getLocale());
          context.setVariables(variables);
          return templateEngine.process(TEMPLATE_NAME, context);
     }

     private Map<String, Object> buildVariables(LocalizedMessageSource messages, RoomInvitationMailDTO mail) {
          return Map.ofEntries(
                  entry(ACTION_URL_VARIABLE, createActionURL(mail.getRoomId())),
                  entry(MAIN_MESSAGE_VARIABLE, createMainMessage(messages, mail.getOrganiserUsername(), mail.getRoomName())),
                  entry(STARTING_GREETER_VARIABLE, createStartingGreeter(messages, mail.getRecipientUsername()))
          );
     }

     private String createActionURL(long roomId) {
          return String.format(routingProperties.getRoomInvitationTemplate(), roomId);
     }

     private String createMainMessage(LocalizedMessageSource message, String organiser, String roomTitle) {
          return String.format(message.get(MAIN_MESSAGE_TEMPLATE), organiser, roomTitle);
     }

     private String createStartingGreeter(LocalizedMessageSource messages, String username) {
          return String.format(messages.get(STARTING_GREETER_TEMPLATE), username);
     }
}
