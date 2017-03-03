package org.lightfw.utilx.dynamic;

import java.lang.instrument.Instrumentation;

/**
 *
 */
public class AssistantAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        //TODO ADD ARGS CHECK
        System.out.println("AssistantAgent.premain() was called.");
        inst.addTransformer(new JsstStackXformer());
    }
}