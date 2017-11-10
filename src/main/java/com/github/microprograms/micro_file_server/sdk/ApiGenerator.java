package com.github.microprograms.micro_file_server.sdk;

import java.io.IOException;

import com.github.microprograms.micro_api_sdk.model.EngineDefinition;
import com.github.microprograms.micro_api_sdk.utils.ApiDocumentForShowdocUtils;
import com.github.microprograms.micro_api_sdk.utils.ApiDocumentUtils;
import com.github.microprograms.micro_api_sdk.utils.ApiEngineGeneratorUtils;

public class ApiGenerator {
    public static void main(String[] args) throws IOException {
        publicApi();
        publicApiForShowdoc();
    }

    public static void publicApi() throws IOException {
        String srcFolder = "src/main/java";
        EngineDefinition engineDefinition = ApiEngineGeneratorUtils.buildEngineDefinition("design/public-api.json");
        ApiEngineGeneratorUtils.deleteModelJavaFiles(srcFolder, engineDefinition);
        ApiEngineGeneratorUtils.createModelJavaFiles(srcFolder, engineDefinition);
        ApiEngineGeneratorUtils.updateApiJavaFiles(srcFolder, engineDefinition);
        ApiEngineGeneratorUtils.deleteUnusedApiJavaFiles(srcFolder, engineDefinition);
        ApiEngineGeneratorUtils.updateErrorCodeJavaFile(srcFolder, engineDefinition);
        ApiDocumentUtils.writeApiHtmlDocumentFile("doc", engineDefinition);
    }

    public static void publicApiForShowdoc() throws IOException {
        EngineDefinition engineDefinition = ApiEngineGeneratorUtils.buildEngineDefinition("design/public-api.json");
        ApiDocumentForShowdocUtils.update(engineDefinition);
    }
}
