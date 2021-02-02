package stepDefinitions;
import apiEngine.EndPoints;
import cucumber.ScenarioContext;
import cucumber.TestContext;

public class BaseSteps {

    private EndPoints endPoints;
    private ScenarioContext scenarioContext;

    public BaseSteps(TestContext testContext) {
        endPoints = testContext.getEndPoints();
        scenarioContext = testContext.getScenarioContext();
    }

    public EndPoints getEndPoints() {
        return endPoints;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}