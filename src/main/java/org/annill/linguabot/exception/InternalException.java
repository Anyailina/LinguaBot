package org.annill.linguabot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.annill.linguabot.enums.ExceptionEnum;

@AllArgsConstructor
@Getter
public abstract class InternalException extends RuntimeException {
    protected final ExceptionEnum exception;
}
