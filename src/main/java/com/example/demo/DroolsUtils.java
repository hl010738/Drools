package com.example.demo;

import org.drools.decisiontable.DecisionTableProviderImpl;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

public class DroolsUtils {

    public final static String err_01 = "unable to local Decision Table: ";

    public final static String err_02 = "kieHelper XLS to DRL Error";

    public static String getDRLFromXLS(String excelFile, Object object){

        DecisionTableConfiguration configuration = KnowledgeBuilderFactory.newDecisionTableConfiguration();

        configuration.setInputType(DecisionTableInputType.XLS);

        Resource dt = ResourceFactory.newClassPathResource(excelFile, object.getClass());

        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();

        String drl = decisionTableProvider.loadFromResource(dt, null);

        return drl;
    }

    public static KieSession createKieSessionFromDRL(String drl) {

        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results verify = kieHelper.verify();
        if (verify.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {

            System.out.println("createKieSessionFromDRL" + err_02);
            for (Message message : verify.getMessages(Message.Level.WARNING, Message.Level.ERROR)) {
                System.out.println("createKieSessionFromDRL" + message.getText());
            }

            //
            System.out.println(err_02);
        }

        return kieHelper.build().newKieSession();
    }
}
