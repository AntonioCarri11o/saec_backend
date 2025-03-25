package com.saec.formtic.model.test.answer;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class MutipleAnswer extends Answer {
    List<String> answers;
}
