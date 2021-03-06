/*
 * Copyright (c) 2016. Zuercher Hochschule fuer Angewandte Wissenschaften
 *  All Rights Reserved.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License"); you may
 *     not use this file except in compliance with the License. You may obtain
 *     a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *     WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *     License for the specific language governing permissions and limitations
 *     under the License.
 */
package ch.icclab.cyclops.endpoint;

import ch.icclab.cyclops.util.APICallCounter;
import ch.icclab.cyclops.client.UdrServiceClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Author: Oleksii
 * Created: 25/01/16
 * Description: Prediction endpoint
 */
public class Prediction extends ServerResource {

    final static Logger logger = LogManager.getLogger(Prediction.class.getName());
    // used as counter
    private APICallCounter counter = APICallCounter.getInstance();

    // userid and resourceid that was received via API call
    private String userid;
    private String resourceid;

    /**
     * This method is invoked in order to get command from API URL
     */
    public void doInit() {
        userid = (String) getRequestAttributes().get("userid");
        resourceid = (String) getRequestAttributes().get("resourceid");
    }

    @Get
    public String generatePrediction(){
        logger.trace("BEGIN CONSTRUCTOR generatePrediction()");
        String response;
        String endpoint = "/prediction";
        UdrServiceClient client = new UdrServiceClient();
        Integer daysBefore = Integer.parseInt(getQueryValue("use"));
        Integer daysTo = Integer.parseInt(getQueryValue("forecast"));
        response = client.getUserUsageData(userid,resourceid,daysBefore,daysTo);
        counter.increment(endpoint);
        logger.trace("END CONSTRUCTOR generatePrediction()");
        return response;
    }

}
