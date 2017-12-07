package io.atlasmap.qe.test.atlas.steps;

import java.util.Map;

import org.junit.Assert;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import io.atlasmap.qe.test.atlas.utils.Utils;

public class BackendSteps extends CucumberGlue {

    @Given("^atlasmap is clean$")
    public void atlasmapIsClean() throws Exception {
        Utils.cleanMappingFolder();
    }

    @Given("^atlasmap contains TestClass$")
    public void atlasmapContainsTestClass() throws Exception {
        String resp = Utils.requestClass(atlasmapPage.TEST_CLASS);
        Assert.assertTrue(resp.contains(atlasmapPage.TEST_CLASS));
    }

    @Then("^save mapping as \"([^\"]*)\"$")
    public void userSavesMappingAs(String arg1) throws Exception {
        Thread.sleep(1000);
        String mappingLocation = Utils.moveMappping(arg1);
        validator.setMappingLocation(arg1);
    }

    @And("^verify \"([^\"]*)\"$")
    public void userVerifies(String arg0) throws Throwable {
        Assert.assertTrue(validator.verifyMapping());

    }

    @When("^set source data$")
    public void userSetsSourceData(DataTable sourceMappingData) throws Throwable {
        //SourceMappingTestClass source = new SourceMappingTestClass();
        for (Map<String, String> source : sourceMappingData.asMaps(String.class, String.class)) {
            for (String field : source.keySet()) {
                this.validator.setSourceValue(field, source.get(field));
            }
        }
    }

    @And("^set expected data$")
    public void userSetsExpectedData(DataTable targetMappingData) throws Throwable {
        for (Map<String, String> source : targetMappingData.asMaps(String.class, String.class)) {
            for (String field : source.keySet()) {
                this.validator.setTargetValue(field, source.get(field));
            }
        }
    }
}
