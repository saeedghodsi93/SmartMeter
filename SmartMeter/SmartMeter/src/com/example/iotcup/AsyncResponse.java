package com.example.iotcup;

import com.example.iotcup.JSONParser.JSONParserReturnObject;

public interface AsyncResponse {
    void processFinish(JSONParserReturnObject jsonParserReturnObject, int asyncTaskId);
    
}