package com.example.demo;

import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.drools.core.impl.StatefulKnowledgeSessionImpl;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private KieSession kieSession;

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public void test(){

        //RuleNameEqualsAgendaFilter aaa = new RuleNameEqualsAgendaFilter("aaa");
        //kieSession.fireAllRules(aaa);

        QueryParam queryParam = new QueryParam();

        FactHandle param = kieSession.insert(queryParam);

        queryParam.setTest("aaa");
        kieSession.fireAllRules();

        RuleResult ruleResult = new RuleResult();
        FactHandle result = kieSession.insert(ruleResult);
        queryParam.setTest("bbb");
        kieSession.update(param, queryParam);
        kieSession.fireAllRules();
        System.out.println("bbb result: " + ruleResult.getResult());


        queryParam.setTest("ccc");
        kieSession.update(param, queryParam);
        kieSession.delete(result);
        kieSession.setGlobal("testService", testService);
        //RuleNameEqualsAgendaFilter ccc = new RuleNameEqualsAgendaFilter("ccc");
        kieSession.fireAllRules();


        kieSession.dispose();
    }
}
