package com.parabank.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.parabank.api.ParaBankApi;
import com.parabank.models.User;
import com.parabank.utils.TestData;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ScenarioCTest {

    @Test(description = "Scenario C - headless API parity and response schema")
    public void apiParity() throws Exception {

        User u = TestData.newUser();

        // Register user directly through HTTP
        registerHeadless(u);

        // Login through ParaBank API
        ParaBankApi api = new ParaBankApi();

        JsonNode customer = api.json(
                "/login/"
                        + enc(u.username())
                        + "/"
                        + enc(u.password())
        );

        assertCustomerSchema(customer);

        int customerId = customer.get("id").asInt();

        // Get customer accounts
        JsonNode accounts =
                api.json("/customers/" + customerId + "/accounts");

        Assert.assertTrue(
                accounts.isArray() && !accounts.isEmpty(),
                "Customer should have at least one account"
        );

        int accountId =
                accounts.get(0).get("id").asInt();

        // Deposit
        Map<String, String> depositData = new LinkedHashMap<>();
        depositData.put("accountId", String.valueOf(accountId));
        depositData.put("amount", "100.00");

        api.post("/deposit", depositData);

        // Get transactions
        JsonNode tx =
                api.json("/accounts/" + accountId + "/transactions");

        Assert.assertTrue(
                tx.isArray(),
                "Transactions response should be an array"
        );

        if (!tx.isEmpty()) {
            assertTransactionSchema(tx.get(0));
        }
    }

    private void registerHeadless(User u) throws Exception {

        /*
         * Map.of() supports maximum 10 key-value pairs.
         * Therefore LinkedHashMap is used here because
         * registration requires 11 fields.
         */
        Map<String, String> registrationData =
                new LinkedHashMap<>();

        registrationData.put(
                "customer.firstName",
                u.firstName()
        );

        registrationData.put(
                "customer.lastName",
                u.lastName()
        );

        registrationData.put(
                "customer.address.street",
                u.address()
        );

        registrationData.put(
                "customer.address.city",
                u.city()
        );

        registrationData.put(
                "customer.address.state",
                u.state()
        );

        registrationData.put(
                "customer.address.zipCode",
                u.zipCode()
        );

        registrationData.put(
                "customer.phoneNumber",
                u.phone()
        );

        registrationData.put(
                "customer.ssn",
                u.ssn()
        );

        registrationData.put(
                "customer.username",
                u.username()
        );

        registrationData.put(
                "customer.password",
                u.password()
        );

        registrationData.put(
                "repeatedPassword",
                u.password()
        );

        String form = registrationData.entrySet()
                .stream()
                .map(e ->
                        URLEncoder.encode(
                                e.getKey(),
                                StandardCharsets.UTF_8
                        )
                        + "="
                        + URLEncoder.encode(
                                e.getValue(),
                                StandardCharsets.UTF_8
                        )
                )
                .reduce(
                        (a, b) -> a + "&" + b
                )
                .orElse("");

        String url =
                com.parabank.config.Config.baseUrl()
                        + "/register.htm";

        HttpRequest request =
                HttpRequest.newBuilder(
                        URI.create(url)
                )
                .header(
                        "Content-Type",
                        "application/x-www-form-urlencoded"
                )
                .POST(
                        HttpRequest.BodyPublishers
                                .ofString(form)
                )
                .build();

        HttpResponse<String> response =
                HttpClient.newHttpClient()
                        .send(
                                request,
                                HttpResponse.BodyHandlers.ofString()
                        );

        Assert.assertTrue(
                response.statusCode() < 400,
                "Registration HTTP failure: "
                        + response.statusCode()
                        + "\nResponse: "
                        + response.body()
        );
    }

    private String enc(String s) {

        return URLEncoder.encode(
                s,
                StandardCharsets.UTF_8
        );
    }

    private void assertCustomerSchema(JsonNode n) {

        for (String field :
                List.of(
                        "id",
                        "firstName",
                        "lastName",
                        "address"
                )) {

            Assert.assertTrue(
                    n.has(field),
                    "Customer schema missing: " + field
            );
        }

        Assert.assertTrue(
                n.get("id").isInt(),
                "Customer id should be integer"
        );

        Assert.assertTrue(
                n.get("address").isObject(),
                "Customer address should be an object"
        );
    }

    private void assertTransactionSchema(JsonNode n) {

        for (String field :
                List.of(
                        "id",
                        "accountId",
                        "type",
                        "date",
                        "amount"
                )) {

            Assert.assertTrue(
                    n.has(field),
                    "Transaction schema missing: " + field
            );
        }

        Assert.assertTrue(
                n.get("id").isInt(),
                "Transaction id should be integer"
        );

        Assert.assertTrue(
                n.get("accountId").isInt(),
                "Transaction accountId should be integer"
        );

        Assert.assertTrue(
                n.get("amount").isNumber(),
                "Transaction amount should be numeric"
        );
    }
}