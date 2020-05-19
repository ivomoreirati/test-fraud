package br.com.itau.fraud.processor;

import br.com.itau.fraud.dtos.IBaseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class BaseProcessor<T extends IBaseDTO> implements IProcessor {

    @Autowired
    protected ObjectMapper objectMapper;


    public void processMessage(final String message) {
        Objects.requireNonNull(message, "The message must not be null.");
        Long start = System.nanoTime();
        T dto = null;
        boolean success = false;
        try {

            log.debug("Message received [{}].", message);

            dto = buildDTO(message);

            if (!isMessageValid(dto)) {
                return;
            }

            doProcessMessage(dto);
            success = true;
        } catch (Exception e) {
            log.error("Unexpected exception. Payload: [{ " + message + "}]", e);
        }
        Long end = System.nanoTime();
        log.debug("Integration for the item [{} - {}] ended in {} seconds. Success: [{}]",
                getMessageType(),
                Objects.nonNull(dto) ? dto.getDTOId() : "",
                TimeUnit.NANOSECONDS.toSeconds((end - start)),
                success);
    }

    public abstract Class<T> getClassDtoClass();

    abstract void doProcessMessage(final T dto) throws Exception;

    protected abstract String getMessageType();

    protected abstract boolean isMessageValid(final IBaseDTO dto);

    private T buildDTO(final String message) throws IOException {
        Objects.requireNonNull(message, "Message must not be null");
        return objectMapper.readValue(message, getClassDtoClass());
    }
}