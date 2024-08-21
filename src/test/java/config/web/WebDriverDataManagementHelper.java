package config.web;

import com.github.javafaker.Faker;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.SkipException;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static config.web.WebDriverFactory.getCurrentPath;

@Log
public class WebDriverDataManagementHelper {

    private static final String FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String INTERNAL_TEST_DATA = "/src/test/resources/data/externalData.json";
    private static final String CUCUMBER_REPORTS_FOLDER = "/target/cucumber/";

    Faker faker = new Faker();
    public JSONObject testData = setTestData();


    public JSONObject setTestData() {
        JSONObject data = new JSONObject();
        data.put("uuid", UUID.randomUUID());
        data.put("uuid2", UUID.randomUUID());
        data.put("randomNumber", createRandomNumber());
        data.put("today", getTodayDate());
        data.put("lastYear", addDaysToDate("today", -365));
        data.put("quote", faker.backToTheFuture().quote());
        data.put("name", faker.backToTheFuture().character());

        JSONObject externalData = initExternalTestData(INTERNAL_TEST_DATA);
        if(!externalData.isEmpty()){
            data.putAll(externalData);
        }
        return data;
    }

    public JSONObject getRawTestData(){
        return testData.isEmpty() ? null : testData;
    }

    public String getTestData(String key){
        String value = testData.containsKey(key) ? testData.get(key).toString() : null;
        if (StringUtils.isEmpty(value)) {
            log.info(String.format("Selected key %s is not in saved test data", key));
        }
        return value;
    }

    public void saveInTestData(String key, String value) {
        if (StringUtils.isNotEmpty(key)) {
            if (testData.containsKey(key)) {
                testData.replace(key, value);
            } else {
                testData.put(key, value);
            }
            log.info("Test Data updated: " + testData);
        } else {
            log.info("testData data: value was empty");
        }
    }

    public static String createRandomNumber() {
        long randomNumber = (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;
        return String.valueOf(randomNumber);
    }

    public static String getTodayDate() {
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);

        String today = new SimpleDateFormat(FORMAT_DATE).format(Calendar.getInstance().getTime());
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(format.parse(today));
            currentDate = c.getTime();
        } catch (Exception e) {
            log.info("Error converting dates");
        }
        return format.format(currentDate);
    }

    public static String addDaysToDate(String startDate, int daysToAdd) {
        Date currentDatePlusDays = new Date();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);

        if (StringUtils.equalsIgnoreCase(startDate, "today")) {
            startDate = new SimpleDateFormat(FORMAT_DATE).format(Calendar.getInstance().getTime());
        } else {
            startDate =
                    String.valueOf(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("uuuu-MM-dd")))
                            .concat("T16:00:00");
        }

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(startDate));
            c.add(Calendar.DAY_OF_MONTH, daysToAdd);
            currentDatePlusDays = c.getTime();
        } catch (java.text.ParseException e) {
            log.info("Error converting dates");
        }
        return format.format(currentDatePlusDays);
    }

    public static JSONObject initExternalTestData(String rawFileData) {
        String bodyPath;
        JSONObject jsonData = null;
        try {
            bodyPath = new String(Files.readAllBytes(Paths.get(getCurrentPath()
                            + rawFileData)));
        } catch (IOException | NullPointerException e) {
            throw new SkipException("check configProperties or path variable " + e.getMessage());
        }

        if (StringUtils.isNotEmpty(bodyPath)) {
            try {
                JSONParser parser = new JSONParser();
                jsonData = (JSONObject) parser.parse(bodyPath);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.info("data.json is empty");
        }

        return jsonData;
    }

    public static JSONArray initExternalData(String rawFileData) {
        String bodyPath;
        JSONArray jsonData = null;
        try {
            bodyPath = new String(Files.readAllBytes(Paths.get(rawFileData)));
        } catch (IOException | NullPointerException e) {
            throw new SkipException("check configProperties or path variable " + e.getMessage());
        }

        if (StringUtils.isNotEmpty(bodyPath)) {
            try {
                JSONParser parser = new JSONParser();
                jsonData = (JSONArray) parser.parse(bodyPath);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.info("data.json is empty");
        }

        return jsonData;
    }

    public static void extractMetricsFromReport() throws ParseException {
        JSONParser parser = new JSONParser();
        String feature;
        String tags = "";
        String start_timestamp = "";
        String name = "";
        String status = "";
        JSONArray report = null;

        File[] listFiles = new File(getCurrentPath() + CUCUMBER_REPORTS_FOLDER).listFiles();

        if(listFiles!=null){
            for(File f : listFiles){
                try {
                    report = WebDriverDataManagementHelper.initExternalData(f.getAbsolutePath());
                    if(report!=null){
                        for(Object obj : report) {
                            JSONObject jsonData = (JSONObject) parser.parse(obj.toString());
                            JSONArray rawScenarios = (JSONArray) jsonData.get("elements");
                            JSONArray rawTags = (JSONArray) jsonData.get("tags");

                            feature = jsonData.get("name").toString();

                            for (int i = 0; rawTags.size() > i; i++) {
                                JSONObject tagData = (JSONObject) parser.parse(rawTags.get(i).toString());
                                tags = tags.concat(tagData.get("name") + " ");
                            }
                            System.out.println("###############");
                            System.out.println(feature);
                            System.out.println(tags);
                            System.out.println("###############");

                            for (int i = 0; rawScenarios.size() > i; i++) {
                                JSONObject scenariosData = (JSONObject) parser.parse(rawScenarios.get(i).toString());
                                name = scenariosData.get("name").toString();
                                start_timestamp = scenariosData.get("start_timestamp").toString();

                                JSONArray rawStatus = (JSONArray) scenariosData.get("after");
                                JSONObject rawStatusData = (JSONObject) parser.parse(rawStatus.get(0).toString());
                                JSONObject result = (JSONObject) rawStatusData.get("result");
                                status = result.get("status").toString();

                                System.out.println(name);
                                System.out.println(start_timestamp);
                                System.out.println(status);
                                System.out.println("###############");
                            }

                        }
                    }
                } catch (NullPointerException e) {
                    throw new SkipException("check configProperties or path variable " + e.getMessage());
                }
            }
        }

    }

    public JSONObject getUsersBundle(){
        JSONObject rawData = getRawTestData();
        return rawData.containsKey("users") ? (JSONObject) rawData.get("users") : null;
    }

    public JSONObject getUserDataFromExternal(String user){
        JSONObject usersBundle = getUsersBundle();
        return usersBundle.containsKey(user) ? (JSONObject) usersBundle.get(user) : null;
    }

    public JSONObject adminUserCredentials() {
        JSONObject usersBundle = getUsersBundle();
        if(usersBundle.isEmpty()){
            throw new SkipException("There is not users in test data");
        }

        JSONObject userdata = null;
        try {
            for (Object rawUser : usersBundle.values()) {
                JSONObject rawUserJson = (JSONObject) rawUser;
                if(rawUserJson.containsKey("status") &&
                        StringUtils.equals("active", rawUserJson.get("status").toString())
                    &&rawUserJson.containsKey("role")&&
                        StringUtils.equals("admin", rawUserJson.get("role").toString())){
                    userdata = rawUserJson;
                    break;
                }
            }

            if(userdata==null){
                throw new SkipException("No active users");
            }

        } catch (NullPointerException e) {
            throw new SkipException("Issue retrieving user " + e.getMessage());
        }
        return userdata;
    }

}
