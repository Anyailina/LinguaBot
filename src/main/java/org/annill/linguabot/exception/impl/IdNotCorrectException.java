package org.annill.linguabot.exception.impl;


import org.annill.linguabot.enums.ExceptionEnum;
import org.annill.linguabot.exception.InternalException;

public class IdNotCorrectException extends InternalException {
    public IdNotCorrectException() {
        super(ExceptionEnum.ID_NOT_CORRECT);
    }
}
