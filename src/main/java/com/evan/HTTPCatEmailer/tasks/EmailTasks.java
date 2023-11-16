package com.evan.HTTPCatEmailer.tasks;

import com.evan.HTTPCatEmailer.service.CatService;
import com.evan.HTTPCatEmailer.service.EmailService;
import com.evan.HTTPCatEmailer.tools.ProfileManager;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class EmailTasks {

    private final EmailService emailService;

    private final CatService catService;

    private  final ProfileManager profileManager;

    private static final Logger log = LoggerFactory.getLogger(EmailTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public EmailTasks(EmailService emailService, CatService catService, ProfileManager profileManager) {
        this.emailService = emailService;
        this.catService = catService;
        this.profileManager = profileManager;
    }

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

    @Scheduled(fixedRate = 600000)
    public void EmailReminder(){
        log.info("Check your email!");
    }

    @Scheduled(fixedRate = 600000)
    public void sendRandomStatusAsAttachment() throws MessagingException {
        Random random = new Random();
        Long randomId = (long) random.nextInt(71) + 1;
        Mono<ResponseEntity<byte[]>> response = catService.requestImageFromCatCloneId(randomId);
        Mono<byte[]> imageBytes = catService.convertMonoCatImageToMonoByteArray(response);
        Mono<String> randomStatusCodeMono = catService.requestStatusFromCatCloneId(randomId);
        imageBytes.subscribe(
                imageData -> {
                    log.info("Sending attachment email...");
                    emailService.sendAttachmentMessage(profileManager.getActiveEmailUsername(),
                            randomStatusCodeMono,
                            "",
                            Mono.just(imageData)).subscribe();
                },
                error -> log.error("Error obtaining image bytes.", error)
        );
    }
}