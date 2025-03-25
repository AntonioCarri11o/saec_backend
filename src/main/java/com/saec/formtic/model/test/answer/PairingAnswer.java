package com.saec.formtic.model.test.answer;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper=true)
public class PairingAnswer extends Answer{
    private List<Map<String,String>> answers;
}
